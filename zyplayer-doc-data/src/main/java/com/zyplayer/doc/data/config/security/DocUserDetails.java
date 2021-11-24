package com.zyplayer.doc.data.config.security;

import java.util.Set;

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
	private Set<String> authorities;
	
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
	
	public Set<String> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
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
	
	public DocUserDetails(Long userId, String username, String password, boolean enabled, Set<String> authorities) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authorities = authorities;
	}
	
	@Override
	public String toString() {
		return "DocUserDetails{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", authorities=" + authorities +
				'}';
	}
}
