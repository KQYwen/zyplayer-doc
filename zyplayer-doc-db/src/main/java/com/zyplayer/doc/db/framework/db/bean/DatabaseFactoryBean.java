package com.zyplayer.doc.db.framework.db.bean;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;

/**
 * 描述连接信息的对象
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public class DatabaseFactoryBean {
	private Long id;
	private AtomikosDataSourceBean dataSource;
	private SqlSessionTemplate sqlSessionTemplate;
	private String url;
	private String host;
	private String dbName;
	private DatabaseProduct databaseProduct;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public static enum DatabaseProduct {
		MYSQL, SQLSERVER
	}

	public AtomikosDataSourceBean getDataSource() {
		return dataSource;
	}

	public void setDataSource(AtomikosDataSourceBean dataSource) {
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
