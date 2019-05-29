//package com.zyplayer.doc.manage.framework.config.security;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//
//import com.alibaba.fastjson.JSON;
//
//public class DocAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//	private static Logger logger = LoggerFactory.getLogger(DocAuthenticationSuccessHandler.class);
//
//	private RequestCache requestCache = new HttpSessionRequestCache();
//	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//		SavedRequest savedRequest = requestCache.getRequest(request, response);
//		String targetUrl = savedRequest.getRedirectUrl();
//		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
//		if (isAjax) {
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("url", targetUrl);
//			response.getWriter().print(JSON.toJSONString(result));
//			response.getWriter().flush();
//		} else {
//			redirectStrategy.sendRedirect(request, response, targetUrl);
//		}
//		logger.info("Redirecting to DefaultSavedRequest Url: " + targetUrl);
//	}
//
//}