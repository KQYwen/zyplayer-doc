package com.zyplayer.doc.wiki.controller.param;

public class SearchByEsParam {
	private Long spaceId;
	private String keywords;
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
	
	public Integer getStartIndex() {
		return (pageNum - 1) * pageSize;
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
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
