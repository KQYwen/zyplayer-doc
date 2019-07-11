package com.zyplayer.doc.data.repository.manage.param;

import java.util.List;

public class SearchByEsParam {
	private Long spaceId;
	private String keywords;
	private Integer pageNum;
	private Integer pageSize;
	private Integer newsType;
	private List<Long> spaceIds;
	
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
	
	public Integer getNewsType() {
		return newsType;
	}
	
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	
	public List<Long> getSpaceIds() {
		return spaceIds;
	}
	
	public void setSpaceIds(List<Long> spaceIds) {
		this.spaceIds = spaceIds;
	}
}
