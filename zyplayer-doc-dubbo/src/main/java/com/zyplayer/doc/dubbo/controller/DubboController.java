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
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
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
	
	@Value("${zyplayer.doc.dubbo.zookeeper.url:}")
	private String zookeeperUrl;
	@Value("${zyplayer.doc.dubbo.nacos.url:}")
	private String nacosUrl;
	@Value("${zyplayer.doc.dubbo.nacos.service:}")
	private String nacosService;
	@Resource
	private MgDubboStorageService mgDubboStorageService;
	
	/**
	 * 重新获取所有的服务列表
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/reloadService")
	public DocResponseJson loadService() throws Exception {
		List<DubboInfo> providerList;
		if (StringUtils.isBlank(zookeeperUrl)) {
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
	@PostMapping(value = "/findDocInfo")
	public DocResponseJson findDocInfo(DubboRequestParam param) {
		List<DubboDocInfo.DubboDocParam> paramList = new LinkedList<>();
		try {
			Class clazz = Class.forName(param.getService());
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.equals(param.getMethod())) {
					Type[] parameterTypes = method.getGenericParameterTypes();
					for (Type clas : parameterTypes) {
						DubboDocInfo.DubboDocParam docParam = new DubboDocInfo.DubboDocParam();
						docParam.setParamType(clas.getTypeName());
						paramList.add(docParam);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			return DocResponseJson.warn("未找到指定类，请引入相关包，类名：" + param.getService());
		}
		if (paramList.isEmpty()) {
			return DocResponseJson.ok();
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
			dubboDocInfo.setParams(paramList);
			dubboDocInfo.setFunction(function);
			dubboDocInfo.setVersion(1);
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
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperUrl, retryPolicy);
		client.start();
		List<String> dubboList = client.getChildren().forPath("/dubbo");
		if (dubboList == null || dubboList.isEmpty()) {
			return Collections.emptyList();
		}
		List<DubboInfo> providerList = new LinkedList<>();
		for (String dubboStr : dubboList) {
			List<String> providers = client.getChildren().forPath("/dubbo/" + dubboStr + "/providers");
			
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
		client.close();
		return providerList;
	}
}

