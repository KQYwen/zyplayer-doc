package com.zyplayer.doc.swaggerplus.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerGlobalParam;
import com.zyplayer.doc.data.service.manage.SwaggerGlobalParamService;
import com.zyplayer.doc.swaggerplus.controller.param.ProxyRequestParam;
import com.zyplayer.doc.swaggerplus.controller.vo.HttpCookieVo;
import com.zyplayer.doc.swaggerplus.controller.vo.HttpHeaderVo;
import com.zyplayer.doc.swaggerplus.controller.vo.ProxyRequestResultVo;
import com.zyplayer.doc.swaggerplus.framework.utils.SwaggerDocUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SwaggerHttpRequestService {
	
	@Resource
	private SwaggerGlobalParamService swaggerGlobalParamService;
	
	private static final Map<String, Method> requestMethodMap = Stream.of(Method.values()).collect(Collectors.toMap(val -> val.name().toLowerCase(), val -> val));
	
	List<String> domainHeaderKeys = Arrays.asList("referer", "origin");
	List<String> needRequestHeaderKeys = Arrays.asList("user-agent");
	
	/**
	 * 请求真实的swagger文档内容
	 *
	 * @author 暮光：城中城
	 * @since 2021-11-04
	 */
	public String requestSwaggerUrl(HttpServletRequest request, String docUrl, String docDomain) {
		List<SwaggerGlobalParam> globalParamList = swaggerGlobalParamService.getGlobalParamList();
		Map<String, Object> globalFormParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 1))
				.collect(Collectors.toMap(SwaggerGlobalParam::getParamKey, SwaggerGlobalParam::getParamValue));
		Map<String, String> globalHeaderParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 2))
				.collect(Collectors.toMap(SwaggerGlobalParam::getParamKey, SwaggerGlobalParam::getParamValue));
		Map<String, String> globalCookieParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 3))
				.collect(Collectors.toMap(SwaggerGlobalParam::getParamKey, SwaggerGlobalParam::getParamValue));
		Map<String, String> requestHeaders = this.getHttpHeader(request, globalHeaderParamMap);
		if (StringUtils.isNotBlank(docDomain)) {
			domainHeaderKeys.forEach(key -> requestHeaders.put(key, docDomain));
			requestHeaders.put("host", SwaggerDocUtil.getDomainHost(docDomain));
		}
		// 执行请求
		String resultStr = HttpRequest.get(docUrl)
				.form(globalFormParamMap)
				.addHeaders(requestHeaders)
				.header("Accept", "application/json, text/javascript, */*; q=0.01")
				.cookie(this.getHttpCookie(request, globalCookieParamMap, null))
				.timeout(10000).execute().body();
		return resultStr;
	}
	
	/**
	 * 执行代理请求
	 *
	 * @author 暮光：城中城
	 * @since 2021-11-04
	 */
	public ProxyRequestResultVo proxyRequest(HttpServletRequest request, ProxyRequestParam requestParam) {
		ProxyRequestResultVo resultVo = new ProxyRequestResultVo();
		long startTime = System.currentTimeMillis();
		try {
			// 执行请求
			Method method = requestMethodMap.get(requestParam.getMethod());
			if (method == null) {
				throw new ConfirmException("不支持的请求方式：" + requestParam.getMethod());
			}
			HttpRequest httpRequest = HttpUtil.createRequest(method, requestParam.getUrl());
			// header获取
			Map<String, String> headerParam = new HashMap<>();
			requestParam.getHeaderParamData().forEach(item -> headerParam.put(item.getCode(), item.getValue()));
			Map<String, String> requestHeaders = this.getHttpHeader(request, headerParam);
			if (StringUtils.isNotBlank(requestParam.getHost())) {
				domainHeaderKeys.forEach(key -> requestHeaders.put(key, requestParam.getHost()));
				requestHeaders.put("host", SwaggerDocUtil.getDomainHost(requestParam.getHost()));
			}
			// http自带参数
			httpRequest.addHeaders(requestHeaders);
			// 用户输入的参数
			requestParam.getFormParamData().forEach(data -> httpRequest.form(data.getCode(), data.getValue()));
			requestParam.getFormEncodeParamData().forEach(data -> httpRequest.form(data.getCode(), data.getValue()));
			// cookie参数
			Map<String, String> cookieParam = new HashMap<>();
			String headerCookie = headerParam.getOrDefault("Cookie", headerParam.get("cookie"));
			requestParam.getCookieParamData().forEach(item -> cookieParam.put(item.getCode(), item.getValue()));
			httpRequest.cookie(this.getHttpCookie(request, cookieParam, headerCookie));
			if (StringUtils.isNotBlank(requestParam.getBodyParam())) {
				httpRequest.body(requestParam.getBodyParam());
			}
			// 强制设置类型，貌似不用刻意设置，如果写的application/json，参数是表单，传过去收不到值，先注释这个
//			if (StringUtils.isNotBlank(requestParam.getContentType())) {
//				httpRequest.contentType(requestParam.getContentType());
//			}
			// 执行请求
			HttpResponse httpResponse = httpRequest.timeout(10000).execute();
			resultVo.setData(httpResponse.body());
			resultVo.setStatus(httpResponse.getStatus());
			resultVo.setContentLength(httpResponse.bodyBytes().length);
			// 设置返回的cookies
			List<HttpCookie> responseCookies = httpResponse.getCookies();
			if (CollectionUtils.isNotEmpty(responseCookies)) {
				resultVo.setCookies(responseCookies.stream().map(val -> new HttpCookieVo(val.getName(), val.getValue())).collect(Collectors.toList()));
			}
			// 设置返回的headers
			Map<String, List<String>> responseHeaders = httpResponse.headers();
			if (MapUtils.isNotEmpty(responseHeaders)) {
				List<HttpHeaderVo> headerList = new ArrayList<>(responseHeaders.size());
				for (Map.Entry<String, List<String>> httpHeader : responseHeaders.entrySet()) {
					HttpHeaderVo vo = new HttpHeaderVo();
					vo.setName(httpHeader.getKey());
					vo.setValue(String.join(";", httpHeader.getValue()));
					headerList.add(vo);
				}
				resultVo.setHeaders(headerList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVo.setErrorMsg(e.getMessage());
		}
		resultVo.setUseTime(System.currentTimeMillis() - startTime);
		return resultVo;
	}
	
	/**
	 * 获取http的cookie
	 *
	 * @author 暮光：城中城
	 * @since 2021-11-04
	 */
	private List<HttpCookie> getHttpCookie(HttpServletRequest request, Map<String, String> globalCookieParamMap, String headerCookie) {
		List<HttpCookie> httpCookies = new LinkedList<>();
		for (Cookie cookie : request.getCookies()) {
			httpCookies.add(new HttpCookie(cookie.getName(), cookie.getValue()));
		}
		if (StringUtils.isNotBlank(headerCookie)) {
			Arrays.stream(headerCookie.split(";")).map(String::trim).forEach(cookie -> {
				String[] cookieArr = StringUtils.split(cookie, "=", 2);
				if (ArrayUtils.getLength(cookieArr) == 2) {
					httpCookies.add(new HttpCookie(cookieArr[0], cookieArr[1]));
				}
			});
		}
		if (MapUtils.isNotEmpty(globalCookieParamMap)) {
			globalCookieParamMap.forEach((key, value) -> httpCookies.add(new HttpCookie(key, value)));
		}
		return httpCookies;
	}
	
	/**
	 * 获取http的header
	 *
	 * @author 暮光：城中城
	 * @since 2021-11-04
	 */
	private Map<String, String> getHttpHeader(HttpServletRequest request, Map<String, String> globalHeaderParamMap) {
		Map<String, String> headerParamMap = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = StringUtils.lowerCase(headerNames.nextElement());
			if (needRequestHeaderKeys.contains(headerName)) {
				headerParamMap.put(headerName, request.getHeader(headerName));
			}
		}
		if (MapUtils.isNotEmpty(globalHeaderParamMap)) {
			headerParamMap.putAll(globalHeaderParamMap);
		}
		return headerParamMap;
	}
}
