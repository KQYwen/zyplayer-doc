package com.zyplayer.doc.db.framework.db.enums;

/**
 * 数据库类型枚举
 */
public enum DatabaseProductEnum {
	MYSQL("com.mysql.jdbc.Driver"),
	SQLSERVER("net.sourceforge.jtds.jdbc.Driver"),
	ORACLE("oracle.jdbc.driver.OracleDriver"),
	POSTGRESQL("org.postgresql.Driver"),
	HIVE("org.apache.hive.jdbc.HiveDriver"),
	;
	
	private String driverClassName;
	
	DatabaseProductEnum(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
}
