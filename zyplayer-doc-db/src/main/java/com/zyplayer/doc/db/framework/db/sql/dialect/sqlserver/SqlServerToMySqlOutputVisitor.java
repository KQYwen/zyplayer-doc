package com.zyplayer.doc.db.framework.db.sql.dialect.sqlserver;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLIndexDefinition;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerOutputVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.util.FnvHash;
import com.zyplayer.doc.db.framework.db.sql.dialect.sqlserver.function.SqlServerToMySqlFunctionTransform;
import com.zyplayer.doc.db.framework.db.sql.dialect.sqlserver.util.SqlServerSQLDataTypeTransformUtil;
import com.zyplayer.doc.db.framework.utils.MapCacheUtil;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;

import java.util.List;
import java.util.Objects;

/**
 * sqlserver转mysql遍历实现
 *
 * @author diantu
 * @since 2023年2月2日
 */
public class SqlServerToMySqlOutputVisitor extends SQLServerOutputVisitor {

    // 目标数据库类型
    private final DbType distDbType = DbType.mysql;

    private final SqlServerToMySqlFunctionTransform functionTransform = new SqlServerToMySqlFunctionTransform();

    public SqlServerToMySqlOutputVisitor(Appendable appender) {
        super(appender);
    }

    public SqlServerToMySqlOutputVisitor(Appendable appender, boolean printPostSemi) {
        super(appender, printPostSemi);
    }

    /**
     * sqlserver建表语句遍历
     * @param x
     * @return
     */
    @Override
    public boolean visit(SQLCreateTableStatement x) {
        MySqlCreateTableStatement mySqlCreateTableStatement = new MySqlCreateTableStatement();
        mySqlCreateTableStatement.setTableSource(x.getTableSource());
        String tableName = x.getTableSource().getName().getSimpleName();
        String name = String.valueOf(x.getName()).replaceAll("\"", "");
        name = name.replaceAll("\\[", "").replaceAll("\\]", "");
        mySqlCreateTableStatement.setName(name);
        for(SQLTableElement sqlTableElement : x.getTableElementList()){
            if( sqlTableElement instanceof SQLColumnDefinition) {
                SQLColumnDefinition sqlColumnDefinition = ((SQLColumnDefinition)sqlTableElement);
                String columnName = sqlColumnDefinition.getName().getSimpleName().replaceAll("\"", "");
                columnName = columnName.replaceAll("\\[", "").replaceAll("\\]", "");
                sqlColumnDefinition.setName(columnName);
                if(SQLTransformUtils.containsKeyWords(columnName,DbType.mysql)){
                    sqlColumnDefinition.setName("`"+columnName+"`");
                }
                sqlColumnDefinition.setDataType(SqlServerSQLDataTypeTransformUtil.transformSqlServerToMySql(SQLParserUtils.createExprParser(sqlColumnDefinition.getDataType().toString(), DbType.oracle).parseDataType()));
                if(sqlColumnDefinition.getDefaultExpr() != null) {
                    SQLExpr expr = sqlColumnDefinition.getDefaultExpr();
                    if(expr instanceof SQLMethodInvokeExpr) {
                        SQLMethodInvokeExpr sqlMethodInvokeExpr = (SQLMethodInvokeExpr) expr;
                        final long nameHashCode64 = sqlMethodInvokeExpr.methodNameHashCode64();
                        //mysql默认值只能为常量
                        if (nameHashCode64 == FnvHash.Constants.IDENTITY) {
                            SQLIdentifierExpr xx = new SQLIdentifierExpr("AUTO_INCREMENT");
                            xx.setParent(expr.getParent());
                            sqlColumnDefinition.setDefaultExpr(xx);
                        }else {
                            functionTransform.methodInvoke((SQLMethodInvokeExpr) sqlColumnDefinition.getDefaultExpr());
                        }

                    }
                }
                sqlColumnDefinition.setDbType(distDbType);
                MapCacheUtil.getInstance().addCacheData(tableName.toUpperCase() + ":" + columnName.toUpperCase(), sqlColumnDefinition.toString().replaceAll(sqlColumnDefinition.getColumnName(), ""));
                mySqlCreateTableStatement.getTableElementList().add(sqlColumnDefinition);
            }
        }

        if(Objects.nonNull(x.getSelect())){
            x.setParent(mySqlCreateTableStatement);
            mySqlCreateTableStatement.setSelect(x.getSelect());
        }
        println();
        print(mySqlCreateTableStatement.toString());
        println(";");
        return false;
    }

    /**
     * sqlserver修改语句遍历
     * @param x
     * @return
     */
    @Override
    public boolean visit(SQLAlterTableStatement x) {
        for (SQLAlterTableItem item : x.getItems()) {
            if(item instanceof SQLAlterTableRename) {
                ((SQLAlterTableStatement) item.getParent()).setDbType(distDbType);
                String rename = "";
                if(x.isAfterSemi()) {
                    rename = x.toString().substring(0, x.toString().length() - 1);
                } else {
                    rename = x.toString();
                }
                println();
                print0(rename);
                return false;
            }
            if(item instanceof SQLAlterTableAddConstraint) {
                ((SQLAlterTableStatement) item.getParent()).setDbType(distDbType);
                SQLPrimaryKeyImpl sQLPrimaryKeyImpl =  (SQLPrimaryKeyImpl) ((SQLAlterTableAddConstraint) item).getConstraint();
                SQLIndexDefinition sQLIndexDefinition = sQLPrimaryKeyImpl.getIndexDefinition();
                List<SQLSelectOrderByItem> list = sQLIndexDefinition.getColumns();
                for (SQLSelectOrderByItem sqlSelectOrderByItem : list) {
                    SQLIdentifierExpr sQLIdentifierExpr = (SQLIdentifierExpr) sqlSelectOrderByItem.getExpr();
                    sQLIdentifierExpr.setName(sQLIdentifierExpr.getName().replaceAll("\\[", "").replaceAll("\\]", ""));
                }
            }
        }
        return super.visit(x);
    }
}
