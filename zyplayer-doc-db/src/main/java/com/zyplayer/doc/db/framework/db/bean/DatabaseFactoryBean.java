package com.zyplayer.doc.db.framework.db.bean;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * 描述连接信息的对象
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public class DatabaseFactoryBean {
	private DataSource dataSource;
	private SqlSessionTemplate sqlSessionTemplate;
	private String url;
	private String host;
	private String dbName;
	private DatabaseProduct databaseProduct;

	public static enum DatabaseProduct {
		MYSQL, SQLSERVER
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public DatabaseProduct getDatabaseProduct() {
		return databaseProduct;
	}

	public void setDatabaseProduct(DatabaseProduct databaseProduct) {
		this.databaseProduct = databaseProduct;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
