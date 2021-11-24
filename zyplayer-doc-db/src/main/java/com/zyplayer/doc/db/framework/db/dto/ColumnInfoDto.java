package com.zyplayer.doc.db.framework.db.dto;

/**
 * 字段信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public class ColumnInfoDto {
	private String isNullable;
	private String columnType;
	private String columnDefault;
	private String extra;

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

}
