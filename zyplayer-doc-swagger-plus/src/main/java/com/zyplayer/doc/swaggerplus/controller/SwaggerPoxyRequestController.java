package com.zyplayer.doc.swaggerplus.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.swaggerplus.controller.param.ProxyRequestParam;
import com.zyplayer.doc.swaggerplus.controller.vo.ProxyRequestResultVo;
import com.zyplayer.doc.swaggerplus.service.SwaggerHttpRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数控制器
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
@RequestMapping("/doc-swagger/proxy")
public class SwaggerPoxyRequestController {
	private static Logger logger = LoggerFactory.getLogger(SwaggerPoxyRequestController.class);
	
	@Resource
	private SwaggerHttpRequestService swaggerHttpRequestService;
	
	/**
	 * 代理接口请求
	 *
	 * @return 请求参数
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/request")
	public ResponseJson<ProxyRequestResultVo> request(HttpServletRequest request, ProxyRequestParam requestParam) {
		ProxyRequestResultVo requestResult = swaggerHttpRequestService.proxyRequest(request, requestParam);
		return DocResponseJson.ok(requestResult);
	}
}
