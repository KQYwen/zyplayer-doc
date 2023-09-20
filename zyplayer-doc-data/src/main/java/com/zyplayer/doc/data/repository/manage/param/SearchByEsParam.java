package com.zyplayer.doc.data.repository.manage.param;

import lombok.Data;

import java.util.List;

/**
 * 文档搜索参数
 *
 * @author 暮光：城中城
 * @since 2019-07-10
 */
@Data
public class SearchByEsParam {
	private Long spaceId;
	private String keywords;
	private Integer pageNum;
	private Integer pageSize;
	private Integer newsType;
	private List<Long> spaceIds;
	private Long dirId;
}
