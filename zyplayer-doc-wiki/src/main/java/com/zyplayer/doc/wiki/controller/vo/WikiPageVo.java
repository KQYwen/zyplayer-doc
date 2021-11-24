package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiPage;

import java.util.List;

/**
 * wiki页面信息
 *
 * @author 暮光：城中城
 * @since 2019-03-11
 */
public class WikiPageVo extends WikiPage {
	private String path;
	private List<WikiPageVo> children;
	
	public List<WikiPageVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<WikiPageVo> children) {
		this.children = children;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
