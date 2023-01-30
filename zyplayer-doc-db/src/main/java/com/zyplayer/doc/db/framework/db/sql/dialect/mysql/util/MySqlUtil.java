package com.zyplayer.doc.db.framework.db.sql.dialect.mysql.util;

import com.alibaba.druid.sql.dialect.mysql.parser.MySqlLexer;
import org.springframework.util.StringUtils;

/**
 * mysql sql语句相关工具类
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class MySqlUtil {

    /**
     * 是否包含关键词
     * @param name
     * @return
     */
    public static boolean containsKeyWords(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        return MySqlLexer.DEFAULT_MYSQL_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
    }

}
