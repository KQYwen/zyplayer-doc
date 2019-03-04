package com.zyplayer.doc.dubbo.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.dubbo.controller.param.DubboRequestParam;
import com.zyplayer.doc.dubbo.controller.vo.DubboInfoVo;
import com.zyplayer.doc.dubbo.framework.bean.DubboDocInfo;
import com.zyplayer.doc.dubbo.framework.bean.DubboInfo;
import com.zyplayer.doc.dubbo.framework.bean.NacosDubboInfo;
import com.zyplayer.doc.dubbo.framework.bean.ReferenceConfigHolder;
import com.zyplayer.doc.dubbo.framework.constant.StorageKeys;
import com.zyplayer.doc.dubbo.framework.service.MgDubboStorageService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.UrlUtils;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.identifier.MetadataIdentifier;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月10日
 */
@RestController
@RequestMapping("/zyplayer-doc-dubbo/doc-dubbo")
public class DubboController {
	private static Logger logger = LoggerFactory.getLogger(DubboController.class);
	
	private final static String DEFAULT_ROOT = "dubbo";
	private final static String METADATA_NODE_NAME = "service.data";
	private String root;
	
	@Value("${zyplayer.doc.dubbo.zookeeper.url:}")
	private String serviceZookeeperUrl;
	@Value("${zyplayer.doc.dubbo.zookeeper.metadata-url:}")
	private String metadataZookeeperUrl;
	@Value("${zyplayer.doc.dubbo.nacos.url:}")
	private String nacosUrl;
	@Value("${zyplayer.doc.dubbo.nacos.service:}")
	private String nacosService;
	@Resource
	private MgDubboStorageService mgDubboStorageService;
	
	private CuratorFramework serverClient;
	private CuratorFramework metadataClient;
	
	@PostConstruct
	private void init() {
		if (StringUtils.isNotBlank(serviceZookeeperUrl)) {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
			serverClient = CuratorFrameworkFactory.newClient(serviceZookeeperUrl, retryPolicy);
			serverClient.start();
		}
		if (StringUtils.isNotBlank(metadataZookeeperUrl)) {
			URL url = UrlUtils.parseURL(metadataZookeeperUrl, Collections.emptyMap());
			String group = url.getParameter(Constants.GROUP_KEY, DEFAULT_ROOT);
			if (!group.startsWith(Constants.PATH_SEPARATOR)) {
				group = Constants.PATH_SEPARATOR + group;
			}
			this.root = group;
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
			metadataClient = CuratorFrameworkFactory.newClient(metadataZookeeperUrl, retryPolicy);
			metadataClient.start();
		}
	}
	
	@PreDestroy
	private void preDestroy() {
		serverClient.close();
	}
	
	/**
	 * 重新获取所有的服务列表
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/reloadService")
	public DocResponseJson loadService() throws Exception {
		List<DubboInfo> providerList;
		if (StringUtils.isBlank(serviceZookeeperUrl)) {
			if (StringUtils.isBlank(nacosUrl) || StringUtils.isBlank(nacosService)) {
				return DocResponseJson.warn("zyplayer.doc.dubbo.zookeeper.url、zyplayer.doc.dubbo.nacos.url 参数均未配置");
			}
			logger.info("zookeeper参数未配置，使用nacos配置");
			providerList = this.getDubboInfoByNacos();
		} else {
			providerList = this.getDubboInfoByZookeeper();
		}
		mgDubboStorageService.put(StorageKeys.DUBBO_SERVICE_LIST, JSON.toJSONString(providerList));
		return DocResponseJson.ok();
	}
	
	/**
	 * 请求执行服务
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/request")
	public DocResponseJson request(DubboRequestParam param) {
		DubboInfo.DubboNodeInfo dubboNodeInfo = new DubboInfo.DubboNodeInfo();
		dubboNodeInfo.setIp(param.getIp());
		dubboNodeInfo.setPort(param.getPort());
		dubboNodeInfo.setInterfaceX(param.getService());
		String paramTypeStr = Optional.ofNullable(param.getParamTypes()).orElse("");
		String paramsStr = Optional.ofNullable(param.getParams()).orElse("");
		List<String> typeList = JSON.parseArray(paramTypeStr, String.class);
		List<String> paramList = JSON.parseArray(paramsStr, String.class);
		List<String> queryTypeList = new LinkedList<>();
		List<Object> queryParamList = new LinkedList<>();
		for (int i = 0; i < paramList.size(); i++) {
			String typeStr = typeList.get(i);
			String paramStr = paramList.get(i);
			try {
				if (typeStr.endsWith("[]")) {
					String type = typeStr.substring(0, typeStr.length() - 2);
					Class<?> aClass = Class.forName(type);
					List<?> objects = JSON.parseArray(paramStr, aClass);
					queryTypeList.add(typeStr);
					queryParamList.add(objects);
				} else if (typeStr.matches("java\\.util\\.List<.+>")) {
					Pattern pattern = Pattern.compile("java\\.util\\.List<(.+)>");
					Matcher matcher = pattern.matcher(typeStr);
					if (matcher.find()) {
						String group = matcher.group(1);
						Class<?> aClass = Class.forName(group);
						List<?> objects = JSON.parseArray(paramStr, aClass);
						queryParamList.add(objects);
						queryTypeList.add("java.util.List");
					}
				} else {
					Class<?> aClass = Class.forName(typeStr);
					Object object = JSON.parseObject(paramStr, aClass);
					queryParamList.add(object);
					queryTypeList.add(typeStr);
				}
			} catch (Exception e) {
				// 未找到对应类型，请手动引入项目包
				logger.warn("未找到对应类型，请手动引入项目包：{}", typeStr);
				queryParamList.add(paramStr);
				queryTypeList.add(typeStr);
			}
		}
		GenericService bean = ReferenceConfigHolder.getBean(dubboNodeInfo);
		try {
			Object result = bean.$invoke(param.getMethod(), queryTypeList.toArray(new String[]{}), queryParamList.toArray());
			return DocResponseJson.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("请求失败：" + e.getMessage());
		}
	}
	
	/**
	 * 获取文档列表
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/getDocList")
	public DocResponseJson getDocList() {
		String dubboServiceList = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_LIST);
		String dubboServiceDoc = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_DOC);
		if (StringUtils.isBlank(dubboServiceList)) {
			return DocResponseJson.ok();
		}
		DubboInfoVo dubboInfoVo = new DubboInfoVo();
		List<DubboInfo> providerList = JSON.parseArray(dubboServiceList, DubboInfo.class);
		dubboInfoVo.setServerList(providerList);
		if (StringUtils.isNotBlank(dubboServiceDoc)) {
			List<DubboDocInfo> docInfoList = JSON.parseArray(dubboServiceDoc, DubboDocInfo.class);
			Map<String, DubboDocInfo> docInfoMap = docInfoList.stream().collect(Collectors.toMap(DubboDocInfo::getFunction, val -> val));
			dubboInfoVo.setDocMap(docInfoMap);
		}
		return DocResponseJson.ok(dubboInfoVo);
	}
	
	/**
	 * 获取文档详情，依据类名生成
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@GetMapping(value = "/test")
	public DocResponseJson test() throws Exception {
		String path = getNodePath("com.zyplayer.dubbo.service.UserService", null, null, "dubbo-provider");
		if (metadataClient.checkExists().forPath(path) == null) {
			return DocResponseJson.ok(path);
		}
		String metadata = new String(metadataClient.getData().forPath(path));
		FullServiceDefinition fullServiceDefinition = JSON.parseObject(metadata, FullServiceDefinition.class);
		return DocResponseJson.ok(fullServiceDefinition);
	}
	
	/**
	 * 获取文档详情，依据类名生成
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/findDocInfo")
	public DocResponseJson findDocInfo(DubboRequestParam param) {
		DubboDocInfo definition = this.getDefinitionByJar(param);
		if (definition == null) {
			definition = this.getDefinitionByMetadata(param);
		}
		if (definition == null) {
			return DocResponseJson.warn("未找到指定类，请引入相关包或开启metadata，类名：" + param.getService());
		}
		Map<String, DubboDocInfo> docInfoMap = new HashMap<>();
		String dubboServiceDoc = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_DOC);
		if (StringUtils.isNotBlank(dubboServiceDoc)) {
			List<DubboDocInfo> docInfoList = JSON.parseArray(dubboServiceDoc, DubboDocInfo.class);
			docInfoMap = docInfoList.stream().collect(Collectors.toMap(DubboDocInfo::getFunction, val -> val));
		}
		String function = param.getService() + "." + param.getMethod();
		DubboDocInfo dubboDocInfo = docInfoMap.get(function);
		if (dubboDocInfo == null) {
			dubboDocInfo = new DubboDocInfo();
			dubboDocInfo.setParams(definition.getParams());
			dubboDocInfo.setFunction(function);
			dubboDocInfo.setVersion(1);
			dubboDocInfo.setResultType(definition.getResultType());
			dubboDocInfo.setService(param.getService());
			dubboDocInfo.setMethod(param.getMethod());
			docInfoMap.put(function, dubboDocInfo);
			List<DubboDocInfo> docInfoList = new ArrayList<>(docInfoMap.values());
			mgDubboStorageService.put(StorageKeys.DUBBO_SERVICE_DOC, JSON.toJSONString(docInfoList));
		}
		return DocResponseJson.ok(dubboDocInfo);
	}
	
	/**
	 * 保存文档
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/saveDoc")
	public DocResponseJson saveDoc(DubboDocInfo param, String paramsJson) {
		String dubboServiceDoc = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_DOC);
		Map<String, DubboDocInfo> docInfoMap = new HashMap<>();
		if (StringUtils.isNotBlank(dubboServiceDoc)) {
			List<DubboDocInfo> docInfoList = JSON.parseArray(dubboServiceDoc, DubboDocInfo.class);
			docInfoMap = docInfoList.stream().collect(Collectors.toMap(DubboDocInfo::getFunction, val -> val));
		}
		String function = param.getService() + "." + param.getMethod();
		DubboDocInfo dubboDocInfo = docInfoMap.get(function);
		if (dubboDocInfo != null) {
			Integer newVersion = Optional.ofNullable(param.getVersion()).orElse(1);
			Integer oldVersion = Optional.ofNullable(dubboDocInfo.getVersion()).orElse(1);
			if (oldVersion > newVersion) {
				return DocResponseJson.warn("已有用户在您之前修改过文档，请刷新后再修改");
			}
			param.setVersion(oldVersion + 1);
			if (StringUtils.isEmpty(param.getExplain())) {
				param.setExplain(dubboDocInfo.getExplain());
			}
			if (StringUtils.isEmpty(param.getResult())) {
				param.setResult(dubboDocInfo.getResult());
			}
			param.setParams(dubboDocInfo.getParams());
		} else {
			param.setVersion(1);
		}
		if (StringUtils.isNotBlank(paramsJson)) {
			param.setParams(JSON.parseArray(paramsJson, DubboDocInfo.DubboDocParam.class));
		}
		param.setFunction(function);
		docInfoMap.put(function, param);
		List<DubboDocInfo> docInfoList = new ArrayList<>(docInfoMap.values());
		mgDubboStorageService.put(StorageKeys.DUBBO_SERVICE_DOC, JSON.toJSONString(docInfoList));
		return DocResponseJson.ok(param);
	}
	
	/**
	 * 通过nacos方式获取所有服务
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	private List<DubboInfo> getDubboInfoByNacos() {
		List<DubboInfo> providerList = new LinkedList<>();
		String[] nacosServiceArr = nacosService.split(";");
		for (String service : nacosServiceArr) {
			String resultStr = HttpUtil.get(nacosUrl + "/v1/ns/instance/list?serviceName=providers:" + service);
			NacosDubboInfo dubboInstance = JSON.parseObject(resultStr, NacosDubboInfo.class);
			List<NacosDubboInfo.HostsBean> hosts = dubboInstance.getHosts();
			DubboInfo dubboInfo = new DubboInfo();
			List<DubboInfo.DubboNodeInfo> nodeList = new LinkedList<>();
			for (NacosDubboInfo.HostsBean host : hosts) {
				DubboInfo.DubboNodeInfo dubboNodeInfo = new DubboInfo.DubboNodeInfo();
				dubboNodeInfo.setIp(host.getIp());
				dubboNodeInfo.setPort(host.getPort());
				dubboNodeInfo.setInterfaceX(host.getMetadata().getInterfaceX());
				dubboNodeInfo.setMethods(host.getMetadata().getMethods().split(","));
				dubboNodeInfo.setApplication(host.getMetadata().getApplication());
				nodeList.add(dubboNodeInfo);
			}
			dubboInfo.setInterfaceX(service);
			dubboInfo.setNodeList(nodeList);
			providerList.add(dubboInfo);
		}
		return providerList;
	}
	
	/**
	 * 通过Zookeeper方式获取所有服务
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	private List<DubboInfo> getDubboInfoByZookeeper() throws Exception {
		List<String> dubboList = serverClient.getChildren().forPath("/dubbo");
		if (dubboList == null || dubboList.isEmpty()) {
			return Collections.emptyList();
		}
		List<DubboInfo> providerList = new LinkedList<>();
		for (String dubboStr : dubboList) {
			String path = "/dubbo/" + dubboStr + "/providers";
			if (metadataClient.checkExists().forPath(path) == null) {
				continue;
			}
			List<String> providers = serverClient.getChildren().forPath(path);
			List<DubboInfo.DubboNodeInfo> nodeList = providers.stream().map(val -> {
				String tempStr = val;
				try {
					tempStr = URLDecoder.decode(val, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				// IP和端口
				String ipPort = tempStr.substring(tempStr.indexOf("://") + 3);
				ipPort = ipPort.substring(0, ipPort.indexOf("/"));
				String[] ipPortArr = ipPort.split(":");
				// 参数
				Map<String, String> paramMap = new HashMap<>();
				String params = tempStr.substring(tempStr.indexOf("?"));
				String[] paramsArr = params.split("&");
				for (String param : paramsArr) {
					String[] split = param.split("=");
					paramMap.put(split[0], split[1]);
				}
				DubboInfo.DubboNodeInfo dubboNodeInfo = new DubboInfo.DubboNodeInfo();
				dubboNodeInfo.setIp(ipPortArr[0]);
				dubboNodeInfo.setPort(NumberUtils.toInt(ipPortArr[1]));
				dubboNodeInfo.setInterfaceX(paramMap.get("interface"));
				dubboNodeInfo.setMethods(paramMap.get("methods").split(","));
				dubboNodeInfo.setApplication(paramMap.get("application"));
				return dubboNodeInfo;
			}).collect(Collectors.toList());
			DubboInfo dubboInfo = new DubboInfo();
			dubboInfo.setInterfaceX(dubboStr);
			dubboInfo.setNodeList(nodeList);
			providerList.add(dubboInfo);
		}
		return providerList;
	}
	
	private DubboDocInfo getDefinitionByMetadata(DubboRequestParam param) {
		try {
			String path = getNodePath(param.getService(), null, null, param.getApplication());
			if (metadataClient.checkExists().forPath(path) == null) {
				return null;
			}
			String resultType = null;
			String metadata = new String(metadataClient.getData().forPath(path));
			FullServiceDefinition definition = JSON.parseObject(metadata, FullServiceDefinition.class);
			List<DubboDocInfo.DubboDocParam> paramList = new LinkedList<>();
			for (MethodDefinition method : definition.getMethods()) {
				if (Objects.equals(method.getName(), param.getMethod())) {
					String[] parameterTypes = method.getParameterTypes();
					resultType = method.getReturnType();
					for (int i = 0; i < parameterTypes.length; i++) {
						DubboDocInfo.DubboDocParam docParam = new DubboDocInfo.DubboDocParam();
						docParam.setParamType(parameterTypes[i]);
						docParam.setParamName("arg" + i);
						paramList.add(docParam);
					}
				}
			}
			DubboDocInfo dubboDocInfo = new DubboDocInfo();
			dubboDocInfo.setParams(paramList);
			dubboDocInfo.setResultType(resultType);
			return dubboDocInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private DubboDocInfo getDefinitionByJar(DubboRequestParam param) {
		String resultType = null;
		List<DubboDocInfo.DubboDocParam> paramList = new LinkedList<>();
		try {
			Class clazz = Class.forName(param.getService());
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.equals(param.getMethod())) {
					resultType = method.getGenericReturnType().getTypeName();
					Type[] parameterTypes = method.getGenericParameterTypes();
					Parameter[] parameters = method.getParameters();
					for (int i = 0; i < parameterTypes.length; i++) {
						DubboDocInfo.DubboDocParam docParam = new DubboDocInfo.DubboDocParam();
						docParam.setParamName(parameters[i].getName());
						docParam.setParamType(parameterTypes[i].getTypeName());
						paramList.add(docParam);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		DubboDocInfo dubboDocInfo = new DubboDocInfo();
		dubboDocInfo.setParams(paramList);
		dubboDocInfo.setResultType(resultType);
		return dubboDocInfo;
	}
	
	String toRootDir() {
		if (root.equals(Constants.PATH_SEPARATOR)) {
			return root;
		}
		return root + Constants.PATH_SEPARATOR;
	}
	
	String getNodePath(String serviceInterface, String version, String group, String application) {
		MetadataIdentifier metadataIdentifier = new MetadataIdentifier(serviceInterface, version, group, Constants.PROVIDER_SIDE, application);
		return toRootDir() + metadataIdentifier.getUniqueKey(MetadataIdentifier.KeyTypeEnum.PATH) + Constants.PATH_SEPARATOR + METADATA_NODE_NAME;
	}
	
	
}

