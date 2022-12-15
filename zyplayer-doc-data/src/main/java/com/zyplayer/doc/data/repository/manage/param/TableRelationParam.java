package com.zyplayer.doc.data.repository.manage.param;

import lombok.Data;

/**
 * 表关系请求参数
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
@Data
public class TableRelationParam {
	private Long sourceId;
	private String dbName;
	private String tableName;
	private String columnName;
	// 关系JSON，大概是：[{dbName: 'xxx', tableName: 'xxx', columnName: 'xxx'}]
	private String relation;
}
