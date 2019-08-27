package com.zyplayer.doc.swagger.framework.filter;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.swagger.controller.param.HttpRequestParam;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.constant.Consts;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;

/**
 * 请求代理过滤器
 * 使得所有的swagger-ui都可以查看所有的文档，目标是：文档大一统~
 *
 * @author 暮光：城中城
 * @since 2019年12月16日
 */
@Component
public class ZyplayerProxyFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(ZyplayerProxyFilter.class);
	
	@Autowired
	private MgStorageService mgStorageService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}
	
	@Override
	public void destroy() {
	
	}
	
	public static void main(String[] args) {
//		String requestUrl = "http://192.168.0.249:8082/zyplayer-doc-manage/zyplayer-proxy/1/v2/api-docs";
//		int proxyIndex = requestUrl.indexOf(Consts.ZYPLAYER_PROXY);
//		if (proxyIndex < 0) {
//			return;
//		}
//		String requestUrlEnd = requestUrl.substring(proxyIndex + Consts.ZYPLAYER_PROXY.length());
//		int idIndex = requestUrlEnd.indexOf("/");
//		String idStr = requestUrlEnd.substring(0, idIndex);
		
		String resource = "{\"swagger\":\"2.0\",\"host\":\"127.0.0.1:8999\",\"basePath\":\"/\",\"tags\":";
		resource = resource.replaceFirst("\"basePath\":\".*?\"", "\"basePath\":\"/xxxxx\"");
		System.out.println(resource);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String method = httpServletRequest.getMethod();
		String requestUrl = httpServletRequest.getRequestURL().toString();
		String bodyStr = IOUtils.toString(httpServletRequest.getInputStream(), "utf-8");
		
		int proxyIndex = requestUrl.indexOf(Consts.ZYPLAYER_PROXY);
		if (proxyIndex < 0) {
			// 不是拦截接口直接跳过
			chain.doFilter(request, response);
			return;
		}
		long startTime = System.currentTimeMillis();
		String requestUrlEnd = requestUrl.substring(proxyIndex + Consts.ZYPLAYER_PROXY.length());
		int idIndex = requestUrlEnd.indexOf("/");
		String proxyUrlEnd = requestUrlEnd.substring(idIndex);
		String idStr = requestUrlEnd.substring(0, idIndex);
		Integer docId = Integer.valueOf(idStr);
		
		String swaggerResourcesStr = mgStorageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		List<SwaggerResourcesInfoVo> resourcesInfoVoList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
		if (resourcesInfoVoList == null || resourcesInfoVoList.isEmpty()) {
			this.send(startTime, requestUrl, servletResponse, "文档为空，请刷新重试");
			return;
		}
		// 找到真正被代理的对象
		String proxyUrl = null;
		for (SwaggerResourcesInfoVo swaggerResourcesInfoVo : resourcesInfoVoList) {
			if (Objects.equals(swaggerResourcesInfoVo.getId(), docId)) {
				proxyUrl = swaggerResourcesInfoVo.getUrl();
				break;
			}
		}
		if (StringUtils.isBlank(proxyUrl)) {
			this.send(startTime, requestUrl, servletResponse, "未找到对应文档，请刷新重试");
			return;
		}
		// 组装URL
		proxyUrl = proxyUrl.replace(Consts.SWAGGER_RESOURCES, proxyUrlEnd);
		HttpRequestParam param = new HttpRequestParam();
		param.setUrl(proxyUrl);
		param.setMethod(method);
		HttpRequest httpRequest = param.createRequest();
		// 组装参数
		Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
		if (parameterMap != null && parameterMap.size() > 0) {
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				httpRequest.form(entry.getKey(), entry.getValue());
			}
		}
		// 请求体
		if (StringUtils.isNotBlank(bodyStr)) {
			httpRequest.body(bodyStr);
		}
		// header
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String element = headerNames.nextElement();
				httpRequest.header(element, httpServletRequest.getHeader(element));
			}
		}
		// cookies
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null && cookies.length > 0) {
			List<HttpCookie> httpCookieList = new LinkedList<>();
			for (int i = 0; i < cookies.length; i++) {
				HttpCookie httpCookie = new HttpCookie(cookies[i].getName(), cookies[i].getValue());
				httpCookieList.add(httpCookie);
			}
			httpRequest.cookie(httpCookieList.toArray(new HttpCookie[]{}));
		}
		String resultStr = httpRequest.execute().body();
		if (proxyUrl.contains(Consts.V2_API_DOCS)) {
			// "basePath":"/" 替换成 "basePath":"/zyplayer-doc-manage/zyplayer-proxy/2/"，使其走代理接口
			ServletContext servletContext = httpServletRequest.getServletContext();
			Object ctx = servletContext.getAttribute("ctx");
			ctx = (ctx + Consts.ZYPLAYER_PROXY + idStr).replaceAll("//", "/");
			resultStr = resultStr.replaceFirst("\"basePath\":\".*?\"", "\"basePath\":\"" + ctx + "\"");
			// 替换host为当前项目的
			String requestUrlHost = requestUrl.substring(0, proxyIndex + Consts.ZYPLAYER_PROXY.length());
			requestUrlHost = requestUrlHost.replace("http://", "");
			requestUrlHost = requestUrlHost.substring(0, requestUrlHost.indexOf("/"));
			resultStr = resultStr.replaceFirst("\"host\":\".*?\"", "\"host\":\"" + requestUrlHost + "\"");
		}
		this.send(startTime, proxyUrl, servletResponse, resultStr);
	}
	
	public void send(Long startTime, String requestUrl, HttpServletResponse response, String result) throws IOException {
		response.setStatus(200);
		// response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.getWriter().write(result);
		Long totalTime = System.currentTimeMillis() - startTime;
		logger.info("代理请求结束，总耗时：{}ms，URI：{}", totalTime, requestUrl);
	}
}
