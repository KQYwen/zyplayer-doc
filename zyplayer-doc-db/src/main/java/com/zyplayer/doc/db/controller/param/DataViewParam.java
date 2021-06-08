package com.zyplayer.doc.db.controller.param;

public class DataViewParam {
	private Long sourceId;
	private String executeId;
	private Integer pageSize;
	private Integer pageNum;
	private String dbName;
	private String tableName;
	// 下载多张数据表
	private String tableNames;
	private String orderColumn;
	private String orderType;
	private String condition;
	private String downloadType;
	private String retainColumn;
	private String conditionColumn;
	private Integer dropTableFlag;
	private Integer createTableFlag;
	private Integer downloadFileType;
	
	public Integer getOffset() {
		return ((this.pageNum - 1) * this.pageSize);
	}
	
	public Long getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getExecuteId() {
		return executeId;
	}
	
	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
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
	
	public String getOrderColumn() {
		return orderColumn;
	}
	
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
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
	
	public Integer getDropTableFlag() {
		return dropTableFlag;
	}
	
	public void setDropTableFlag(Integer dropTableFlag) {
		this.dropTableFlag = dropTableFlag;
	}
	
	public Integer getCreateTableFlag() {
		return createTableFlag;
	}
	
	public void setCreateTableFlag(Integer createTableFlag) {
		this.createTableFlag = createTableFlag;
	}
	
	public String getRetainColumn() {
		return retainColumn;
	}
	
	public void setRetainColumn(String retainColumn) {
		this.retainColumn = retainColumn;
	}
	
	public String getTableNames() {
		return tableNames;
	}
	
	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}
	
	public Integer getDownloadFileType() {
		return downloadFileType;
	}
	
	public void setDownloadFileType(Integer downloadFileType) {
		this.downloadFileType = downloadFileType;
	}
}
