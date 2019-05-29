//package com.zyplayer.doc.manage.framework.config.security;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zyplayer.doc.core.json.DocResponseJson;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//	private static Logger logger = LoggerFactory.getLogger(CustomizeAuthenticationSuccessHandler.class);
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//		logger.info("AT onAuthenticationSuccess(...) function!");
//
//		WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//		logger.info("login--IP:" + details.getRemoteAddress());
//
//		SecurityContext context = SecurityContextHolder.getContext();
//		Authentication authentication1 = context.getAuthentication();
//		Object principal = authentication1.getPrincipal();
//		Object principal1 = authentication.getPrincipal();
//
//		String name = authentication.getName();
//		logger.info("login--name:" + name + " principal:" + principal + " principal1:" + principal1);
//
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//			Map<String, Object> map = new HashMap<>();
//			map.put("user", principal);
//			map.put("name", name);
//			out.append(JSONObject.toJSONString(DocResponseJson.ok(map)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				out.close();
//			}
//		}
//	}
//}