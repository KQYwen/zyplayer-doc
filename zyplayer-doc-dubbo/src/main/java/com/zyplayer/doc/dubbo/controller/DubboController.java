package com.zyplayer.doc.dubbo.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.dubbo.controller.param.DubboRequestParam;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.*;
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
	@GetMapping(value = "/reloadService")
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
	@GetMapping(value = "/request")
	public DocResponseJson request(DubboRequestParam param) {
		DubboInfo.DubboNodeInfo dubboNodeInfo = new DubboInfo.DubboNodeInfo();
		dubboNodeInfo.setIp(param.getIp());
		dubboNodeInfo.setPort(param.getPort());
		dubboNodeInfo.setInterfaceX(param.getService());
		String[] paramTypes = Optional.ofNullable(param.getParamTypes()).orElse(new String[]{});
		Object[] params = Optional.ofNullable(param.getParams()).orElse(new Object[]{});
		GenericService bean = ReferenceConfigHolder.getBean(dubboNodeInfo);
		try {
			Object result = bean.$invoke(param.getMethod(), paramTypes, params);
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
	@GetMapping(value = "/getDocList")
	public DocResponseJson getDocList() {
		String dubboServiceList = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_LIST);
		String dubboServiceDoc = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_DOC);
		if (StringUtils.isBlank(dubboServiceList)) {
			return DocResponseJson.ok();
		}
		List<DubboInfo> providerList = JSON.parseArray(dubboServiceList, DubboInfo.class);
		if (StringUtils.isNotBlank(dubboServiceDoc)) {
			List<DubboDocInfo> docInfoList = JSON.parseArray(dubboServiceDoc, DubboDocInfo.class);
			Map<String, DubboDocInfo> docInfoMap = docInfoList.stream().collect(Collectors.toMap(DubboDocInfo::getService, val -> val));
			for (DubboInfo dubboInfo : providerList) {
				dubboInfo.setDocInfo(docInfoMap.get(dubboInfo.getInterfaceX()));
			}
		}
		return DocResponseJson.ok(providerList);
	}
	
	/**
	 * 保存文档
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@GetMapping(value = "/saveDoc")
	public DocResponseJson saveDoc(DubboDocInfo param) {
		String dubboServiceDoc = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_DOC);
		Map<String, DubboDocInfo> docInfoMap = new HashMap<>();
		if (StringUtils.isNotBlank(dubboServiceDoc)) {
			List<DubboDocInfo> docInfoList = JSON.parseArray(dubboServiceDoc, DubboDocInfo.class);
			docInfoMap = docInfoList.stream().collect(Collectors.toMap(DubboDocInfo::getService, val -> val));
		}
		DubboDocInfo dubboDocInfo = docInfoMap.get(param.getService());
		if (dubboDocInfo != null) {
			Integer newVersion = Optional.ofNullable(param.getVersion()).orElse(1);
			Integer oldVersion = Optional.ofNullable(dubboDocInfo.getVersion()).orElse(1);
			if (oldVersion > newVersion) {
				return DocResponseJson.warn("已有用户在您之前修改过文档，请刷新后再修改");
			}
			param.setVersion(oldVersion + 1);
		} else {
			param.setVersion(1);
		}
		docInfoMap.put(param.getService(), param);
		List<DubboDocInfo> docInfoList = new ArrayList<>(docInfoMap.values());
		mgDubboStorageService.put(StorageKeys.DUBBO_SERVICE_DOC, JSON.toJSONString(docInfoList));
		return DocResponseJson.ok();
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
		return providerList;
	}
}

