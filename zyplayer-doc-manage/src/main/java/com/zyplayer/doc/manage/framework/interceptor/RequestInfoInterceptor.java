package com.zyplayer.doc.manage.framework.interceptor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录当前请求信息
 */
@Component
public class RequestInfoInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(RequestInfoInterceptor.class);
	
	private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
	
	/**
	 * 把当前请求记录到下来
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) {
		long startTime = startTimeThreadLocal.get();
		long totalTime = System.currentTimeMillis() - startTime;// 结束时间
		logger.info("总耗时：{}ms，URI：{}", totalTime, request.getRequestURI());
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object haddler, ModelAndView modelAndView) {
	}
	
	/**
	 * 记录请求信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
		startTimeThreadLocal.set(System.currentTimeMillis());
		String originRegex = ".*\\.zyplayer\\.com(:\\d+|)$";
		String origin = request.getHeader("Origin");
		if (StringUtils.isNotBlank(origin) && origin.toLowerCase().matches(originRegex)) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			response.setHeader("Access-Control-Allow-Methods", "HEAD,GET,POST,PUT,DELETE");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setContentType("application/json; charset=utf-8");
		}
		return true;
	}
	
}
