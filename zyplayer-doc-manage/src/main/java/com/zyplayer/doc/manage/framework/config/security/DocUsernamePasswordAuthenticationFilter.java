package com.zyplayer.doc.manage.framework.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class DocUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	// 是否开启验证码功能
	private boolean isOpenValidateCode = false;

	public static final String VALIDATE_CODE = "validateCode";

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (isOpenValidateCode) {
			checkValidateCode(request);
		}
		return super.attemptAuthentication(request, response);
	}

	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionCode = this.obtainSessionValidateCode(session);
		// 让上一次的验证码失效
		session.setAttribute(VALIDATE_CODE, null);
		String parameterCode = this.obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(sessionCode) || !sessionCode.equalsIgnoreCase(parameterCode)) {
			throw new AuthenticationServiceException("验证码错误！");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

}