package com.zyplayer.doc.api.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.api.controller.param.ProxyRequestParam;
import com.zyplayer.doc.api.controller.vo.ProxyRequestResultVo;
import com.zyplayer.doc.api.service.SwaggerHttpRequestService;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomNode;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomParams;
import com.zyplayer.doc.data.service.manage.ApiCustomNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求参数控制器
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
@RequestMapping("/doc-api/proxy")
public class ApiPoxyRequestController {
	private static Logger logger = LoggerFactory.getLogger(ApiPoxyRequestController.class);
	
	@Resource
	ApiCustomNodeService apiCustomNodeService;
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
		// 自建接口请求时保存信息
		if (requestParam.getNodeId() != null) {
			ApiCustomNode apiCustomNode = new ApiCustomNode();
			apiCustomNode.setNodeType(1);
			apiCustomNode.setId(requestParam.getNodeId());
			apiCustomNode.setDocId(requestParam.getDocId());
			apiCustomNode.setNodeName(requestParam.getApiName());
			ApiCustomParams apiCustomParams = new ApiCustomParams();
			apiCustomParams.setApiUrl(requestParam.getUrl());
			apiCustomParams.setMethod(requestParam.getMethod());
			apiCustomParams.setFormData(requestParam.getFormParam());
			apiCustomParams.setBodyData(requestParam.getBodyParam());
			apiCustomParams.setHeaderData(requestParam.getHeaderParam());
			apiCustomParams.setCookieData(requestParam.getCookieParam());
			apiCustomNodeService.addNode(apiCustomNode, apiCustomParams);
		}
		ProxyRequestResultVo requestResult = swaggerHttpRequestService.proxyRequest(request, requestParam);
		return DocResponseJson.ok(requestResult);
	}
	
	/**
	 * 代理接口下载请求
	 *
	 * @return 请求参数
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/download")
	public ResponseJson<ProxyRequestResultVo> download(HttpServletRequest request, HttpServletResponse response, ProxyRequestParam requestParam) {
		swaggerHttpRequestService.proxyDownload(request, response, requestParam);
		return DocResponseJson.ok();
	}
}
