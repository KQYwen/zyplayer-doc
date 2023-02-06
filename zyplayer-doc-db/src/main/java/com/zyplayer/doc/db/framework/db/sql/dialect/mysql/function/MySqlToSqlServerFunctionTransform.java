package com.zyplayer.doc.db.framework.db.sql.dialect.mysql.function;

import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;

/**
 * mysql2sqlserver需要转换的函数实现
 *
 * @author diantu
 * @since 2023年2月6日
 */
public class MySqlToSqlServerFunctionTransform  implements MySqlToSqlServerFunction{

    @Override
    public void auto_increment(SQLMethodInvokeExpr expr) {
        identifierExpr("identity", expr);
    }
}
