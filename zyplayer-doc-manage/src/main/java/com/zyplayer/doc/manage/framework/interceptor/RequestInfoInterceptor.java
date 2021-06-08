package com.zyplayer.doc.manage.framework.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.zyplayer.doc.core.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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
		Long startTime = startTimeThreadLocal.get();
		startTime = Optional.ofNullable(startTime).orElse(System.currentTimeMillis());
		long totalTime = System.currentTimeMillis() - startTime;// 结束时间
		String clientIP = ServletUtil.getClientIP(request);
		logger.info("IP：{}，总耗时：{}ms，URI：{}", clientIP, totalTime, request.getRequestURI());
		ThreadLocalUtil.clean();
		startTimeThreadLocal.remove();
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object haddler, ModelAndView modelAndView) {
	}
	
	/**
	 * 记录请求信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
		response.setContentType("application/json; charset=utf-8"); // 设定JSON格式标准输出、及编码
		startTimeThreadLocal.set(System.currentTimeMillis());
		ThreadLocalUtil.setHttpServletRequest(request);
		return true;
	}
	
}
