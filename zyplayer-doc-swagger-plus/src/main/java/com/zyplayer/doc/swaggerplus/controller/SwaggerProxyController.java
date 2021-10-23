package com.zyplayer.doc.swaggerplus.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerDoc;
import com.zyplayer.doc.data.service.manage.SwaggerDocService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 承接了所有的ApiResourceController的接口
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
public class SwaggerProxyController {
	
	@Resource
	private SwaggerDocService swaggerDocService;
	
	@RequestMapping("/swagger-resources")
	public List<SwaggerResource> swaggerResources() {
		Set<SwaggerResource> resourceList = new HashSet<>();
		List<SwaggerDoc> docList = swaggerDocService.getSwaggerDocList(new SwaggerDoc());
		for (SwaggerDoc swaggerDoc : docList) {
			SwaggerResource resource = new SwaggerResource();
			resource.setUrl("/doc-swagger/doc/content?id=" + swaggerDoc.getId());
			resource.setName(swaggerDoc.getName());
			resource.setSwaggerVersion("2.0");
			resourceList.add(resource);
		}
		return new LinkedList<>(resourceList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/swagger-resources/configuration/security")
	public ResponseEntity<SecurityConfiguration> securityConfiguration() {
		SecurityConfiguration securityConfiguration = new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "api_key", ",");
		return new ResponseEntity<>(securityConfiguration, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/swagger-resources/configuration/ui")
	public ResponseEntity<UiConfiguration> uiConfiguration() {
		UiConfiguration uiConfiguration = new UiConfiguration(null);
		return new ResponseEntity<>(uiConfiguration, HttpStatus.OK);
	}
}

