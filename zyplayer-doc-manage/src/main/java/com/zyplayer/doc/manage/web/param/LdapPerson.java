package com.zyplayer.doc.manage.web.param;

import lombok.*;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;

/**
 * 域账号用户信息
 * 参考项目：https://gitee.com/durcframework/torna，类：cn.torna.service.login.form.impl.LdapUser
 *
 * @author 暮光：城中城
 * @since 2021年8月2日
 */
@Data
@Entry(objectClasses = "inetOrgPerson")
public class LdapPerson {

	/**
	 * 用户ID
	 */
	@DnAttribute(value = "uid")
	private String uid;

	/**
	 * 用户名
	 */
	@Attribute(name = "displayName")
	private String displayName;

	/**
	 * 邮箱
	 */
	@Attribute(name = "mail")
	private String mail;

}
