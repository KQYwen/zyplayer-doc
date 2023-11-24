package com.zyplayer.doc.db.framework.db.sql.dialect.mysql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.zyplayer.doc.db.framework.db.sql.dialect.mysql.function.MySqlToSqlServerFunctionTransform;
import com.zyplayer.doc.db.framework.db.sql.dialect.mysql.util.MySqlSQLDataTypeTransformUtil;
import com.zyplayer.doc.db.framework.utils.MapCacheUtil;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;

import java.util.List;
import java.util.Objects;

/**
 * mysql转sqlserver遍历实现
 *
 * @author diantu
 * @since 2023年2月6日
 */
public class MySqlToSqlServerOutputVisitor extends MySqlOutputVisitor {

    private final MySqlToSqlServerFunctionTransform functionTransform = new MySqlToSqlServerFunctionTransform();

    // 目标数据库类型
    private final DbType distDbType = DbType.sqlserver;

    public MySqlToSqlServerOutputVisitor(Appendable appender) {
        super(appender);
    }

    public MySqlToSqlServerOutputVisitor(Appendable appender, boolean parameterized) {
        super(appender, parameterized);
    }

    /**
     * mysql建表语句遍历
     * @param x
     * @return
     */
    @Override
    public boolean visit(MySqlCreateTableStatement x) {
        SQLCreateTableStatement sQLCreateTableStatement = new SQLCreateTableStatement();
        sQLCreateTableStatement.setTableSource(x.getTableSource());
        String tableName = x.getTableSource().getName().getSimpleName();
        sQLCreateTableStatement.setName(String.valueOf(x.getName()).replaceAll("\"", ""));
        sQLCreateTableStatement.setName(String.valueOf(x.getName()).replaceAll("`", ""));
        sQLCreateTableStatement.setComment(x.getComment());
        for(SQLTableElement sqlTableElement : x.getTableElementList()){
            if( sqlTableElement instanceof SQLColumnDefinition) {
                SQLColumnDefinition sqlColumnDefinition = ((SQLColumnDefinition)sqlTableElement);
                String columnName = sqlColumnDefinition.getName().getSimpleName().replaceAll("\"", "");
                columnName = columnName.replaceAll("`", "");
                sqlColumnDefinition.setName(columnName);
                if(SQLTransformUtils.containsKeyWords(columnName,DbType.sqlserver)){
                    sqlColumnDefinition.setName("\""+columnName+"\"");
                }
                sqlColumnDefinition.setDataType(MySqlSQLDataTypeTransformUtil.transformMySqlToSqlServer(SQLParserUtils.createExprParser(sqlColumnDefinition.getDataType().toString(), DbType.mysql).parseDataType()));
                if(sqlColumnDefinition.getDefaultExpr() != null) {
                    SQLExpr expr = sqlColumnDefinition.getDefaultExpr();
                    if(expr instanceof SQLMethodInvokeExpr) {
                        functionTransform.methodInvoke((SQLMethodInvokeExpr) sqlColumnDefinition.getDefaultExpr());
                    }
                }else{
                    //自增转换
                    if(sqlColumnDefinition.isAutoIncrement()){
                        sqlColumnDefinition.setAutoIncrement(false);
                        SQLColumnDefinition.Identity identity = new SQLColumnDefinition.Identity();
                        identity.setCycle(true);
                        identity.setIncrement(1);
                        sqlColumnDefinition.setIdentity(identity);
                    }
                }
                //@TODO 注释待转换
                if(sqlColumnDefinition.getComment()!= null){
                    sqlColumnDefinition.setComment((String) null);
                }

                sqlColumnDefinition.setDbType(distDbType);
                MapCacheUtil.getInstance().addCacheData(tableName.toUpperCase() + ":" + columnName.toUpperCase(), sqlColumnDefinition.toString().replaceAll(sqlColumnDefinition.getColumnName(), ""));
                sQLCreateTableStatement.getTableElementList().add(sqlColumnDefinition);
            }else if(sqlTableElement instanceof MySqlPrimaryKey){
                SQLPrimaryKeyImpl sqlserverPrimaryKey = new SQLPrimaryKeyImpl();
                List<SQLSelectOrderByItem> list =  ((MySqlPrimaryKey) sqlTableElement).getIndexDefinition().getColumns();
                for (SQLSelectOrderByItem sqlSelectOrderByItem : list) {
                    SQLIdentifierExpr sQLIdentifierExpr = (SQLIdentifierExpr) sqlSelectOrderByItem.getExpr();
                    sQLIdentifierExpr.setName(sQLIdentifierExpr.getName().replaceAll("`", ""));
                    sqlserverPrimaryKey.addColumn(sQLIdentifierExpr);
                }
                sQLCreateTableStatement.getTableElementList().add(sqlserverPrimaryKey);
            }else if(sqlTableElement instanceof MySqlUnique) {
                SQLUnique sqlserverUnique = new SQLUnique();
                ((MySqlUnique) sqlTableElement).cloneTo(sqlserverUnique);
                sQLCreateTableStatement.getTableElementList().add(sqlserverUnique);
            }
        }

        if(Objects.nonNull(x.getSelect())){
            x.setParent(sQLCreateTableStatement);
            sQLCreateTableStatement.setSelect(x.getSelect());
        }
        println();
        print(sQLCreateTableStatement.toString());
        return false;
    }

}
