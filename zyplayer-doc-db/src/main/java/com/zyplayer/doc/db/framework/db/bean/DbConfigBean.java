package com.zyplayer.doc.db.framework.db.bean;

/**
 * 数据库配置信息
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public class DbConfigBean {
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	public String getDriverClassName() {
		return driverClassName;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
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
}
