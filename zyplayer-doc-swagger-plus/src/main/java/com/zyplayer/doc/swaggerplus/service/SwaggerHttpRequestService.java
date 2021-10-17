package com.zyplayer.doc.swaggerplus.service;

import cn.hutool.http.HttpRequest;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerGlobalParam;
import com.zyplayer.doc.data.service.manage.SwaggerGlobalParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SwaggerHttpRequestService {
	
	@Resource
	private SwaggerGlobalParamService swaggerGlobalParamService;
	
	public String requestUrl(HttpServletRequest request, String docUrl) {
		List<SwaggerGlobalParam> globalParamList = swaggerGlobalParamService.getGlobalParamList();
		Map<String, Object> globalFormParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 1))
				.collect(Collectors.toMap(SwaggerGlobalParam::getParamKey, SwaggerGlobalParam::getParamValue));
		Map<String, String> globalHeaderParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 2))
				.collect(Collectors.toMap(SwaggerGlobalParam::getParamKey, SwaggerGlobalParam::getParamValue));
		
		String resultStr = HttpRequest.get(docUrl)
				.form(globalFormParamMap)
				.header("Accept", "application/json, text/javascript, */*; q=0.01")
				.header("User-Agent", request.getHeader("User-Agent"))
				.addHeaders(globalHeaderParamMap)
				.cookie(this.getHttpCookie(request))
				.timeout(5000).execute().body();
		return resultStr;
	}
	
	private List<HttpCookie> getHttpCookie(HttpServletRequest request) {
		List<HttpCookie> httpCookies = new LinkedList<>();
		for (Cookie cookie : request.getCookies()) {
			HttpCookie httpCookie = new HttpCookie(cookie.getName(), cookie.getValue());
			httpCookie.setDomain(cookie.getDomain());
			httpCookie.setPath(cookie.getPath());
			httpCookie.setMaxAge(cookie.getMaxAge());
			httpCookies.add(httpCookie);
		}
		return httpCookies;
	}
}
