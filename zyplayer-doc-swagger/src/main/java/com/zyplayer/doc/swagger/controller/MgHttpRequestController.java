package com.zyplayer.doc.swagger.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.swagger.controller.param.HttpRequestParam;
import com.zyplayer.doc.swagger.controller.vo.HttpCookieVo;
import com.zyplayer.doc.swagger.controller.vo.HttpHeaderVo;
import com.zyplayer.doc.swagger.controller.vo.HttpRequestVo;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 后台代理网络请求的控制器
 *
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@RestController
@RequestMapping("/swagger-mg-ui/http")
public class MgHttpRequestController {

	@Resource
	MgStorageService mgStorageService;

	@PostMapping(value = "/request")
	public DocResponseJson<HttpRequestVo> post(HttpRequestParam param) {
		String paramUrl = param.getUrl();
		List<String> whiteDomain = mgStorageService.getProxyRequestWhiteDomain();
		if (whiteDomain == null || whiteDomain.isEmpty()) {
			return DocResponseJson.warn("未设置代理请求白名单，不能代理请求");
		}
		paramUrl = paramUrl.replace("http://", "").replace("https://", "");
		String regexStr = paramUrl.substring(0, paramUrl.indexOf("/"));
		long inWhiteList = whiteDomain.stream().filter(regexStr::matches).count();
		if (inWhiteList <= 0) {
			return DocResponseJson.warn("该域名不在白名单内，不能代理请求");
		}
		HttpRequest request = param.createRequest();
		HttpResponse response;
		try{
			response = request.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("请求失败，请检查域名是否正确");
		}
		HttpRequestVo httpRequestVo = new HttpRequestVo();
		httpRequestVo.setData(response.body());
		httpRequestVo.setStatus(response.getStatus());
		List<HttpCookie> cookies = response.getCookies();
		if (cookies != null && cookies.size() > 0) {
			List<HttpCookieVo> cookie = cookies.stream().map(val -> new HttpCookieVo(val.getName(), val.getValue())).collect(Collectors.toList());
			httpRequestVo.setCookie(cookie);
		}
		Map<String, List<String>> headers = response.headers();
		if (headers != null && headers.size() > 0) {
			List<HttpHeaderVo> header = new ArrayList<>(headers.size());
			for (Entry<String, List<String>> httpHeader : headers.entrySet()) {
				HttpHeaderVo vo = new HttpHeaderVo();
				vo.setName(httpHeader.getKey());
				vo.setValue(String.join(";", httpHeader.getValue()));
				header.add(vo);
			}
			httpRequestVo.setHeader(header);
		}
		return DocResponseJson.ok(httpRequestVo);
	}
}
