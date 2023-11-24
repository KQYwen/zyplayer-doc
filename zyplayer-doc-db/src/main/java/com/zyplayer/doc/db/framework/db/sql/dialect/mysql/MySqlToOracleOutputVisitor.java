package com.zyplayer.doc.db.framework.db.sql.dialect.mysql;

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
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSysdateExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTableStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OraclePrimaryKey;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUnique;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.util.FnvHash;
import com.zyplayer.doc.db.framework.db.sql.dialect.mysql.function.MySqlToOracleFunctionTransform;
import com.zyplayer.doc.db.framework.db.sql.dialect.mysql.util.MySqlSQLDataTypeTransformUtil;
import com.zyplayer.doc.db.framework.utils.MapCacheUtil;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;

import java.util.List;
import java.util.Objects;

/**
 * mysql转oracle遍历实现
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class MySqlToOracleOutputVisitor extends MySqlOutputVisitor {

    private final MySqlToOracleFunctionTransform functionTransform = new MySqlToOracleFunctionTransform();

    // 目标数据库类型
    private final DbType distDbType = DbType.oracle;

    public MySqlToOracleOutputVisitor(Appendable appender) {
        super(appender);
    }

    public MySqlToOracleOutputVisitor(Appendable appender, boolean parameterized) {
        super(appender, parameterized);
    }

    /**
     * mysql建表语句遍历
     * @param x
     * @return
     */
    @Override
    public boolean visit(MySqlCreateTableStatement x) {
        OracleCreateTableStatement oracleCreateTableStatement = new OracleCreateTableStatement();
        oracleCreateTableStatement.setTableSource(x.getTableSource());
        String tableName = x.getTableSource().getName().getSimpleName();
        oracleCreateTableStatement.setName(String.valueOf(x.getName()).replaceAll("\"", ""));
        oracleCreateTableStatement.setName(String.valueOf(x.getName()).replaceAll("`", ""));
        oracleCreateTableStatement.setComment(x.getComment());
        for(SQLTableElement sqlTableElement : x.getTableElementList()){
            if( sqlTableElement instanceof SQLColumnDefinition) {
                SQLColumnDefinition sqlColumnDefinition = ((SQLColumnDefinition)sqlTableElement);
                String columnName = sqlColumnDefinition.getName().getSimpleName().replaceAll("\"", "");
                columnName = columnName.replaceAll("`", "");
                sqlColumnDefinition.setName(columnName);
                if(SQLTransformUtils.containsKeyWords(columnName,DbType.oracle)){
                    sqlColumnDefinition.setName("\""+columnName+"\"");
                }
                sqlColumnDefinition.setDataType(MySqlSQLDataTypeTransformUtil.transformMySqlToOracle(SQLParserUtils.createExprParser(sqlColumnDefinition.getDataType().toString(), DbType.mysql).parseDataType()));
                if(sqlColumnDefinition.getDefaultExpr() != null) {
                    SQLExpr expr = sqlColumnDefinition.getDefaultExpr();
                    if(expr instanceof SQLMethodInvokeExpr) {
                        functionTransform.methodInvoke((SQLMethodInvokeExpr) sqlColumnDefinition.getDefaultExpr());
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
                //@TODO 自增转换(oracle没有AUTO_INCREMENT)
                if(sqlColumnDefinition.isAutoIncrement()){
                    sqlColumnDefinition.setAutoIncrement(false);
                    //SQLIdentifierExpr xx = new SQLIdentifierExpr("sys_guid()");
                    //xx.setName("sys_guid()");
                    //sqlColumnDefinition.setDefaultExpr(xx);
                }
                //@TODO 注释待转换
                if(sqlColumnDefinition.getComment()!= null){
                    sqlColumnDefinition.setComment((String) null);
                }
                sqlColumnDefinition.setDbType(distDbType);
                MapCacheUtil.getInstance().addCacheData(tableName.toUpperCase() + ":" + columnName.toUpperCase(), sqlColumnDefinition.toString().replaceAll(sqlColumnDefinition.getColumnName(), ""));
                oracleCreateTableStatement.getTableElementList().add(sqlColumnDefinition);
            }else if(sqlTableElement instanceof MySqlPrimaryKey){
                OraclePrimaryKey oraclePrimaryKey = new OraclePrimaryKey();
                List<SQLSelectOrderByItem> list =  ((MySqlPrimaryKey) sqlTableElement).getIndexDefinition().getColumns();
                for (SQLSelectOrderByItem sqlSelectOrderByItem : list) {
                    SQLIdentifierExpr sQLIdentifierExpr = (SQLIdentifierExpr) sqlSelectOrderByItem.getExpr();
                    sQLIdentifierExpr.setName(sQLIdentifierExpr.getName().replaceAll("`", ""));
                    oraclePrimaryKey.addColumn(sQLIdentifierExpr);
                }
                oracleCreateTableStatement.getTableElementList().add(oraclePrimaryKey);
            }else if(sqlTableElement instanceof MySqlUnique) {
                OracleUnique oracleUnique = new OracleUnique();
                ((MySqlUnique) sqlTableElement).cloneTo(oracleUnique);
                oracleCreateTableStatement.getTableElementList().add(oracleUnique);
            }
        }

        if(Objects.nonNull(x.getSelect())){
            x.setParent(oracleCreateTableStatement);
            oracleCreateTableStatement.setSelect(x.getSelect());
        }
        println();
        print(oracleCreateTableStatement.toString());
        return false;
    }

}
