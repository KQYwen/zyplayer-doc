package com.zyplayer.doc.db.framework.db.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * 表字段注释信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Data
public class TableColumnDescDto {
	@ColumnWidth(20)
	@ExcelProperty("表名")
	private String tableName;

	@ColumnWidth(20)
	@ExcelProperty("字段名")
	private String name;

	@ColumnWidth(15)
	@ExcelProperty("是否自增")
	private String selfIncrement;

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
	@ExcelProperty("小数点")
	private String numericScale;

	@ColumnWidth(10)
	@ExcelProperty("主键")
	private String primaryKey;

	@ColumnWidth(80)
	@ExcelProperty("注释")
	private String description;
}
