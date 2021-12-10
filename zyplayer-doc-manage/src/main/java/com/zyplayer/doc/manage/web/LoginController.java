package com.zyplayer.doc.manage.web;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.config.security.UserAuthVo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.manage.web.param.LdapPerson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户登录控制器
 *
 * @author 暮光：城中城
 * @since 2018-12-08
 */
@RestController
public class LoginController {
	
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserAuthService userAuthService;
	@Resource
	private LdapTemplate ldapTemplate;
	
	// TODO 域账号登录，待测试
	@Value("${spring.ldap.domainName:}")
	private String ldapDomainName;
	@Value("${spring.ldap.enable:false}")
	private boolean ldapLoginEnable;
	
	/**
	 * 用户登录
	 */
	@PostMapping(value = "/login")
	public DocResponseJson<Object> login(String username, String password, HttpServletResponse response) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_no", username);
		queryWrapper.eq("del_flag", 0);
		UserInfo userInfo = userInfoService.getOne(queryWrapper);
		// 如果使用域账号登录
		if (ldapLoginEnable) {
			LdapPerson ldapPerson = this.getUserFromLdap(username, password);
			if (null == ldapPerson) {
				return DocResponseJson.warn("用户名或密码错误");
			}
			if (userInfo == null) {
				userInfo = this.ldapAutoRegister(ldapPerson);
			}
		} else {
			if (userInfo == null) {
				return DocResponseJson.warn("用户名'" + username + "'没有找到！");
			}
			String pwdMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
			if (!Objects.equals(userInfo.getPassword(), pwdMd5)) {
				return DocResponseJson.warn("用户名或密码错误");
			}
		}
		List<UserAuthVo> userAuthSet = userAuthService.getUserAuthSet(userInfo.getId());
		String accessToken = IdUtil.simpleUUID();
		DocUserDetails userDetails = new DocUserDetails(userInfo.getId(), userInfo.getUserName(), userInfo.getPassword(), true, userAuthSet);
		DocUserUtil.setCurrentUser(accessToken, userDetails);
		// 放入cookie，过期时间：24小时
		Cookie cookie = new Cookie("accessToken", accessToken);
		cookie.setPath("/");
		cookie.setDomain("zyplayer.com");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
		// 再搞一份当前域名的cookie
		cookie = new Cookie("accessToken", accessToken);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
		return DocResponseJson.ok();
	}
	
	/**
	 * 退出登录
	 */
	@PostMapping(value = "/logout")
	public DocResponseJson<Object> logout() {
		DocUserUtil.logout();
		return DocResponseJson.ok();
	}
	
	/**
	 * 域账户注册
	 */
	private UserInfo ldapAutoRegister(LdapPerson ldapPerson) {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(ldapPerson.getEmail());
		userInfo.setPassword("LDAP");
		userInfo.setUserName(ldapPerson.getName());
		userInfo.setUserNo(ldapPerson.getsAMAccountName());
		userInfo.setSex(1);
		userInfoService.save(userInfo);
		return userInfo;
	}
	
	/**
	 * 鉴别域账号中是否有该用户
	 */
	public LdapPerson getUserFromLdap(String username, String password) {
		if (StringUtils.endsWithIgnoreCase(username, ldapDomainName)) {
			username = username.replaceAll("(?i)" + ldapDomainName, "");
		}
		String userDn = username + ldapDomainName;
		DirContext dirContext = null;
		try {
			dirContext = ldapTemplate.getContextSource().getContext(userDn, password);
			List<LdapPerson> search = ldapTemplate.search(
					LdapQueryBuilder.query().where("objectClass").is("person").and("sAMAccountName").is(username),
					(AttributesMapper<LdapPerson>) attributes -> {
						LdapPerson person = new LdapPerson();
						person.setName(this.getAttributeValue(attributes.get("cn")));
						person.setsAMAccountName(this.getAttributeValue(attributes.get("sAMAccountName")));
						person.setEmail(this.getAttributeValue(attributes.get("userPrincipalName")));
						return person;
					});
			if (CollectionUtils.isNotEmpty(search)) {
				return search.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dirContext) {
				LdapUtils.closeContext(dirContext);
			}
		}
		return null;
	}
	
	/**
	 * 取值
	 */
	private String getAttributeValue(Attribute attribute) throws NamingException {
		if (attribute != null) {
			Object obj = attribute.get(0);
			return obj == null ? null : obj.toString();
		}
		return null;
	}
}
