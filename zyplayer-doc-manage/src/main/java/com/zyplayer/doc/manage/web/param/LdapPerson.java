package com.zyplayer.doc.manage.web.param;

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
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
}
