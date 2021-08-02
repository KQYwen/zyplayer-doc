package com.zyplayer.doc.manage.framework.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configurable
public class LdapConfig {
	
	String url = "ldap://10.0.1.1:10389";
	String base = "dc=xx,dc=net";
	String userDn = "cn=Manager,dc=xx,dc=net";
	String password = "MKDSHYDNIS";
	
	/**
	 * 初始化
	 */
	@Bean
	public LdapTemplate getLdapTemplate() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(url);
		contextSource.setBase(base);
		contextSource.setUserDn(userDn);
		contextSource.setPassword(password);
		contextSource.setPooled(false);
		contextSource.afterPropertiesSet();
		return new LdapTemplate(contextSource);
	}
}
