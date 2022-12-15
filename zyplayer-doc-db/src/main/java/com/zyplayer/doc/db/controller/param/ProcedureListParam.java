package com.zyplayer.doc.db.controller.param;

import lombok.Data;

/**
 * 存储过程列表查询参数
 *
 * @author 暮光：城中城
 * @since 2021-04-25
 */
@Data
public class ProcedureListParam {
	private Long sourceId;
	private String dbName;
	private Integer pageNum;
	private Integer pageSize;
	private Integer offset;
	private String name;
	private String type;
}
