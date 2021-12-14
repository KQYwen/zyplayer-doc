package com.zyplayer.doc.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.service.manage.ApiDocService;
import com.zyplayer.doc.api.controller.vo.SwaggerResourceVo;
import com.zyplayer.doc.api.framework.utils.SwaggerDocUtil;
import com.zyplayer.doc.api.service.SwaggerHttpRequestService;
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
public class ApiSwaggerProxyController {
	
	private static final String HAL_MEDIA_TYPE = "application/hal+json";
	
	@Resource
	private ApiDocService apiDocService;
	@Resource
	private SwaggerHttpRequestService swaggerHttpRequestService;
	
	@ResponseBody
	@RequestMapping("/swagger-resources")
	public List<SwaggerResourceVo> swaggerResources() {
		List<SwaggerResourceVo> resourceList = new LinkedList<>();
		QueryWrapper<ApiDoc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("doc_status", 1);
		queryWrapper.eq("open_visit", 1);
		queryWrapper.in("doc_type", 1, 2);
		queryWrapper.orderByAsc("id");
		queryWrapper.select("id", "name", "rewrite_domain");
		List<ApiDoc> docList = apiDocService.list(queryWrapper);
		for (ApiDoc apiDoc : docList) {
			SwaggerResourceVo resource = new SwaggerResourceVo();
			resource.setUrl("/v2/api-docs?id=" + apiDoc.getId());
			resource.setName(apiDoc.getName());
			resource.setSwaggerVersion("2.0");
			resource.setRewriteDomain(apiDoc.getRewriteDomain());
			resourceList.add(resource);
		}
		return resourceList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v2/api-docs", produces = {MimeTypeUtils.APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE})
	public ResponseEntity<Object> content(HttpServletRequest request, Long id) {
		ApiDoc apiDoc = apiDocService.getById(id);
		if (apiDoc == null || !Objects.equals(apiDoc.getOpenVisit(), 1)) {
			throw new ConfirmException("文档不存在");
		}
		if (Objects.equals(apiDoc.getDocType(), 1)) {
			try {
				String docsDomain = SwaggerDocUtil.getV2ApiDocsDomain(apiDoc.getDocUrl());
				String contentStr = swaggerHttpRequestService.requestSwaggerUrl(request, apiDoc.getId(), apiDoc.getDocUrl(), docsDomain);
				return new ResponseEntity<>(new Json(contentStr), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(DocResponseJson.warn("请求文档失败"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Json(apiDoc.getJsonContent()), HttpStatus.OK);
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

