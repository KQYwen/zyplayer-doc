package com.zyplayer.doc.swagger.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.swagger.controller.vo.LocationListVo;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 开放接口文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年1月27日
 */
@RestController
@RequestMapping("/swagger-mg-ui/open-doc")
public class MgOpenDocController {
	private static Logger logger = LoggerFactory.getLogger(MgOpenDocController.class);
	
	@Resource
	private MgStorageService storageService;
	
	@PostMapping(value = "/checkConfig")
	public DocResponseJson<Object> checkConfig() {
		return DocResponseJson.ok();
	}
	
	/**
	 * @author 暮光：城中城
	 * @since 2019年1月27日
	 */
	@ResponseBody
	@PostMapping(value = "/{source}")
	public ResponseJson<List<SwaggerResourcesInfoVo>> resourcesList(@PathVariable("source") String source) {
		
		return DocResponseJson.ok();
	}
	
	
	/**
	 * 获取所有的文档
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param request request
	 * @param response response
	 */
	@ResponseBody
	@PostMapping(value = "/docs")
	public void docs(HttpServletRequest request, HttpServletResponse response) {
		String choiceLocationList = request.getParameter("choiceLocationList");
		List<LocationListVo> locationList = new LinkedList<>();
		String swaggerLocationListStr = storageService.get(StorageKeys.SWAGGER_LOCATION_LIST);
		if (StringUtils.isNotBlank(swaggerLocationListStr)) {
			locationList = JSON.parseArray(swaggerLocationListStr, LocationListVo.class);
		}
		if (StringUtils.isNotBlank(choiceLocationList)) {
			// uuid相同而且开启了文档
			locationList = locationList.stream().filter(val -> Objects.equals(val.getUuid(), choiceLocationList) && Objects.equals(val.getOpenVisit(), 1)).collect(Collectors.toList());
		} else {
			DocResponseJson.ok().send(response);
			return;
		}
		if (locationList.size() <= 0) {
			DocResponseJson.ok().send(response);
			return;
		}
		List<String> swaggerResourceStrList = new LinkedList<>();
		for (LocationListVo location : locationList) {
			try {
				String resourceStr = HttpRequest.get(location.getLocation()).timeout(3000).execute().body();
				Map<String, Object> jsonObject = JSON.parseObject(resourceStr, new TypeReference<HashMap<String, Object>>(){});
				if (jsonObject == null || jsonObject.isEmpty()) {
					continue;
				}
				jsonObject.put("fullUrl", location);
				String resourcesUrl = location.getLocation();
				int indexV2 = location.getLocation().indexOf("/v2");
				if (indexV2 >= 0) {
					resourcesUrl = location.getLocation().substring(0, indexV2);
				}
				// 本来想转对象之后赋值，但是在此转成JSON字符串之后格式就不是之前的了，所有不能转。。。
				// 直接字符串拼接，坑真多~
				String rewriteDomainUrl = Optional.ofNullable(location.getRewriteDomainUrl()).orElse("");
				resourceStr = resourceStr.substring(1);
				resourceStr = "{\"fullUrl\":\"" + location.getLocation() + "\","
						+ "\"domainUrl\":\"" + resourcesUrl + "\","
						+ "\"rewriteDomainUrl\":\"" + rewriteDomainUrl + "\","
						+ resourceStr;
				swaggerResourceStrList.add(resourceStr);
			} catch (Exception e) {
				logger.error("获取文档失败：{}，{}", location, e.getMessage());
			}
		}
		// 用默认的json解析要内存溢出，解析不了JSONObject、、就只有这样写了~
		DocResponseJson.ok(swaggerResourceStrList).send(response);
	}
}
