package com.zyplayer.doc.manage.web.vo;

import com.zyplayer.doc.data.repository.manage.entity.UserInfo;

/**
 * 用户授权信息
 *
 * @author 暮光：城中城
 * @since 2018-12-05
 */
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
