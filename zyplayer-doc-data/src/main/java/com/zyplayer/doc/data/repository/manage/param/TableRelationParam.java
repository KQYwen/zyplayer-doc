package com.zyplayer.doc.data.repository.manage.param;

/**
 * 表关系请求参数
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
public class TableRelationParam {
	private Long sourceId;
	private String dbName;
	private String tableName;
	private String columnName;
	// 关系JSON，大概是：[{dbName: 'xxx', tableName: 'xxx', columnName: 'xxx'}]
	private String relation;
	
	public Long getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getRelation() {
		return relation;
	}
	
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
