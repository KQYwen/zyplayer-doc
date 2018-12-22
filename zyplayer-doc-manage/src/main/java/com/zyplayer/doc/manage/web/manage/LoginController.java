package com.zyplayer.doc.manage.web.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyplayer.doc.core.json.DocResponseJson;

@RestController
public class LoginController {
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@GetMapping(value = "/login")
	public ModelAndView loginPage(HttpServletRequest request) {
		return new ModelAndView("/statics/manage/login.html");
	}
	
//	@PostMapping(value = "/logout")
//	public DocResponseJson<Object> logout(HttpServletRequest request) {
//
//		return DocResponseJson.ok();
//	}
	
	/**
	 * 如果是访问受限页面后，跳转到登录页的，则在targetUrl保存之前受限页面的路径，供页面调用
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/login/success")
	public DocResponseJson<String> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = null;
		if (savedRequest != null) {
			targetUrl = savedRequest.getRedirectUrl();
		}
		if (StringUtils.isBlank(targetUrl)) {
			targetUrl = "/";
		}
		return DocResponseJson.ok(targetUrl);
	}
	
	/**
	 * 获取异常信息返回给页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/login/failure")
	public DocResponseJson<String> loginFailure(HttpServletRequest request, HttpServletResponse response) {
		AuthenticationException ae = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		return DocResponseJson.warn(ae.getMessage());
	}
}