package com.zyplayer.doc.data.repository.manage.vo;

import java.util.List;

/**
 * 表关系结构
 * @author 暮光：城中城
 * @since 2021-06-07
 */
public class TableRelationVo {
	private String dbName;
	private String tableName;
	// name和columnName是一个，name给前端使用的
	private String name;
	private String columnName;
	private Integer nodeType;
	private List<TableRelationVo> children;
	
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public List<TableRelationVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<TableRelationVo> children) {
		this.children = children;
	}
	
	public Integer getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
}
