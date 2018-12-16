package com.zyplayer.doc.swagger.controller;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.constant.Consts;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 承接了所有的ApiResourceController的接口
 *
 * @author 暮光：城中城
 * @since 2018年12月16日
 */
@RestController
public class ZyplayerSwaggerController {
	
	@Autowired
	private MgStorageService storageService;
	
	@RequestMapping("/swagger-resources")
	public List<SwaggerResource> swaggerResources() {
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		List<SwaggerResourcesInfoVo> resourcesInfoVoList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
		if (resourcesInfoVoList == null || resourcesInfoVoList.isEmpty()) {
			return Collections.emptyList();
		}
		List<SwaggerResource> resourceList = new LinkedList<>();
		resourcesInfoVoList.forEach(resource -> {
			resource.getResourceList().forEach(val -> {
				String location = val.getLocation();
				val.setLocation(Consts.ZYPLAYER_PROXY + resource.getId() + location);
			});
			resourceList.addAll(resource.getResourceList());
		});
		return resourceList;
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

