package com.zyplayer.doc.db.controller.param;

import lombok.Data;

/**
 * 数据预览查询参数
 *
 * @author 暮光：城中城
 * @since 2021-05-20
 */
@Data
public class DataViewParam {
	// 基本信息
	private String executeId;
	private Long sourceId;
	private Integer pageSize;
	private Integer pageNum;
	private String dbName;
	private String tableName;
	// 保留的列json，多个使用英文逗号分隔，{"user_info": "id,name,age"}
	private String retainColumnJson;
	// 查询的条件json，{"user_info": "id > 0"}
	private String conditionJson;
	// 哪些列作为update语句的更新条件json，{"user_info": "id,name"}，用于update语句拼where条件使用
	private String conditionColumnJson;
	// 解析之后设置的，待使用
	private String condition;
	private String retainColumn;
	private String conditionColumn;
	// 下载多张数据表
	private String tableNames;
	private String downloadType;
	private Integer dropTableFlag;
	private Integer createTableFlag;
	private Integer downloadFileType;
	// 数据查询时使用，导出暂不支持排序
	private String orderColumn;
	private String orderType;

	public Integer getOffset() {
		return ((this.pageNum - 1) * this.pageSize);
	}
}
