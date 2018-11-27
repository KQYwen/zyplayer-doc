package com.mg.swagger.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mg.swagger.controller.param.HttpRequestParam;
import com.mg.swagger.framework.json.MgUiResponseJson;
import com.mg.swagger.framework.json.ResponseJson;

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
	public ResponseJson post(HttpRequestParam param) {
		HttpRequest request = param.createRequest();
		HttpResponse execute = request.execute();
		return MgUiResponseJson.ok(execute);
	}
}
