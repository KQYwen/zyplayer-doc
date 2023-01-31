package com.zyplayer.doc.db.framework.db.sql.dialect.oracle.function;

import com.alibaba.druid.sql.dialect.oracle.parser.OracleLexer;
import org.springframework.util.StringUtils;

/**
 * oracle sql语句相关工具类
 *
 * @author diantu
 * @since 2023年1月30日
 */
public class OracleUtil {

    /**
     * 是否包含关键词
     * @param name
     * @return
     */
    public static boolean containsKeyWords(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        return OracleLexer.DEFAULT_ORACLE_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
    }
}
