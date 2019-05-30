package com.zyplayer.doc.manage.framework.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class DocUserFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpRequest.getCookies();
		boolean haveCtx = false;
		Object ctxObj = httpRequest.getServletContext().getAttribute("ctx");
		String ctxStr = String.valueOf(ctxObj);
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("ctx".equals(cookie.getName()) && ctxStr.equals(cookie.getValue())) {
					haveCtx = true;
					break;
				}
			}
		}
		if (!haveCtx) {
			// 前后端分离的，前段拿不到项目名，直接写/是不对的，只有后端放到cookie里给前端
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			Cookie cookieAdd = new Cookie("ctx", ctxStr);
			cookieAdd.setPath("/");
			httpResponse.addCookie(cookieAdd);
		}
		chain.doFilter(httpRequest, response);
	}

	@Override
	public void destroy() {
	}

}
