package com.zyplayer.doc.db.framework.db.sql.dialect.oracle;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSysdateExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTableStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OraclePrimaryKey;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUnique;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.util.FnvHash;
import com.zyplayer.doc.db.framework.db.sql.dialect.oracle.function.OracleToMySqlFunctionTransform;
import com.zyplayer.doc.db.framework.db.sql.dialect.oracle.util.OracleSQLDataTypeTransformUtil;
import com.zyplayer.doc.db.framework.utils.MapCacheUtil;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;

import java.util.List;
import java.util.Objects;

/**
 * oracle转mysql遍历实现
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class OracleToMySqlOutputVisitor extends OracleOutputVisitor {

    private final OracleToMySqlFunctionTransform functionTransform = new OracleToMySqlFunctionTransform();

    // 目标数据库类型
    private final DbType distDbType = DbType.mysql;

    public OracleToMySqlOutputVisitor(Appendable appender) {
        super(appender);
    }

    public OracleToMySqlOutputVisitor(Appendable appender, boolean printPostSemi) {
        super(appender, printPostSemi);
    }

    /**
     * oracle建表语句遍历
     * @param x
     * @return
     */
    @Override
    public boolean visit(OracleCreateTableStatement x) {
        MySqlCreateTableStatement mySqlCreateTableStatement = new MySqlCreateTableStatement();
        mySqlCreateTableStatement.setTableSource(x.getTableSource());
        String tableName = x.getTableSource().getName().getSimpleName();
        mySqlCreateTableStatement.setName(String.valueOf(x.getName()).replaceAll("\"", ""));
        for(SQLTableElement sqlTableElement : x.getTableElementList()){
            if( sqlTableElement instanceof SQLColumnDefinition) {
                SQLColumnDefinition sqlColumnDefinition = ((SQLColumnDefinition)sqlTableElement);
                String columnName = sqlColumnDefinition.getName().getSimpleName().replaceAll("\"", "");
                sqlColumnDefinition.setName(columnName);
                if(SQLTransformUtils.containsKeyWords(columnName,DbType.mysql)){
                    sqlColumnDefinition.setName("`"+columnName+"`");
                }
                sqlColumnDefinition.setDataType(OracleSQLDataTypeTransformUtil.transformOracleToMySql(SQLParserUtils.createExprParser(sqlColumnDefinition.getDataType().toString(), DbType.oracle).parseDataType()));
                if(sqlColumnDefinition.getDefaultExpr() != null) {
                    SQLExpr expr = sqlColumnDefinition.getDefaultExpr();
                    if(expr instanceof SQLMethodInvokeExpr) {
                        SQLMethodInvokeExpr sqlMethodInvokeExpr = (SQLMethodInvokeExpr) expr;
                        final long nameHashCode64 = sqlMethodInvokeExpr.methodNameHashCode64();
                        //mysql默认值只能为常量
                        if (nameHashCode64 == FnvHash.Constants.SYS_GUID || nameHashCode64 == FnvHash.Constants.TO_CHAR) {
                            sqlColumnDefinition.setDefaultExpr(null);
                        }else {
                            functionTransform.methodInvoke((SQLMethodInvokeExpr) sqlColumnDefinition.getDefaultExpr());
                        }

                    }else if(expr instanceof SQLIdentifierExpr) {
                        SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) expr;
                        final long nameHashCode64 = sqlIdentifierExpr.nameHashCode64();
                        if(nameHashCode64 == FnvHash.Constants.SYSTIMESTAMP) {
                            SQLIdentifierExpr xx = sqlIdentifierExpr.clone();
                            xx.setName("CURRENT_TIMESTAMP");
                            xx.setParent(sqlIdentifierExpr.getParent());
                            sqlColumnDefinition.setDefaultExpr(xx);
                            if(sqlColumnDefinition.getColumnName().contains("UPDATE_TIME")
                                    || sqlColumnDefinition.getColumnName().contains("MDFY_TIME")) {
                                sqlColumnDefinition.setOnUpdate(xx);
                            }
                        }
                    }else if(expr instanceof OracleSysdateExpr) {
                        SQLIdentifierExpr xx = new SQLIdentifierExpr("CURRENT_TIMESTAMP");
                        xx.setParent(expr.getParent());
                        sqlColumnDefinition.setDefaultExpr(xx);
                        if(sqlColumnDefinition.getColumnName().contains("UPDATE_TIME")
                                || sqlColumnDefinition.getColumnName().contains("MDFY_TIME")) {
                            sqlColumnDefinition.setOnUpdate(xx);
                        }
                    }
                }
                sqlColumnDefinition.setDbType(distDbType);
                MapCacheUtil.getInstance().addCacheData(tableName.toUpperCase() + ":" + columnName.toUpperCase(), sqlColumnDefinition.toString().replaceAll(sqlColumnDefinition.getColumnName(), ""));
                mySqlCreateTableStatement.getTableElementList().add(sqlColumnDefinition);
            }else if(sqlTableElement instanceof OraclePrimaryKey){
                MySqlPrimaryKey mySqlPrimaryKey = new MySqlPrimaryKey();
                mySqlPrimaryKey.setName("primary key");
                List<SQLSelectOrderByItem> list =  ((OraclePrimaryKey) sqlTableElement).getIndexDefinition().getColumns();
                for (SQLSelectOrderByItem sqlSelectOrderByItem : list) {
                    mySqlPrimaryKey.addColumn(sqlSelectOrderByItem);
                }
                mySqlCreateTableStatement.getTableElementList().add(mySqlPrimaryKey);
            }else if(sqlTableElement instanceof OracleUnique) {
                MySqlUnique mySqlUnique = new MySqlUnique();
                ((OracleUnique) sqlTableElement).cloneTo(mySqlUnique);
                mySqlCreateTableStatement.getTableElementList().add(mySqlUnique);
            }
        }

        if(Objects.nonNull(x.getSelect())){
            x.setParent(mySqlCreateTableStatement);
            mySqlCreateTableStatement.setSelect(x.getSelect());
        }
        println();
        print(mySqlCreateTableStatement.toString());
        return false;
    }
}
