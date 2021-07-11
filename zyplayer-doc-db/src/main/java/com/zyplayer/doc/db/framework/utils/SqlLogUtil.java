package com.zyplayer.doc.db.framework.utils;

import org.apache.ibatis.mapping.ParameterMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * SQL日志工具
 * @author 暮光：城中城
 * @since 2019-08-19
 */
public class SqlLogUtil {
	private static final Logger logger = LoggerFactory.getLogger(SqlLogUtil.class);
	
	private static String getParameterValue(Object obj) {
		String value;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Number) {
			value = obj.toString();
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(obj) + "'";
		} else {
			value = (obj != null) ? obj.toString() : "'null'";
		}
		return value;
	}
	
	public static String parseLogSql(String sql, List<ParameterMapping> parameterMappings, List<Object> paramList) {
		StringBuilder sqlSb = new StringBuilder(sql.replaceAll(" {2,}", " "));
		int fromIndex = 0;
		if (parameterMappings.size() > 0) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				Object obj = paramList.get(i);
				fromIndex = replacePlaceholder(sqlSb, fromIndex, getParameterValue(obj));
			}
		}
		// 最多返回300的长度
//		String logSql = sqlSb.toString();
//		if (sqlSb.length() > 300) {
//			logSql = sqlSb.substring(0, 300) + "...";
//		}
//		logger.info("sql ==> {}", logSql);
		return sqlSb.toString();
	}
	
	/**
	 * 替换？占位符
	 *
	 * @param sql
	 * @param fromIndex
	 * @param replaceStr
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年10月27日
	 */
	private static int replacePlaceholder(StringBuilder sql, int fromIndex, String replaceStr) {
		int index = sql.indexOf("?", fromIndex);
		if (index >= 0) {
			sql.replace(index, index + 1, replaceStr);
		}
		return index + replaceStr.length();
	}
}

