package org.dromara.zyplayer.data.service.params;

import lombok.Data;

@Data
public class PageDeleteParam {
	private Long pageId;
	private Integer delFlag;
	private Long spaceId;
}
