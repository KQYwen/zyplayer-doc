package com.zyplayer.doc.swagger.controller;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.swagger.controller.param.HttpRequestParam;
import com.zyplayer.doc.swagger.controller.vo.HttpCookieVo;
import com.zyplayer.doc.swagger.controller.vo.HttpHeaderVo;
import com.zyplayer.doc.swagger.controller.vo.HttpRequestVo;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * 后台代理网络请求的控制器
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@RestController
@RequestMapping("/swagger-mg-ui/http")
public class MgHttpRequestController {

	@PostMapping(value = "/request")
	public DocResponseJson<HttpRequestVo> post(HttpRequestParam param) {
		HttpRequest request = param.createRequest();
		HttpResponse response = request.execute();
		HttpRequestVo httpRequestVo = new HttpRequestVo();
		httpRequestVo.setData(response.body());
		httpRequestVo.setStatus(response.getStatus());
		List<HttpCookie> cookies = response.getCookies();
		if (cookies != null && cookies.size() > 0) {
			List<HttpCookieVo> cookie = cookies.stream().map(val -> {
				return new HttpCookieVo(val.getName(), val.getValue());
			}).collect(Collectors.toList());
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
