package com.zyplayer.doc.db.framework.db.support;

/**
 * 动态分库使用
 */
public class DBInfoHolder {
	private static final ThreadLocal<String> DB_INFO_NOW = new ThreadLocal<String>();

	public static void setDbInfoNow(String dbInfo) {
		DB_INFO_NOW.set(dbInfo);
	}

	public static String getDbInfoNow() {
		return DB_INFO_NOW.get();
	}

	public static void clear() {
	}
}
