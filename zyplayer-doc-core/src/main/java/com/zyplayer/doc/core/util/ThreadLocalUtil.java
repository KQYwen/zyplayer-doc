package com.zyplayer.doc.core.util;

import javax.servlet.http.HttpServletRequest;

public class ThreadLocalUtil {
	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
	
	public static void setHttpServletRequest(HttpServletRequest request) {
		ThreadLocalUtil.request.set(request);
	}
	
	public static HttpServletRequest getHttpServletRequest() {
		return ThreadLocalUtil.request.get();
	}
	
	public static void clean() {
		ThreadLocalUtil.request.remove();
	}
	
}
