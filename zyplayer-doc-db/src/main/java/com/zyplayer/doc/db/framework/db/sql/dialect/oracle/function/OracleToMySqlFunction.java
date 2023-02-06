package com.zyplayer.doc.db.framework.db.sql.dialect.oracle.function;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.zyplayer.doc.db.framework.db.sql.MethodInvoke;

/**
 * oracle2mysql需要转换的函数
 *
 * @author diantu
 * @since 2023年1月29日
 */
public interface OracleToMySqlFunction extends MethodInvoke {

    /**
     * 转换 sys_guid 函数
     * @author diantu
     * @date 2023年1月29日
     * @param expr
     * @return void
     */
    void sys_guid(SQLMethodInvokeExpr expr);

    /**
     * 整体替换当前类型
     * @author diantu
     * @date 2023年1月29日
     * @param name
     * @param expr
     * @return void
     */
    default void identifierExpr(String name, SQLObject expr) {
        SQLIdentifierExpr sqlIdentifierExpr = new SQLIdentifierExpr();
        sqlIdentifierExpr.setName(name);
        sqlIdentifierExpr.setParent(expr.getParent());
        SQLObject parent = expr.getParent();
        if (parent instanceof SQLSelectItem) {
            ((SQLSelectItem) parent).setExpr(sqlIdentifierExpr);
        } else if (parent instanceof SQLBinaryOpExpr) {
            ((SQLBinaryOpExpr) parent).setRight(sqlIdentifierExpr);
        }else if(parent instanceof SQLColumnDefinition){
            ((SQLColumnDefinition) parent).setName(name);
        }
    }
}
