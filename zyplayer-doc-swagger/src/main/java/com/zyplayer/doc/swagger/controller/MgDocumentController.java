package com.zyplayer.doc.swagger.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.swagger.controller.vo.GlobalParamVo;
import com.zyplayer.doc.swagger.controller.vo.LocationListVo;
import com.zyplayer.doc.swagger.controller.vo.SwaggerLocationVo;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.configuration.EnableDocSwagger;
import com.zyplayer.doc.swagger.framework.configuration.SpringContextUtil;
import com.zyplayer.doc.swagger.framework.constant.Consts;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger.web.SwaggerResource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@AuthMan
@RestController
@RequestMapping("/swagger-mg-ui/document")
public class MgDocumentController {
	private static Logger logger = LoggerFactory.getLogger(MgDocumentController.class);

	@Resource
	private MgStorageService storageService;
	
	// 文档请求超时时间
	int docTimeout = 8000;
	
	/**
	 * 获取所有的文档地址
	 *
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @return 文档内容
	 */
	@ResponseBody
	@PostMapping(value = "/resourcesList")
	public ResponseJson<List<SwaggerResourcesInfoVo>> resourcesList() {
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		List<SwaggerResourcesInfoVo> resourcesList = new LinkedList<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
		}
		return DocResponseJson.ok(resourcesList);
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
		List<SwaggerResourcesInfoVo> resourcesSet = new LinkedList<>();
		List<LocationListVo> locationList = this.getLocationSet();
		
		if (StringUtils.isNotBlank(choiceLocationList)) {
			// 当前choiceLocationList只有一个，可以这样搞
			locationList = locationList.stream().filter(val -> Objects.equals(val.getLocation(), choiceLocationList)).collect(Collectors.toList());
		}
		if (locationList.size() <= 0) {
			// 默认加上自身的文档
			String serverPath = "http://" + request.getServerName() // 服务器地址
					+ ":" + request.getServerPort() // 端口号
					+ request.getContextPath();
			// 是否加入自身的文档
			EnableDocSwagger swaggerMgUi = SpringContextUtil.getEnableSwaggerMgUi();
			if (swaggerMgUi == null) {
				resourcesSet.add(new SwaggerResourcesInfoVo(serverPath + "/swagger-resources"));
			} else {
				if (swaggerMgUi.selfDoc()) {
					resourcesSet.add(new SwaggerResourcesInfoVo(serverPath + "/swagger-resources"));
				}
				// 启动后第一次访问没有数据情况下需要加载进来的swagger-resources地址
				String[] defaultResources = swaggerMgUi.defaultResources();
				for (String url : defaultResources) {
					resourcesSet.add(new SwaggerResourcesInfoVo(url));
				}
				String[] defaultLocation = swaggerMgUi.defaultLocation();
				for (String url : defaultLocation) {
					locationList.add(new LocationListVo(url, ""));
				}
			}
		}
		List<GlobalParamVo> globalParamList = this.getGlobalParamList();
		if (resourcesSet.size() > 0) {
			List<LocationListVo> locationListStorage = this.getLocationSet();
			for (SwaggerResourcesInfoVo resourcesInfoVo : resourcesSet) {
				List<SwaggerResource> resourceList = null;
				String resourcesUrl = resourcesInfoVo.getUrl();
				try {
					String resourcesStr = HttpRequest.get(resourcesUrl)
							.form(this.getGlobalParamObjMap(globalParamList, "form"))
							.addHeaders(this.getGlobalParamMap(globalParamList, "header"))
							.timeout(docTimeout).execute().body();
					resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
				} catch (Exception e) {
					logger.error("获取文档失败：{}，{}", resourcesUrl, e.getMessage());
				}
				if (resourceList == null || resourceList.isEmpty()) {
					continue;
				}
				resourcesInfoVo.setResourceList(resourceList);
				String resourcesDomain = resourcesUrl.substring(0, resourcesUrl.lastIndexOf("/") + 1);
				for (SwaggerResource swaggerResource : resourceList) {
					String location = this.getLocationUrl(resourcesDomain, swaggerResource.getLocation(), swaggerResource.getName());
					locationListStorage.add(new LocationListVo(location, resourcesUrl));
				}
			}
			locationList.addAll(locationListStorage);
			this.storageSwaggerLocationList(locationListStorage);
			resourcesSet.forEach(val -> {
				if (val.getId() == null) {
					val.setId(storageService.getNextId());
				}
			});
			storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
		}
		List<String> swaggerResourceStrList = new LinkedList<>();
		for (LocationListVo location : locationList) {
			try {
				String resourceStr = HttpRequest.get(location.getLocation())
						.form(this.getGlobalParamObjMap(globalParamList, "form"))
						.addHeaders(this.getGlobalParamMap(globalParamList, "header"))
						.timeout(docTimeout).execute().body();
				String resourcesUrl = location.getLocation();
				int indexV2 = location.getLocation().indexOf("/v2");
				if (indexV2 >= 0) {
					resourcesUrl = location.getLocation().substring(0, indexV2);
				}
				// 本来想转对象之后赋值，但是在此转成JSON字符串之后格式就不是之前的了，所以不能转。。。
				// 直接字符串拼接，坑真多~
				String rewriteDomainUrl = Optional.ofNullable(location.getRewriteDomainUrl()).orElse("");
				resourceStr = resourceStr.substring(1);
				resourceStr = "{\"fullUrl\":\"" + location.getLocation() + "\","
						+ "\"domainUrl\":\"" + resourcesUrl + "\","
						+ "\"rewriteDomainUrl\":\"" + rewriteDomainUrl + "\","
						+ "\"customName\":\"" + location.getCustomName() + "\","
						+ resourceStr;
				swaggerResourceStrList.add(resourceStr);
			} catch (Exception e) {
				logger.error("获取文档失败：{}，{}", location, e.getMessage());
			}
		}
		// 用默认的json解析要内存溢出，解析不了JSONObject、、就只有这样写了~
		DocResponseJson.ok(swaggerResourceStrList).send(response);
	}

	/**
	 * 获取所有的文档
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param request request
	 * @param response response
	 */
	@ResponseBody
	@PostMapping(value = "/docsOld")
	public void docsOld(HttpServletRequest request, HttpServletResponse response) {
//		boolean needStorage = false;
//		String choiceDocList = request.getParameter("choiceDocList");
//		// 转成set，防止重复
//		List<SwaggerResourcesInfoVo> resourcesSet = new LinkedList<>();
//
//		List<SwaggerResourcesInfoVo> resourcesList = new LinkedList<>();
//		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
//		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
//			resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
//		}
//		if (StringUtils.isNotBlank(choiceDocList)) {
//			Map<String, SwaggerResourcesInfoVo> infoVoMap = resourcesList.stream().collect(Collectors.toMap(SwaggerResourcesInfoVo::getUrl, val -> val));
//			for (String url : choiceDocList.split(",")) {
//				SwaggerResourcesInfoVo resourcesInfoVo = infoVoMap.get(url);
//				if (resourcesInfoVo != null) {
//					resourcesSet.add(resourcesInfoVo);
//				}
//			}
//		} else if (resourcesList.size() > 0) {
//			resourcesSet.addAll(resourcesList);
//		} else {
//			needStorage = true;
//			// 默认加上自身的文档
//			String serverPath = "http://" + request.getServerName() // 服务器地址
//					+ ":" + request.getServerPort() // 端口号
//					+ request.getContextPath();
//			// 是否加入自身的文档
//			Object enableSwaggerMgUi = SpringContextUtil.getBeanWithAnnotation(EnableDocSwagger.class);
//			if (enableSwaggerMgUi != null) {
//				EnableDocSwagger swaggerMgUi = enableSwaggerMgUi.getClass().getAnnotation(EnableDocSwagger.class);
//				if (swaggerMgUi == null) {
//					// 直接通过superclass去找
//					Class<?> superclass = enableSwaggerMgUi.getClass().getSuperclass();
//					if (superclass != null) {
//						swaggerMgUi = superclass.getAnnotation(EnableDocSwagger.class);
//					}
//				}
//				if (swaggerMgUi == null) {
//					// 再通过AopUtils去找
//					Class<?> targetClass = AopUtils.getTargetClass(enableSwaggerMgUi);
//					if (targetClass != null) {
//						swaggerMgUi = targetClass.getAnnotation(EnableDocSwagger.class);
//					}
//				}
//				if (swaggerMgUi == null) {
//					resourcesSet.add(new SwaggerResourcesInfoVo(serverPath + "/swagger-resources"));
//				} else {
//					if (swaggerMgUi.selfDoc()) {
//						resourcesSet.add(new SwaggerResourcesInfoVo(serverPath + "/swagger-resources"));
//					}
//					// 启动后第一次访问没有数据情况下需要加载进来的swagger-resources地址
//					String[] defaultResources = swaggerMgUi.defaultResources();
//					for (String url : defaultResources) {
//						resourcesSet.add(new SwaggerResourcesInfoVo(url));
//					}
//				}
//			}
//		}
//		List<String> swaggerResourceStrList = new LinkedList<>();
//		for (SwaggerResourcesInfoVo resourcesInfoVo : resourcesSet) {
//			List<SwaggerResource> resourceList = null;
//			String resourcesUrl = resourcesInfoVo.getUrl();
//			try {
//				String resourcesStr = HttpRequest.get(resourcesUrl).timeout(docTimeout).execute().body();
//				resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
//			} catch (Exception e) {
//				logger.error("获取文档失败：{}，{}", resourcesUrl, e.getMessage());
//			}
//			if (resourceList == null || resourceList.isEmpty()) {
//				continue;
//			}
//			resourcesInfoVo.setResourceList(resourceList);
//			resourcesUrl = resourcesUrl.substring(0, resourcesUrl.lastIndexOf("/") + 1);
//			for (SwaggerResource resource : resourceList) {
//				String location = this.getLocationUrl(resourcesUrl, resource.getLocation(), resource.getName());
//				try {
//					String resourceStr = HttpRequest.get(location).timeout(docTimeout).execute().body();
//					Map<String, Object> jsonObject = JSON.parseObject(resourceStr, new TypeReference<HashMap<String, Object>>(){});
//					if (jsonObject == null || jsonObject.isEmpty()) {
//						continue;
//					}
//					jsonObject.put("fullUrl", location);
//					// 本来想转对象之后赋值，但是在此转成JSON字符串之后格式就不是之前的了，所有不能转。。。
//					// 直接字符串拼接，坑真多~
//					String rewriteDomainUrl = Optional.ofNullable(resourcesInfoVo.getRewriteDomainUrl()).orElse("");
//					resourceStr = resourceStr.substring(1);
//					resourceStr = "{\"fullUrl\":\"" + location + "\","
//							+ "\"domainUrl\":\"" + resourcesUrl + "\","
//							+ "\"rewriteDomainUrl\":\"" + rewriteDomainUrl + "\","
//							+ resourceStr;
//					swaggerResourceStrList.add(resourceStr);
//				} catch (Exception e) {
//					logger.error("获取文档失败：{}，{}", location, e.getMessage());
//				}
//			}
//			if (needStorage) {
//				this.addSwaggerLocationList(resourceList, resourcesUrl);
//			}
//		}
//		if (needStorage && resourcesSet.size() > 0) {
//			AtomicInteger idIndex = new AtomicInteger(1);
//			resourcesSet.forEach(val -> val.setId(idIndex.getAndIncrement()));
//			storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesSet));
//		}
//		// 用默认的json解析要内存溢出，解析不了JSONObject、、就只有这样写了~
//		DocResponseJson.ok(swaggerResourceStrList).send(response);
	}

	/**
	 * 增加/swagger-resources地址
	 *
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @param resourcesUrl swagger-resources地址
	 * @param oldUrl 老地址
	 * @param openVisit 是否开放展示
	 * @param rewriteDomainUrl 重写域名地址
	 * @return 添加结果
	 */
	@PostMapping(value = "/addSwaggerResources")
	public ResponseJson<Object> addSwaggerResources(HttpServletRequest request, String swaggerJson, String resourcesUrl,
													String rewriteDomainUrl, String oldUrl, Integer openVisit, String customName) {
		// 通过json文档内容来添加
		ResponseJson<Object> responseJson = this.addSwaggerJsonApiDocs(request, swaggerJson, resourcesUrl, rewriteDomainUrl, openVisit, customName);
		if (responseJson != null) {
			return responseJson;
		}
		// 总有些人喜欢填这个地址，那就替换来支持下吧
		int htmlIndex = resourcesUrl.indexOf("/swagger-ui.html");
		if (htmlIndex > 0) {
			resourcesUrl = resourcesUrl.substring(0, htmlIndex) + "/swagger-resources";
		}
		String swaggerResourcesStr = storageService.get(StorageKeys.SWAGGER_RESOURCES_LIST);
		// 转成set，防止重复
		List<SwaggerResourcesInfoVo> resourcesList = new LinkedList<>();
		if (StringUtils.isNotBlank(swaggerResourcesStr)) {
			resourcesList = JSON.parseArray(swaggerResourcesStr, SwaggerResourcesInfoVo.class);
			// 如果是编辑，把之前的删除掉，再在后面添加
			if (StringUtils.isNotBlank(oldUrl)) {
				String tempStr = oldUrl;
				resourcesList = resourcesList.stream().filter(val -> !Objects.equals(val.getUrl(), tempStr)).collect(Collectors.toList());
			}
		}
		try {
			List<GlobalParamVo> globalParamList = this.getGlobalParamList();
			oldUrl = this.encodeUrlParam(oldUrl);
			resourcesUrl = this.encodeUrlParam(resourcesUrl);
			String resourcesStr = HttpRequest.get(resourcesUrl)
					.form(this.getGlobalParamObjMap(globalParamList, "form"))
					.addHeaders(this.getGlobalParamMap(globalParamList, "header"))
					.header("Accept", "application/json, text/javascript, */*; q=0.01")
					.timeout(docTimeout).execute().body();
			boolean isLocation = this.addSwaggerLocationList(resourcesStr, rewriteDomainUrl, resourcesUrl, oldUrl, openVisit, customName);
			if (!isLocation) {
				List<SwaggerResource> resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
				if (resourceList == null || resourceList.isEmpty()) {
					return DocResponseJson.warn("该地址未找到文档");
				}
				this.addSwaggerLocationList(resourceList, resourcesUrl, rewriteDomainUrl, openVisit);
				SwaggerResourcesInfoVo resourcesInfoVo = new SwaggerResourcesInfoVo(resourcesUrl, resourceList);
				resourcesInfoVo.setRewriteDomainUrl(rewriteDomainUrl);
				resourcesList.add(resourcesInfoVo);
				resourcesList.forEach(val -> {
					if (val.getId() == null) {
						val.setId(storageService.getNextId());
					}
				});
			}
		} catch (Exception e) {
			logger.error("获取文档失败：{}，{}", resourcesUrl, e.getMessage());
			return DocResponseJson.warn("该地址查找文档失败");
		}
		// 去重
		resourcesList = resourcesList.stream().distinct().sorted(Comparator.comparing(SwaggerResourcesInfoVo::getId)).collect(Collectors.toList());
		storageService.put(StorageKeys.SWAGGER_RESOURCES_LIST, JSON.toJSONString(resourcesList));
		return DocResponseJson.ok();
	}

	/**
	 * 增加json格式文档
	 *
	 * @author 暮光：城中城
	 * @since 2019年8月11日
	 * @param swaggerJson swagger的文档内容
	 * @return 添加结果
	 */
	public ResponseJson<Object> addSwaggerJsonApiDocs(HttpServletRequest request, String swaggerJson, String resourcesUrl,
													  String rewriteDomainUrl, Integer openVisit, String customName) {
		if (StringUtils.isNotBlank(swaggerJson)) {
			Integer nextId = 0;
			String customUrl = resourcesUrl;
			if (resourcesUrl.contains(Consts.PROXY_API_DOCS)) {
				nextId = Integer.valueOf(resourcesUrl.substring(resourcesUrl.indexOf("?id=") + 4));
			} else {
				nextId = storageService.getNextId();
				customUrl = request.getRequestURL().toString();
				customUrl = customUrl.substring(0, customUrl.lastIndexOf("/"));
				customUrl = customUrl + Consts.PROXY_API_DOCS + "?id=" + nextId;
			}
			boolean addResult = this.addSwaggerLocationList(swaggerJson, rewriteDomainUrl, customUrl, customUrl, openVisit, customName);
			if (addResult) {
				storageService.put(StorageKeys.PROXY_API_DOCS + nextId, swaggerJson);
				return DocResponseJson.ok();
			}
			return DocResponseJson.warn("添加失败");
		} else if (resourcesUrl.contains(Consts.PROXY_API_DOCS)) {
			return DocResponseJson.warn("添加失败，该地址是代理地址，必须填写文档内容");
		}
		return null;
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
			List<GlobalParamVo> globalParamList = this.getGlobalParamList();
			String resourcesStr = HttpRequest.get(resourcesUrl)
					.form(this.getGlobalParamObjMap(globalParamList, "form"))
					.addHeaders(this.getGlobalParamMap(globalParamList, "header"))
					.timeout(docTimeout).execute().body();
			List<SwaggerResource> resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
			if (resourceList == null || resourceList.isEmpty()) {
				return DocResponseJson.warn("该地址未找到文档");
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
	 * @param location 文档地址
	 * @return 删除结果
	 */
	@Deprecated
	@PostMapping(value = "/deleteSwaggerDoc")
	public ResponseJson<Object> deleteSwaggerDoc(String location) {
		List<LocationListVo> locationList = this.getLocationSet();
		String locationDel = this.encodeUrlParam(location);
		locationList = locationList.stream().filter(val -> !Objects.equals(val.getLocation(), locationDel)).collect(Collectors.toList());
		this.storageSwaggerLocationList(locationList);
		// 代理地址判断
		if (location.contains(Consts.PROXY_API_DOCS)) {
			Integer nextId = Integer.valueOf(location.substring(location.indexOf("?id=") + 4));
			storageService.remove(StorageKeys.PROXY_API_DOCS + nextId);
		}
		return DocResponseJson.ok();
	}

	/**
	 * 获取swaggerLocation列表
	 *
	 * @author 暮光：城中城
	 * @since 2018年8月21日
	 * @return Location列表
	 */
	@PostMapping(value = "/getLocationList")
	public ResponseJson<List<LocationListVo>> getLocationList() {
		List<LocationListVo> locationSet = this.getLocationSet();
		return DocResponseJson.ok(locationSet);
	}
	
	/**
	 * 直接添加地址
 	 */
	private boolean addSwaggerLocationList(String resourcesStr, String rewriteDomainUrl, String locationUrl,
										   String oldUrl, Integer openVisit, String customName) {
		try {
			SwaggerLocationVo swaggerLocationVo = null;
			try {
				swaggerLocationVo = JSON.parseObject(resourcesStr, SwaggerLocationVo.class);
			} catch (Exception e) {
				logger.error("获取文档失败，尝试字符串匹配：{}", e.getMessage());
				// 由于存在$ref这样的字符串，JSON解析可能会失败，尝试字符串匹配
				if (resourcesStr.startsWith("{\"swagger\":\"")) {
					swaggerLocationVo = new SwaggerLocationVo();
					swaggerLocationVo.setSwagger("maybe");
				}
			}
			if (swaggerLocationVo != null && StringUtils.isNotBlank(swaggerLocationVo.getSwagger())) {
				List<LocationListVo> locationList = this.getLocationSet();
				// 组装新的对象
				LocationListVo locationListVo = new LocationListVo(locationUrl, "");
				// 如果旧的不为空，使用旧的uuid
				for (LocationListVo location : locationList) {
					if (Objects.equals(location.getLocation(), oldUrl) && StringUtils.isNotBlank(location.getUuid())) {
						locationListVo = location;
						break;
					}
				}
				locationListVo.setLocation(locationUrl);
				locationListVo.setRewriteDomainUrl(rewriteDomainUrl);
				locationListVo.setOpenVisit(openVisit);
				locationListVo.setCustomName(customName);
				// 去除旧的，加入新的
				locationList = locationList.stream().filter(val -> !Objects.equals(val.getLocation(), oldUrl)).collect(Collectors.toList());
				locationList.add(locationListVo);
				this.storageSwaggerLocationList(locationList);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 直接添加地址
 	 */
	private void addSwaggerLocationList(List<SwaggerResource> resourceList, String resourcesUrl, String rewriteDomainUrl, Integer openVisit) {
		List<LocationListVo> locationList = this.getLocationSet();
		Map<String, LocationListVo> locationListVoMap = locationList.stream().collect(Collectors.toMap(LocationListVo::getLocation, val -> val));
		// 加入到location列表
		String resourcesDomain = resourcesUrl.substring(0, resourcesUrl.lastIndexOf("/") + 1);
		for (SwaggerResource swaggerResource : resourceList) {
			String location = this.getLocationUrl(resourcesDomain, swaggerResource.getLocation(), swaggerResource.getName());
			LocationListVo locationListVo = locationListVoMap.get(location);
			if (locationListVo == null) {
				locationListVo = new LocationListVo(location, resourcesUrl);
				locationList.add(locationListVo);
			}
			locationListVo.setRewriteDomainUrl(rewriteDomainUrl);
			locationListVo.setOpenVisit(openVisit);
		}
		this.storageSwaggerLocationList(locationList);
	}
	
	/**
	 * 保存location列表
 	 */
	private void storageSwaggerLocationList(List<LocationListVo> locationSet) {
		locationSet = locationSet.stream().distinct().collect(Collectors.toList());
		locationSet.forEach(val -> {
			if (val.getId() == null) {
				val.setId(storageService.getNextId());
			}
		});
		locationSet = locationSet.stream().sorted(Comparator.comparing(LocationListVo::getId)).collect(Collectors.toList());
		storageService.put(StorageKeys.SWAGGER_LOCATION_LIST, JSON.toJSONString(locationSet));
	}
	
	/**
	 * 获取swaggerLocation列表
	 */
	private List<LocationListVo> getLocationSet() {
		List<LocationListVo> locationList = new LinkedList<>();
		String swaggerLocationListStr = storageService.get(StorageKeys.SWAGGER_LOCATION_LIST);
		if (StringUtils.isNotBlank(swaggerLocationListStr)) {
			locationList = JSON.parseArray(swaggerLocationListStr, LocationListVo.class);
		}
		return locationList;
	}
	
	/**
	 * 获取拼接好的location
	 */
	private String getLocationUrl(String resourcesDomain, String location, String resourceName) {
		// 最后一个斜杠在resourcesUrl中已经加上，替换掉后面的防止两根斜杠
		location = location.startsWith("/") ? location.replaceFirst("/", "") : location;
		if (location.contains("?")) {
			try {
				String encode = URLEncoder.encode(resourceName, "utf-8");
				location = location.substring(0, location.lastIndexOf("?")) + "?group=" + encode;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resourcesDomain + location;
	}
	
	/**
	 * 全局参数
	 */
	private List<GlobalParamVo> getGlobalParamList() {
		String globalParamList = storageService.get(StorageKeys.GLOBAL_PARAM_LIST);
		// paramIn  key  value
		List<GlobalParamVo> resultList = new LinkedList<>();
		if (StringUtils.isBlank(globalParamList)) {
			return resultList;
		}
		resultList = JSON.parseArray(globalParamList, GlobalParamVo.class);
		return resultList;
	}
	
	/**
	 * 获取指定类型的全局参数
	 */
	private Map<String, String> getGlobalParamMap(List<GlobalParamVo> globalParamList, String paramIn) {
		Map<String, String> paramMap = globalParamList.stream().filter(val -> Objects.equals(val.getParamIn(), paramIn))
				.collect(Collectors.toMap(GlobalParamVo::getKey, GlobalParamVo::getValue));
		return paramMap;
	}
	
	/**
	 * 获取指定类型的全局参数
	 */
	private Map<String, Object> getGlobalParamObjMap(List<GlobalParamVo> globalParamList, String paramIn) {
		Map<String, Object> paramMap = globalParamList.stream().filter(val -> Objects.equals(val.getParamIn(), paramIn))
				.collect(Collectors.toMap(GlobalParamVo::getKey, GlobalParamVo::getValue));
		return paramMap;
	}
	
	/**
	 * url编码参数
	 */
	private String encodeUrlParam(String resourcesUrl) {
		if (StringUtils.isBlank(resourcesUrl)) {
			return resourcesUrl;
		}
		resourcesUrl = resourcesUrl.trim();
		int indexOf = resourcesUrl.indexOf("?");
		if (indexOf < 0) {
			return resourcesUrl;
		}
		StringBuilder baseUrl = new StringBuilder(resourcesUrl.substring(0, indexOf + 1));
		String[] paramArr = resourcesUrl.substring(indexOf + 1).split("&");
		for (int i = 0; i < paramArr.length; i++) {
			String param = paramArr[i];
			String[] split = param.split("=");
			if (i > 0) {
				baseUrl.append("&");
			}
			if (split.length == 2) {
				String value = split[1];
				try {
					value = URLEncoder.encode(split[1], "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				baseUrl.append(split[0]).append("=").append(value);
			} else {
				baseUrl.append(param);
			}
		}
		return baseUrl.toString();
	}
	
	
}
