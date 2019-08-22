package com.zyplayer.doc.data.config.security;

import com.zyplayer.doc.data.utils.CacheUtil;

/**
 * 用户工具类
 * @author 暮光：城中城
 * @since 2019年05月25日
 */
public class DocUserUtil {
	private static ThreadLocal<DocUserDetails> DOC_USER_DETAILS = new ThreadLocal<>();
	private static ThreadLocal<String> ACCESS_TOKEN = new ThreadLocal<>();
	
	public static void setAccessToken(String accessToken) {
		DocUserUtil.ACCESS_TOKEN.set(accessToken);
	}
	
	public static boolean haveCustomAuth(String authName, String suffix) {
		return haveAuth(authName + suffix);
	}
	
	public static boolean haveAuth(String... authNames) {
		DocUserDetails currentUser = getCurrentUser();
		if (currentUser == null) {
			return false;
		}
		for (String authName : authNames) {
			if (!currentUser.getAuthorities().contains(authName)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取当前用户
	 *
	 * @return 用户信息
	 */
	public static DocUserDetails getCurrentUser() {
		DocUserDetails docUser = DOC_USER_DETAILS.get();
		if (docUser == null) {
			docUser = CacheUtil.get(ACCESS_TOKEN.get());
			if (docUser != null) {
				DOC_USER_DETAILS.set(docUser);
			}
		}
		return docUser;
	}
	
	/**
	 * 设置当前用户
	 */
	public static void setCurrentUser(String accessToken, DocUserDetails docUser) {
		DOC_USER_DETAILS.set(docUser);
		CacheUtil.put(accessToken, docUser);
	}
	
	/**
	 * 退出登录
	 */
	public static void logout() {
		CacheUtil.remove(ACCESS_TOKEN.get());
	}
	
	public static void clean() {
		DocUserUtil.DOC_USER_DETAILS.remove();
		DocUserUtil.ACCESS_TOKEN.remove();
	}
}
