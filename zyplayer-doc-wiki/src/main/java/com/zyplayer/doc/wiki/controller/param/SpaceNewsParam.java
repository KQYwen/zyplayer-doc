package com.zyplayer.doc.wiki.controller.param;

public class SpaceNewsParam {
	private Long spaceId;
	private Integer newsType;
	private Integer pageNum;
	private Integer pageSize;
	
	public Integer getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Long getSpaceId() {
		return spaceId;
	}
	
	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}
	
	public Integer getNewsType() {
		return newsType;
	}
	
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
}
