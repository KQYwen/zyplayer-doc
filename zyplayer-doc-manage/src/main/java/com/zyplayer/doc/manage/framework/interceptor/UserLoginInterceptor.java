package com.zyplayer.doc.manage.framework.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.HttpConst;
import com.zyplayer.doc.core.util.ThreadLocalUtil;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import org.apache.catalina.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 用户登录拦截
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);
	
	@Value("${zyplayer.doc.manage.originDomainRegex:}")
	private String originDomainRegex;
	
	private final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) {
		Long startTime = startTimeThreadLocal.get();
		startTime = Optional.ofNullable(startTime).orElse(System.currentTimeMillis());
		long totalTime = System.currentTimeMillis() - startTime;
		String clientIP = ServletUtil.getClientIP(request);
		logger.info("IP：{}，总耗时：{}ms，URI：{}", clientIP, totalTime, request.getRequestURI());
		ThreadLocalUtil.clean();
		startTimeThreadLocal.remove();
		// 清理用户信息
		DocUserUtil.clean();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		startTimeThreadLocal.set(System.currentTimeMillis());
		ThreadLocalUtil.setHttpServletRequest(request);
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
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 清理用户信息
		DocUserUtil.clean();
		// 设置token
		Cookie cookie = ServletUtil.getCookie(request, HttpConst.ACCESS_TOKEN);
		String accessToken = (cookie == null) ? null : cookie.getValue();
		DocUserUtil.setAccessToken(accessToken);
		AuthMan authMan = ((HandlerMethod) handler).getMethod().getAnnotation(AuthMan.class);
		if (authMan == null) {
			authMan = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(AuthMan.class);
			if (authMan == null) {
				return true;
			}
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (currentUser == null) {
			String reason = "你访问的内容需要登录，请登录后再试";
			DocResponseJson.failure(HttpConst.TOKEN_TIMEOUT, reason).send(response);
			return false;
		}
		// 判断权限是否足够
		boolean haveAuth = DocUserUtil.haveAuth(authMan.value());
		if (haveAuth) {
			return true;
		}
		String reasonStr = "没有操作权限，请联系管理员";
		DocResponseJson.warn(reasonStr).send(response);
		return false;
	}
	
}
