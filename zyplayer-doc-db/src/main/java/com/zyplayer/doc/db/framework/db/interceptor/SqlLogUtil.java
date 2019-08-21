package com.zyplayer.doc.db.framework.db.interceptor;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SqlLogUtil {
	private static final Logger logger = LoggerFactory.getLogger(SqlLogUtil.class);
	
	private static String getParameterValue(Object obj) {
		String value;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(obj) + "'";
		} else {
			value = (obj == null) ? "'null'" : obj.toString();
		}
		return value;
	}
	
	public static String getSqlString(Map<String, Object> paramMap, BoundSql boundSql) {
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		StringBuilder sqlSb = new StringBuilder(boundSql.getSql().replaceAll("[\\s]+", " "));
		int fromIndex = 0;
		if (parameterMappings.size() > 0) {
			for (ParameterMapping parameterMapping : parameterMappings) {
				String propertyName = parameterMapping.getProperty();
				Object obj = paramMap.get(propertyName);
				fromIndex = replacePlaceholder(sqlSb, fromIndex, getParameterValue(obj));
			}
		}
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

