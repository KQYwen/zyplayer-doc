package com.zyplayer.doc.manage.framework.interceptor;

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
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		long startTime = startTimeThreadLocal.get();
		long totalTime = System.currentTimeMillis() - startTime;// 结束时间
		logger.error("总耗时：{}ms  URI：{}", totalTime, request.getRequestURI());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object haddler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 记录请求信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		startTimeThreadLocal.set(System.currentTimeMillis());
		return true;
	}

}
