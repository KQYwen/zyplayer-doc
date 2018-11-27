package com.mg.swagger.controller;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mg.swagger.framework.configuration.EnableSwaggerMgUi;
import com.mg.swagger.framework.configuration.SpringContextUtil;
import com.mg.swagger.framework.constant.StorageKeys;
import com.mg.swagger.framework.constant.Toast;
import com.mg.swagger.framework.json.MgUiResponseJson;
import com.mg.swagger.framework.json.ResponseJson;
import com.mg.swagger.framework.service.MgStorageService;

import cn.hutool.http.HttpRequest;
import springfox.documentation.swagger.web.SwaggerResource;

/**
 * 文档控制器
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@RestController
@RequestMapping("/swagger-mg-ui/document")
public class MgDocumentController {

	@Autowired(required = false)
	private MgStorageService storageService;
	
	/**
	 * 获取所有的文档地址
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 */
	@ResponseBody
	@PostMapping(value = "/resourcesList")
	public ResponseJson resourcesList() {
		if (storageService == null) {
			return MgUiResponseJson.warn(Toast.AUTOWIRED_ERROR);
		}
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		Set<String> resourcesSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			List<String> resourcesList = JSON.parseArray(swaggerResourcesStr, String.class);
			resourcesSet.addAll(resourcesList);
		}
		return MgUiResponseJson.ok(resourcesSet);
	}

	/**
	 * 获取所有的文档
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 */
	@ResponseBody
	@PostMapping(value = "/docs")
	public void docs(HttpServletRequest request, HttpServletResponse response) {
		if (storageService == null) {
			MgUiResponseJson.warn(Toast.AUTOWIRED_ERROR).send(response);
			return;
		}
		boolean needRestorage = true;
		String choiseDocList = request.getParameter("choiseDocList");
		// 转成set，防止重复
		Set<String> resourcesSet = new HashSet<>();
		Set<String> swaggerDocsDeleteSet = new HashSet<>();
		if (StringUtils.isNotBlank(choiseDocList)) {
			needRestorage = false;// 选择的则不再存入
			resourcesSet.addAll(Arrays.asList(choiseDocList.split(",")));
		} else {
			String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
			String swaggerDocsDeleteStr = storageService.get(StorageKeys.SWAGGER_DOCS_DELETE_LIST);
			if (StringUtils.isNotBlank(swaggerResourcesStr)) {
				List<String> resourcesList = JSON.parseArray(swaggerResourcesStr, String.class);
				resourcesSet.addAll(resourcesList);
			} else {
				// 默认加上自身的文档
				String serverPath = "http://" + request.getServerName() // 服务器地址
						+ ":" + request.getServerPort() // 端口号
						+ request.getContextPath();
				// 是否加入自身的文档
				Object object = SpringContextUtil.getBeanWithAnnotation(EnableSwaggerMgUi.class);
				EnableSwaggerMgUi swaggerMgUi = object.getClass().getAnnotation(EnableSwaggerMgUi.class);
				if (swaggerMgUi == null) {
					// 直接通过superclass去找
					Class<?> superclass = object.getClass().getSuperclass();
					if (superclass != null) {
						swaggerMgUi = superclass.getAnnotation(EnableSwaggerMgUi.class);
					}
				}
				if (swaggerMgUi == null) {
					// 再通过AopUtils去找
					Class<?> targetClass = AopUtils.getTargetClass(object);
					if (targetClass != null) {
						swaggerMgUi = targetClass.getAnnotation(EnableSwaggerMgUi.class);
					}
				}
				if (swaggerMgUi == null) {
					resourcesSet.add(serverPath + "/swagger-resources");
				} else {
					boolean selfDoc = swaggerMgUi.selfDoc();
					if (selfDoc) {
						resourcesSet.add(serverPath + "/swagger-resources");
					} else {
						// 启动后第一次访问没有数据情况下需要加载进来的swagger-resources地址
						String[] defaultResources = swaggerMgUi.defaultResources();
						if (defaultResources != null && defaultResources.length > 0) {
							resourcesSet.addAll(Arrays.asList(defaultResources));
						}
					}
				}
			}
			if (StringUtils.isNotBlank(swaggerDocsDeleteStr)) {
				List<String> swaggerDocsDeleteList = JSON.parseArray(swaggerDocsDeleteStr, String.class);
				swaggerDocsDeleteSet.addAll(swaggerDocsDeleteList);
			}
		}
		List<Map<String, Object>> swaggerResourceList = new LinkedList<>();
		List<String> swaggerResourceStrList = new LinkedList<>();
		for (String resourcesUrl : resourcesSet) {
			List<SwaggerResource> resourceList = null;
			try {
				String resourcesStr = HttpRequest.get(resourcesUrl).timeout(3000).execute().body();
				resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resourceList == null || resourceList.isEmpty()) {
				continue;
			}
			resourcesUrl = resourcesUrl.substring(0, resourcesUrl.lastIndexOf("/") + 1);
			for (SwaggerResource resource : resourceList) {
				String location = resource.getLocation();
				// 最后一个斜杠在resourcesUrl中已经加上，替换掉后面的防止两根斜杠
				location = location.startsWith("/") ? location.replaceFirst("/", "") : location;
				if (location.indexOf("?") >= 0) {
					try {
						String encode = URLEncoder.encode(resource.getName(), "utf-8");
						location = location.substring(0, location.lastIndexOf("?")) + "?group=" + encode;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				location = resourcesUrl + location;
				// 已删除的则不处理
				if (swaggerDocsDeleteSet.contains(location)) {
					continue;
				}
				try {
					String resourceStr = HttpRequest.get(location).timeout(3000).execute().body();
					Map<String, Object> jsonObject = JSON.parseObject(resourceStr, new TypeReference<HashMap<String, Object>>(){});
					if (jsonObject == null || jsonObject.isEmpty()) {
						continue;
					}
					jsonObject.put("fullUrl", location);
					swaggerResourceList.add(jsonObject);
					// 本来想转对象之后赋值，但是在此转成JSON字符串之后格式就不是之前的了，所有不能转。。。
					// 直接字符串拼接，坑真多~
					resourceStr = resourceStr.substring(1);
					resourceStr = "{\"fullUrl\":\"" + location + "\","
							+ "\"domainUrl\":\"" + resourcesUrl + "\","
							+ resourceStr;
					swaggerResourceStrList.add(resourceStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (needRestorage) {
			storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
		}
		// 用默认的json解析要内存溢出，解析不了JSONObject、、就只有这样写了~
		MgUiResponseJson.ok(swaggerResourceStrList).send(response);
	}

	/**
	 * 增加/swagger-resources地址
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param resourcesUrl
	 * @return
	 */
	@PostMapping(value = "/addSwaggerResources")
	public ResponseJson addSwaggerResources(String resourcesUrl) {
		if (storageService == null) {
			return MgUiResponseJson.warn(Toast.AUTOWIRED_ERROR);
		}
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		String swaggerDocsDeleteStr = storageService.get(StorageKeys.SWAGGER_DOCS_DELETE_LIST);
		Set<String> swaggerDocsDeleteSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerDocsDeleteStr)) {
			List<String> swaggerDocsDeleteList = JSON.parseArray(swaggerDocsDeleteStr, String.class);
			swaggerDocsDeleteSet.addAll(swaggerDocsDeleteList);
		}
		// 转成set，防止重复
		Set<String> resourcesSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			List<String> resourcesList = JSON.parseArray(swaggerResourcesStr, String.class);
			resourcesSet.addAll(resourcesList);
		}
		try {
			String resourcesStr = HttpRequest.get(resourcesUrl).timeout(3000).execute().body();
			List<SwaggerResource> resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
			if (resourceList == null || resourceList.isEmpty()) {
				return MgUiResponseJson.warn("改地址未找到文档");
			}
			// 重新加入的时候把之前的已删除的回恢复
			String resourcesDomain = resourcesUrl.substring(0, resourcesUrl.lastIndexOf("/") + 1);
			for (SwaggerResource swaggerResource : resourceList) {
				String location = swaggerResource.getLocation();
				// 最后一个斜杠在resourcesUrl中已经加上，替换掉后面的防止两根斜杠
				location = location.startsWith("/") ? location.replaceFirst("/", "") : location;
				if (location.indexOf("?") >= 0) {
					try {
						String encode = URLEncoder.encode(swaggerResource.getName(), "utf-8");
						location = location.substring(0, location.lastIndexOf("?")) + "?group=" + encode;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				location = resourcesDomain + location;
				swaggerDocsDeleteSet.remove(location);
			}
			resourcesSet.add(resourcesUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return MgUiResponseJson.warn("改地址查找文档失败");
		}
		storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
		storageService.put(StorageKeys.SWAGGER_DOCS_DELETE_LIST, JSON.toJSONString(swaggerDocsDeleteSet));
		return MgUiResponseJson.ok();
	}

	/**
	 * 删除/v2/api-docs
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param docUrl
	 * @return
	 */
	@PostMapping(value = "/deleteSwaggerDoc")
	public ResponseJson deleteSwaggerDoc(String docUrl) {
		if (storageService == null) {
			return MgUiResponseJson.warn(Toast.AUTOWIRED_ERROR);
		}
		String swaggerDocsDeleteStr = storageService.get(StorageKeys.SWAGGER_DOCS_DELETE_LIST);
		Set<String> swaggerDocsDeleteSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerDocsDeleteStr)) {
			List<String> swaggerDocsDeleteList = JSON.parseArray(swaggerDocsDeleteStr, String.class);
			swaggerDocsDeleteSet.addAll(swaggerDocsDeleteList);
		}
		swaggerDocsDeleteSet.add(docUrl);
		storageService.put(StorageKeys.SWAGGER_DOCS_DELETE_LIST, JSON.toJSONString(swaggerDocsDeleteSet));
		return MgUiResponseJson.ok();
	}
}
