package com.zyplayer.doc.api.controller.vo;

/**
 * 用户权限返回值对象
 *
 * @author 暮光：城中城
 * @since 2021年12月12日
 */
public class DocUserAuthVo {
	
	/**
	 * 权限类型
	 */
	private Integer authType;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 用户编号，用于登录等
	 */
	private String userNo;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 性别 0=女 1=男
	 */
	private Integer sex;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserNo() {
		return userNo;
	}
	
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getSex() {
		return sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public Integer getAuthType() {
		return authType;
	}
	
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
}
