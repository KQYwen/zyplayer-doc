package com.zyplayer.doc.db.framework.db.dto;

/**
 * 表字段注释信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public class QueryTableColumnDescDto {
	private String tableName;
	private String columnName;
	private String description;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
