package com.zyplayer.doc.swagger.controller;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.swagger.controller.vo.LocationListVo;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.constant.Consts;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.lang.StringUtils;
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

import java.util.*;

/**
 * 承接了所有的ApiResourceController的接口
 *
 * @author 暮光：城中城
 * @since 2018年12月16日
 */
@AuthMan
@RestController
public class ZyplayerSwaggerController {
	
	@Resource
	private MgStorageService storageService;
	
	@RequestMapping("/swagger-resources")
	public List<SwaggerResource> swaggerResources() {
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		List<SwaggerResourcesInfoVo> resourcesInfoVoList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
		if (resourcesInfoVoList == null || resourcesInfoVoList.isEmpty()) {
			return Collections.emptyList();
		}
		Set<SwaggerResource> resourceList = new HashSet<>();
		resourcesInfoVoList.forEach(resource -> {
			resource.getResourceList().forEach(val -> {
				String location = val.getLocation();
				val.setLocation(Consts.ZYPLAYER_PROXY + resource.getId() + location);
			});
			resourceList.addAll(resource.getResourceList());
		});
		List<LocationListVo> locationList = new LinkedList<>();
		String swaggerLocationListStr = storageService.get(StorageKeys.SWAGGER_LOCATION_LIST);
		if (StringUtils.isNotBlank(swaggerLocationListStr)) {
			locationList = JSON.parseArray(swaggerLocationListStr, LocationListVo.class);
		}
		for (LocationListVo listVo : locationList) {
			if (listVo.getLocation().contains(Consts.PROXY_API_DOCS)) {
				SwaggerResource resource = new SwaggerResource();
				resource.setLocation(listVo.getLocation());
				resource.setName(listVo.getName());
				resource.setSwaggerVersion("2.0");
				resourceList.add(resource);
			}
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

