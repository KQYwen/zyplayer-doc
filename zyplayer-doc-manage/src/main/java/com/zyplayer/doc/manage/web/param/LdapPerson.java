package com.zyplayer.doc.manage.web.param;

import lombok.Data;

/**
 * 域账号用户信息
 * 参考项目：https://gitee.com/durcframework/torna，类：cn.torna.service.login.form.impl.LdapUser
 *
 * @author 暮光：城中城
 * @since 2021年8月2日
 */
@Data
public class LdapPerson {
	
	/**
	 * 用户ID
	 */
	private String uid;
	
	/**
	 * 用户名
	 */
	private String displayName;
	
	/**
	 * 邮箱
	 */
	private String mail;
}
