package com.zyplayer.doc.swaggerplus.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerDoc;
import com.zyplayer.doc.data.service.manage.SwaggerDocService;
import com.zyplayer.doc.swaggerplus.controller.vo.SwaggerResourceVo;
import com.zyplayer.doc.swaggerplus.framework.utils.SwaggerDocUtil;
import com.zyplayer.doc.swaggerplus.service.SwaggerHttpRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger.web.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 承接了所有的ApiResourceController的接口
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
public class SwaggerProxyController {
	
	private static final String HAL_MEDIA_TYPE = "application/hal+json";
	
	@Resource
	private SwaggerDocService swaggerDocService;
	@Resource
	private SwaggerHttpRequestService swaggerHttpRequestService;
	
	@ResponseBody
	@RequestMapping("/swagger-resources")
	public List<SwaggerResourceVo> swaggerResources() {
		List<SwaggerResourceVo> resourceList = new LinkedList<>();
		SwaggerDoc swaggerSearch = new SwaggerDoc();
		swaggerSearch.setYn(1);
		swaggerSearch.setDocStatus(1);
		List<SwaggerDoc> docList = swaggerDocService.getSwaggerDocList(swaggerSearch);
		for (SwaggerDoc swaggerDoc : docList) {
			SwaggerResourceVo resource = new SwaggerResourceVo();
			resource.setUrl("/v2/api-docs?id=" + swaggerDoc.getId());
			resource.setName(swaggerDoc.getName());
			resource.setSwaggerVersion("2.0");
			resource.setRewriteDomain(swaggerDoc.getRewriteDomain());
			resourceList.add(resource);
		}
		return resourceList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v2/api-docs", produces = {MimeTypeUtils.APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE})
	public ResponseEntity<Object> content(HttpServletRequest request, Long id) {
		SwaggerDoc swaggerDoc = swaggerDocService.getById(id);
		if (swaggerDoc == null) {
			throw new ConfirmException("文档不存在");
		}
		if (Objects.equals(swaggerDoc.getDocType(), 1)) {
			try {
				String docsDomain = SwaggerDocUtil.getV2ApiDocsDomain(swaggerDoc.getDocUrl());
				String contentStr = swaggerHttpRequestService.requestSwaggerUrl(request, swaggerDoc.getDocUrl(), docsDomain);
				return new ResponseEntity<>(new Json(contentStr), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(DocResponseJson.warn("请求文档失败"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Json(swaggerDoc.getJsonContent()), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/swagger-resources/configuration/security")
	public ResponseEntity<SecurityConfiguration> securityConfiguration() {
		SecurityConfiguration securityConfiguration = SecurityConfigurationBuilder.builder().build();
		return new ResponseEntity<>(securityConfiguration, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/swagger-resources/configuration/ui")
	public ResponseEntity<UiConfiguration> uiConfiguration() {
		UiConfiguration uiConfiguration = UiConfigurationBuilder.builder().build();
		return new ResponseEntity<>(uiConfiguration, HttpStatus.OK);
	}
}

