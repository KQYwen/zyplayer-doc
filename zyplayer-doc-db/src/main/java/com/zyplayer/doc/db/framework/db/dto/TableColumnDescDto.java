package com.zyplayer.doc.db.framework.db.dto;

public class TableColumnDescDto {
	private String tableName;
	private String name;
	private String isidenity;
	private String type;
	private String nullable;
	private String length;
	private String ispramary;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsidenity() {
		return isidenity;
	}

	public void setIsidenity(String isidenity) {
		this.isidenity = isidenity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getIspramary() {
		return ispramary;
	}

	public void setIspramary(String ispramary) {
		this.ispramary = ispramary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
