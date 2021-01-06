package com.zyplayer.doc.db.framework.db.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

public class TableColumnDescDto {
	@ColumnWidth(20)
	@ExcelProperty("表名")
	private String tableName;
	
	@ColumnWidth(20)
	@ExcelProperty("字段名")
	private String name;
	
	@ColumnWidth(15)
	@ExcelProperty("是否自增")
	private String isidenity;
	
	@ColumnWidth(20)
	@ExcelProperty("类型")
	private String type;
	
	@ColumnWidth(10)
	@ExcelProperty("空值")
	private String nullable;
	
	@ColumnWidth(10)
	@ExcelProperty("长度")
	private String length;
	
	@ColumnWidth(10)
	@ExcelProperty("主键")
	private String ispramary;
	
	@ColumnWidth(80)
	@ExcelProperty("注释")
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
