package com.zyplayer.doc.db.framework.db.sql.dialect.oracle.function;

import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;

/**
 * oracle2mysql需要转换的函数实现
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class OracleToMySqlFunctionTransform implements OracleToMySqlFunction {

    @Override
    public void sys_guid(SQLMethodInvokeExpr expr) {
        identifierExpr("replace(uuid(), '-', '')", expr);
    }

}
