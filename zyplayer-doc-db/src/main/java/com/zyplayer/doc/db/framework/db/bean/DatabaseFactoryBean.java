package com.zyplayer.doc.db.framework.db.bean;

import com.alibaba.druid.pool.DruidDataSource;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * 描述连接信息的对象
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public class DatabaseFactoryBean {
	private Long id;
	private DruidDataSource dataSource;
	private SqlSessionTemplate sqlSessionTemplate;
	private String url;
	private String dbName;
	private String cnName;
	private String groupName;
	private DatabaseProductEnum databaseProduct;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCnName() {
		return cnName;
	}
	
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public DruidDataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public DatabaseProductEnum getDatabaseProduct() {
		return databaseProduct;
	}
	
	public void setDatabaseProduct(DatabaseProductEnum databaseProduct) {
		this.databaseProduct = databaseProduct;
	}
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
}
