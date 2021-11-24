package com.zyplayer.doc.db.framework.db.dto;

/**
 * 表注释信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public class TableDescDto {
	private String tableName;
	private String description;
	private String majorId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
