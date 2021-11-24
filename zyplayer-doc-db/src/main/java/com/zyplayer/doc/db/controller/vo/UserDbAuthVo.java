package com.zyplayer.doc.db.controller.vo;

/**
 * 用户数据库授权信息
 *
 * @author 暮光：城中城
 * @since 2019-08-22
 */
public class UserDbAuthVo {
	private String userName;
	private Long userId;
	private Integer executeAuth;
	private Integer descEditAuth;
	private Integer procEditAuth;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Integer getExecuteAuth() {
		return executeAuth;
	}
	
	public void setExecuteAuth(Integer executeAuth) {
		this.executeAuth = executeAuth;
	}
	
	public Integer getDescEditAuth() {
		return descEditAuth;
	}
	
	public void setDescEditAuth(Integer descEditAuth) {
		this.descEditAuth = descEditAuth;
	}
	
	public Integer getProcEditAuth() {
		return procEditAuth;
	}
	
	public void setProcEditAuth(Integer procEditAuth) {
		this.procEditAuth = procEditAuth;
	}
}
