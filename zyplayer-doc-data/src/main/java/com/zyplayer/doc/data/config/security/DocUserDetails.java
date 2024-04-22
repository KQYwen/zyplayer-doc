package com.zyplayer.doc.data.config.security;

import lombok.Data;

import java.util.List;

/**
 * 登录用户信息
 *
 * @author 暮光：城中城
 * @since 2018-12-02
 */
@Data
public class DocUserDetails {
	private Long userId;
	private String username;
	private String password;
	private String accessToken;
	private boolean enabled;
	private List<UserAuthInfo> userAuthList;

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

	public DocUserDetails(Long userId, String username, String password, boolean enabled, List<UserAuthInfo> userAuthList) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userAuthList = userAuthList;
	}
}
