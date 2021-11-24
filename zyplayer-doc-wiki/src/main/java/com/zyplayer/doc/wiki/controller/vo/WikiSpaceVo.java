package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;

/**
 * wiki空间信息
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
public class WikiSpaceVo extends WikiSpace {
	
	private Integer favorite;
	
	public Integer getFavorite() {
		return favorite;
	}
	
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
}
