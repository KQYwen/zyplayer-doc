package com.zyplayer.doc.db.controller.param;

import lombok.Data;

/**
 *  查询参数
 *  @author 暮光：城中城
 *  @since 2021-05-20
 */
@Data
public class DataDownloadParam {
	private Long sourceId;
	private String dbName;
	private String tableName;
	private String downloadType;
	private String conditionColumn;
}
