package com.zyplayer.doc.manage.web.manage.vo;

import com.zyplayer.doc.data.repository.manage.entity.UserInfo;

public class UserInfoAuthVo {
	private UserInfo userInfo;
	private UserAuthVo userAuth;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public UserAuthVo getUserAuth() {
		return userAuth;
	}
	
	public void setUserAuth(UserAuthVo userAuth) {
		this.userAuth = userAuth;
	}
}
