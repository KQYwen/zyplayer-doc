package com.zyplayer.doc.data.config;

import com.zyplayer.doc.core.json.HttpConst;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录和跨域拦截器
 * @author 暮光：城中城
 * @since 2019年05月25日
 */
@Component
public class DocLoginOriginInterceptor implements HandlerInterceptor {
	
	@Value("${zyplayer.doc.manage.originDomainRegex:}")
	private String originDomainRegex;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) {
		// 清理用户信息
		DocUserUtil.clean();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object haddler, ModelAndView modelAndView) {
	}
	
	/**
	 * 记录请求信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
		// 指定域名可跨域访问
		if (StringUtils.isNotBlank(originDomainRegex)) {
			String origin = request.getHeader("Origin");
			if (StringUtils.isNotBlank(origin) && origin.toLowerCase().matches(originDomainRegex)) {
				response.setHeader("Access-Control-Allow-Origin", origin); // 允许访问的域
				response.setHeader("Access-Control-Allow-Methods", "HEAD,GET,POST,PUT,DELETE");// 允许GET、POST的外域请求
				response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许请求带cookie到服务器
				response.setContentType("application/json; charset=utf-8"); // 设定JSON格式标准输出、及编码
			}
		}
		// 清理用户信息
		DocUserUtil.clean();
		// 设置token
		String accessToken = getCookieValueByRequest(request, HttpConst.ACCESS_TOKEN);
		DocUserUtil.setAccessToken(accessToken);
		return true;
	}
	
	/**
	 * 获取cookie
	 *
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByRequest(HttpServletRequest request, String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		for (int i = 0; (cookies != null) && (i < cookies.length); i++) {
			Cookie cookie = cookies[i];
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}
	
	/**
	 * 获取cookie值
	 *
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValueByRequest(HttpServletRequest request, String name) {
		Cookie cookie = getCookieByRequest(request, name);
		return cookie == null ? null : cookie.getValue();
	}
}
