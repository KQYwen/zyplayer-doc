package com.zyplayer.doc.db.framework.db.dto;

import lombok.Data;

/**
 * 表信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Data
public class TableInfoDto {
	private String dbName;
	private String tableName;
	private String tableComment;
	private String tableId;
}
