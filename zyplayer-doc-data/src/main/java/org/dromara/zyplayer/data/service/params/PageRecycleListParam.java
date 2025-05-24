package org.dromara.zyplayer.data.service.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 回收站列表参数
 *
 * @author 暮光：城中城
 * @since 2023-05-01
 */
@Data
public class PageRecycleListParam implements Serializable {
	private Long spaceId;
	private Long pageNum;
	
}
