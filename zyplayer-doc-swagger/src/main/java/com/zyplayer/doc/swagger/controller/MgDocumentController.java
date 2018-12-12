package com.zyplayer.doc.swagger.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.configuration.EnableSwaggerMgUi;
import com.zyplayer.doc.swagger.framework.configuration.SpringContextUtil;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * 文档控制器
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@RestController
@RequestMapping("/swagger-mg-ui/document")
public class MgDocumentController {

	private static Logger logger = LoggerFactory.getLogger(MgDocumentController.class);

	@Autowired
	private MgStorageService storageService;
	
	/**
	 * 获取所有的文档地址
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @return 文档内容
	 */
	@ResponseBody
	@PostMapping(value = "/resourcesList")
	public ResponseJson<Set<SwaggerResourcesInfoVo>> resourcesList() {
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		Set<SwaggerResourcesInfoVo> resourcesSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			List<SwaggerResourcesInfoVo> resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
			resourcesSet.addAll(resourcesList);
		}
		return DocResponseJson.ok(resourcesSet);
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
		boolean needRestorage = true;
		String choiseDocList = request.getParameter("choiseDocList");
		// 转成set，防止重复
		Set<SwaggerResourcesInfoVo> resourcesSet = new HashSet<>();
		Set<String> swaggerDocsDeleteSet = new HashSet<>();
		if (StringUtils.isNotBlank(choiseDocList)) {
			needRestorage = false;// 选择的则不再存入
			for (String url : choiseDocList.split(",")) {
				resourcesSet.add(new SwaggerResourcesInfoVo(url));
			}
		} else {
			String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
			String swaggerDocsDeleteStr = storageService.get(StorageKeys.SWAGGER_DOCS_DELETE_LIST);
			if (StringUtils.isNotBlank(swaggerResourcesStr)) {
				List<SwaggerResourcesInfoVo> resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
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
					resourcesSet.add(new SwaggerResourcesInfoVo(serverPath + "/swagger-resources"));
				} else {
					if (swaggerMgUi.selfDoc()) {
						resourcesSet.add(new SwaggerResourcesInfoVo(serverPath + "/swagger-resources"));
					}
					// 启动后第一次访问没有数据情况下需要加载进来的swagger-resources地址
					String[] defaultResources = swaggerMgUi.defaultResources();
					if (defaultResources != null && defaultResources.length > 0) {
						for (String url : defaultResources) {
							resourcesSet.add(new SwaggerResourcesInfoVo(url));
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
		for (SwaggerResourcesInfoVo resourcesInfoVo : resourcesSet) {
			List<SwaggerResource> resourceList = null;
			String resourcesUrl = resourcesInfoVo.getUrl();
			try {
				String resourcesStr = HttpRequest.get(resourcesUrl).timeout(3000).execute().body();
				resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
			} catch (Exception e) {
				logger.error("获取文档失败：{}，{}", resourcesUrl, e.getMessage());
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
					logger.error("获取文档失败：{}，{}", location, e.getMessage());
				}
			}
		}
		if (needRestorage) {
			storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
		}
		// 用默认的json解析要内存溢出，解析不了JSONObject、、就只有这样写了~
		DocResponseJson.ok(swaggerResourceStrList).send(response);
	}

	/**
	 * 增加/swagger-resources地址
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param resourcesUrl swagger-resources地址
	 * @return 添加结果
	 */
	@PostMapping(value = "/addSwaggerResources")
	public ResponseJson<Object> addSwaggerResources(String resourcesUrl) {
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		String swaggerDocsDeleteStr = storageService.get(StorageKeys.SWAGGER_DOCS_DELETE_LIST);
		Set<String> swaggerDocsDeleteSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerDocsDeleteStr)) {
			List<String> swaggerDocsDeleteList = JSON.parseArray(swaggerDocsDeleteStr, String.class);
			swaggerDocsDeleteSet.addAll(swaggerDocsDeleteList);
		}
		// 转成set，防止重复
		Set<SwaggerResourcesInfoVo> resourcesSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			List<SwaggerResourcesInfoVo> resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
			resourcesSet.addAll(resourcesList);
		}
		try {
			String resourcesStr = HttpRequest.get(resourcesUrl).timeout(3000).execute().body();
			List<SwaggerResource> resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
			if (resourceList == null || resourceList.isEmpty()) {
				return DocResponseJson.warn("该地址未找到文档");
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
			resourcesSet.add(new SwaggerResourcesInfoVo(resourcesUrl));
		} catch (Exception e) {
//			暂不想支持直接添加地址
//			try {
//				SwaggerLocationVo swaggerLocationVo = JSON.parseObject(resourcesStr, SwaggerLocationVo.class);
//				if (StringUtils.isNotBlank(swaggerLocationVo.getSwagger())) {
//					Set<String> locationSet = new HashSet<>();
//					if (StringUtils.isNotBlank(swaggerLocationListStr)) {
//						String swaggerLocationListStr = storageService.get(StorageKeys.SWAGGER_LOCATION_LIST);
//						List<String> locationList = JSON.parseArray(swaggerLocationListStr, String.class);
//						locationSet.addAll(locationList);
//						storageService.put(StorageKeys.SWAGGER_LOCATION_LIST, JSON.toJSONString(locationSet));
//					}
//				} else {
//					return DocResponseJson.warn("该地址查找文档失败");
//				}
//			} catch (Exception e2) {
				logger.error("获取文档失败：{}，{}", resourcesUrl, e.getMessage());
				return DocResponseJson.warn("该地址查找文档失败");
//			}
		}
		storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
		storageService.put(StorageKeys.SWAGGER_DOCS_DELETE_LIST, JSON.toJSONString(swaggerDocsDeleteSet));
		return DocResponseJson.ok();
	}

	/**
	 * 增加/swagger-resources地址
	 *
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param resourcesUrl swagger-resources地址
	 * @return 添加结果
	 */
	@PostMapping(value = "/syncDocData")
	public ResponseJson<Object> syncDocData(String resourcesUrl) {
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		// 转成set，防止重复
		Set<SwaggerResourcesInfoVo> resourcesSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			List<SwaggerResourcesInfoVo> resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
			resourcesSet.addAll(resourcesList);
		}
		try {
			String resourcesStr = HttpRequest.get(resourcesUrl).timeout(3000).execute().body();
			List<SwaggerResource> resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
			if (resourceList == null || resourceList.isEmpty()) {
				return DocResponseJson.warn("该地址未找到文档");
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
			}
			resourcesSet.add(new SwaggerResourcesInfoVo(resourcesUrl));
		} catch (Exception e) {
			logger.error("获取文档失败：{}，{}", resourcesUrl, e.getMessage());
			return DocResponseJson.warn("该地址查找文档失败");
		}
		storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
		return DocResponseJson.ok();
	}

	/**
	 * 删除/v2/api-docs
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param docUrl 文档地址
	 * @return 删除结果
	 */
	@PostMapping(value = "/deleteSwaggerDoc")
	public ResponseJson<Object> deleteSwaggerDoc(String docUrl) {
		String swaggerDocsDeleteStr = storageService.get(StorageKeys.SWAGGER_DOCS_DELETE_LIST);
		Set<String> swaggerDocsDeleteSet = new HashSet<>();
		if (StringUtils.isNotBlank(swaggerDocsDeleteStr)) {
			List<String> swaggerDocsDeleteList = JSON.parseArray(swaggerDocsDeleteStr, String.class);
			swaggerDocsDeleteSet.addAll(swaggerDocsDeleteList);
		}
		swaggerDocsDeleteSet.add(docUrl);
		storageService.put(StorageKeys.SWAGGER_DOCS_DELETE_LIST, JSON.toJSONString(swaggerDocsDeleteSet));
		return DocResponseJson.ok();
	}
}
