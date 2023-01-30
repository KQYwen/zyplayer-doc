package com.zyplayer.doc.db.framework.db.sql.dialect.mysql;

import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

/**
 * mysql转oracle遍历实现
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class MySqlToOracleOutputVisitor extends MySqlOutputVisitor {

    public MySqlToOracleOutputVisitor(Appendable appender) {
        super(appender);
    }

    public MySqlToOracleOutputVisitor(Appendable appender, boolean parameterized) {
        super(appender, parameterized);
    }


}
