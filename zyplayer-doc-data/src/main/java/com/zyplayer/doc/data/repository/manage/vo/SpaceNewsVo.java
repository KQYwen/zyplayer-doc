package com.zyplayer.doc.data.repository.manage.vo;

import java.util.Date;

public class SpaceNewsVo {
	private Long spaceId;
	private Long pageId;
	private Integer zanNum;
	private Integer viewNum;
	private String createUserName;
	private String updateUserName;
	private Date createTime;
	private Date updateTime;
	
	private String spaceName;
	private String pageTitle;
	private String previewContent;
	
	public Long getPageId() {
		return pageId;
	}
	
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	
	public Integer getZanNum() {
		return zanNum;
	}
	
	public void setZanNum(Integer zanNum) {
		this.zanNum = zanNum;
	}
	
	public Integer getViewNum() {
		return viewNum;
	}
	
	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}
	
	public String getCreateUserName() {
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getUpdateUserName() {
		return updateUserName;
	}
	
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getSpaceName() {
		return spaceName;
	}
	
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	
	public String getPageTitle() {
		return pageTitle;
	}
	
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	public String getPreviewContent() {
		return previewContent;
	}
	
	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}
	
	public Long getSpaceId() {
		return spaceId;
	}
	
	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}
}
