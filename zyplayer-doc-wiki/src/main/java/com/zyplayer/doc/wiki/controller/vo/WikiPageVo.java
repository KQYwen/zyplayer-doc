package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiPage;

import java.util.List;

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
