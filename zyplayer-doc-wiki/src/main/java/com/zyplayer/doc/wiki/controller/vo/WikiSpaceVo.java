package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;

public class WikiSpaceVo extends WikiSpace {
	
	private Integer favorite;
	
	public Integer getFavorite() {
		return favorite;
	}
	
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
}
