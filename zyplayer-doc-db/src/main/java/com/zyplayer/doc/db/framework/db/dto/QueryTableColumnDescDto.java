package com.zyplayer.doc.db.framework.db.dto;

import lombok.Data;

/**
 * 表字段注释信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Data
public class QueryTableColumnDescDto {
	private String tableName;
	private String columnName;
	private String description;
}
