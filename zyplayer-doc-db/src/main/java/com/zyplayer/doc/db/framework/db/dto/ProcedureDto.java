package com.zyplayer.doc.db.framework.db.dto;

import lombok.Data;

/**
 * 存储过程信息
 *
 * @author 暮光：城中城
 * @since 2021-04-25
 */
@Data
public class ProcedureDto {
	private String db;
	private String name;
	private String type;
	private String definer;
	private String body;
	private String paramList;
	private String returns;
	private String created;
}
