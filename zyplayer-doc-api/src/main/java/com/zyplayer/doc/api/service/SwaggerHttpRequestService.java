package com.zyplayer.doc.api.service;

import cn.hutool.core.io.resource.BytesResource;
import cn.hutool.core.io.resource.MultiResource;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.zyplayer.doc.api.controller.ApiPoxyRequestController;
import com.zyplayer.doc.api.controller.param.ProxyRequestParam;
import com.zyplayer.doc.api.controller.vo.HttpCookieVo;
import com.zyplayer.doc.api.controller.vo.HttpHeaderVo;
import com.zyplayer.doc.api.controller.vo.ProxyRequestResultVo;
import com.zyplayer.doc.api.framework.utils.SwaggerDocUtil;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.repository.manage.entity.ApiGlobalParam;
import com.zyplayer.doc.data.service.manage.ApiGlobalParamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * swagger请求服务
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
@Service
public class SwaggerHttpRequestService {
	private static Logger logger = LoggerFactory.getLogger(SwaggerHttpRequestService.class);
	
	@Resource
	private ApiGlobalParamService apiGlobalParamService;
	
	private static final Map<String, Method> requestMethodMap = Stream.of(Method.values()).collect(Collectors.toMap(val -> val.name().toLowerCase(), val -> val));
	
	List<String> domainHeaderKeys = Arrays.asList("referer", "origin");
	List<String> needRequestHeaderKeys = Arrays.asList("user-agent");
	
	/**
	 * 请求真实的swagger文档内容
	 *
	 * @author 暮光：城中城
	 * @since 2021-11-04
	 */
	public String requestSwaggerUrl(HttpServletRequest request, Long docId, String docUrl, String docDomain) {
		List<ApiGlobalParam> globalParamList = apiGlobalParamService.getGlobalParamList(docId);
		Map<String, Object> globalFormParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 1))
				.collect(Collectors.toMap(ApiGlobalParam::getParamKey, ApiGlobalParam::getParamValue, (val1, val2) -> val1));
		Map<String, String> globalHeaderParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 2))
				.collect(Collectors.toMap(ApiGlobalParam::getParamKey, ApiGlobalParam::getParamValue, (val1, val2) -> val1));
		Map<String, String> globalCookieParamMap = globalParamList.stream().filter(item -> Objects.equals(item.getParamType(), 3))
				.collect(Collectors.toMap(ApiGlobalParam::getParamKey, ApiGlobalParam::getParamValue, (val1, val2) -> val1));
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
	public void proxyDownload(HttpServletRequest request, HttpServletResponse response, ProxyRequestParam requestParam) {
		try {
			HttpResponse httpResponse = this.getHttpResponse(request, requestParam);
			Map<String, List<String>> responseHeaders = httpResponse.headers();
			if (MapUtils.isNotEmpty(responseHeaders)) {
				for (Map.Entry<String, List<String>> httpHeader : responseHeaders.entrySet()) {
					response.addHeader(httpHeader.getKey(), String.join(";", httpHeader.getValue()));
				}
			}
			httpResponse.writeBody(response.getOutputStream(), true, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			HttpResponse httpResponse = getHttpResponse(request, requestParam);
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
	
	private HttpResponse getHttpResponse(HttpServletRequest request, ProxyRequestParam requestParam) {
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
		// 文件参数
		if (request instanceof StandardMultipartHttpServletRequest) {
			StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
			Iterator<String> fileNames = multipartRequest.getFileNames();
			while (fileNames.hasNext()) {
				String fileName = fileNames.next();
				String originKey = fileName.replace("_file_", "");
				List<MultipartFile> fileList = multipartRequest.getFiles(fileName);
				try {
					if (fileList.size() > 1) {
						MultiResource multiResource = new MultiResource();
						for (MultipartFile file : fileList) {
							multiResource.add(new BytesResource(file.getBytes(), file.getOriginalFilename()));
						}
						httpRequest.form(originKey, multiResource);
					} else if (fileList.size() > 0) {
						MultipartFile multipartFile = fileList.get(0);
						httpRequest.form(originKey, multipartFile.getBytes(), multipartFile.getOriginalFilename());
					}
				} catch (IOException e) {
					logger.error("读取上传的文件失败", e);
				}
			}
		}
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
		return httpRequest.timeout(10000).execute();
	}
	
	/**
	 * 获取http的cookie
	 *
	 * @author 暮光：城中城
	 * @since 2021-11-04
	 */
	private List<HttpCookie> getHttpCookie(HttpServletRequest request, Map<String, String> globalCookieParamMap, String headerCookie) {
		List<HttpCookie> httpCookies = new LinkedList<>();
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				httpCookies.add(new HttpCookie(cookie.getName(), cookie.getValue()));
			}
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
