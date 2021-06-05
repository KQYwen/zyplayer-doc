package com.zyplayer.doc.db.controller.param;

public class DataDownloadParam {
	private Long sourceId;
	private String dbName;
	private String tableName;
	private String downloadType;
	private String conditionColumn;
	
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
	
	public String getDownloadType() {
		return downloadType;
	}
	
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	
	public String getConditionColumn() {
		return conditionColumn;
	}
	
	public void setConditionColumn(String conditionColumn) {
		this.conditionColumn = conditionColumn;
	}
}
