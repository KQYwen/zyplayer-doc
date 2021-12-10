package com.zyplayer.doc.data.config.security;

import java.util.List;

/**
 * 登录用户信息
 *
 * @author 暮光：城中城
 * @since 2018-12-02
 */
public class DocUserDetails {
	private Long userId;
	private String username;
	private String password;
	private boolean enabled;
	private List<UserAuthVo> userAuthList;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<UserAuthVo> getUserAuthList() {
		return userAuthList;
	}
	
	public void setUserAuthList(List<UserAuthVo> userAuthList) {
		this.userAuthList = userAuthList;
	}
	
	public DocUserDetails(Long userId, String username) {
		this.userId = userId;
		this.username = username;
	}
	
	public DocUserDetails(Long userId, String username, String password, boolean enabled) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	public DocUserDetails(Long userId, String username, String password, boolean enabled, List<UserAuthVo> userAuthList) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userAuthList = userAuthList;
	}
	
	@Override
	public String toString() {
		return "DocUserDetails{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", userAuthList=" + userAuthList +
				'}';
	}
}
