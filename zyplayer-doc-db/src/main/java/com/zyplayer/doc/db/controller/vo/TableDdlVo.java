package com.zyplayer.doc.db.controller.vo;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;

import java.util.Objects;

public class TableDdlVo {
	private String current;
	private String mysql;
	private String sqlserver;
	private String oracle;
	private String postgresql;
	private String hive;
	
	public String getTableDDLByType() {
		if (Objects.equals(current, DatabaseFactoryBean.DatabaseProduct.MYSQL.name().toLowerCase())) return mysql;
		if (Objects.equals(current, DatabaseFactoryBean.DatabaseProduct.SQLSERVER.name().toLowerCase())) return sqlserver;
		if (Objects.equals(current, DatabaseFactoryBean.DatabaseProduct.ORACLE.name().toLowerCase())) return oracle;
		if (Objects.equals(current, DatabaseFactoryBean.DatabaseProduct.POSTGRESQL.name().toLowerCase())) return postgresql;
		if (Objects.equals(current, DatabaseFactoryBean.DatabaseProduct.HIVE.name().toLowerCase())) return hive;
		return null;
	}
	
	public String getCurrent() {
		return current;
	}
	
	public void setCurrent(String current) {
		this.current = current;
	}
	
	public String getMysql() {
		return mysql;
	}
	
	public void setMysql(String mysql) {
		this.mysql = mysql;
	}
	
	public String getSqlserver() {
		return sqlserver;
	}
	
	public void setSqlserver(String sqlserver) {
		this.sqlserver = sqlserver;
	}
	
	public String getOracle() {
		return oracle;
	}
	
	public void setOracle(String oracle) {
		this.oracle = oracle;
	}
	
	public String getPostgresql() {
		return postgresql;
	}
	
	public void setPostgresql(String postgresql) {
		this.postgresql = postgresql;
	}
}
