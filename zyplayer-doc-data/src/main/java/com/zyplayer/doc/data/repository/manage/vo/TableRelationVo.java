package com.zyplayer.doc.data.repository.manage.vo;

import lombok.*;

import java.util.List;

/**
 * 表关系结构
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
@Data
public class TableRelationVo {
	private String dbName;
	private String tableName;
	// name和columnName是一个，name给前端使用的
	private String name;
	private String columnName;
	private Integer nodeType;
	private List<TableRelationVo> children;

}
