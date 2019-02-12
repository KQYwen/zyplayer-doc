package com.zyplayer.doc.dubbo.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.dubbo.framework.bean.DubboInfo;
import com.zyplayer.doc.dubbo.framework.bean.NacosDubboInfo;
import com.zyplayer.doc.dubbo.framework.bean.ReferenceConfigHolder;
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

import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
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
	
	@GetMapping(value = "/getList")
	public DocResponseJson getDataSourceList() throws Exception {
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
		GenericService bean = ReferenceConfigHolder.getBean(providerList.get(0));
		Object o = bean.$invoke("getUserList", new String[]{}, new String[]{});
		System.out.println(o);
		return DocResponseJson.ok(providerList);
	}
	
	private List<DubboInfo> getDubboInfoByNacos() {
		List<DubboInfo> providerList = new LinkedList<>();
		String[] nacosServiceArr = nacosService.split(";");
		for (String service : nacosServiceArr) {
			String resultStr = HttpUtil.get(nacosUrl + "/v1/ns/instance/list?serviceName=" + service);
			NacosDubboInfo dubboInstance = JSON.parseObject(resultStr, NacosDubboInfo.class);
			List<NacosDubboInfo.HostsBean> hosts = dubboInstance.getHosts();
			for (NacosDubboInfo.HostsBean host : hosts) {
				DubboInfo dubboInfo = new DubboInfo();
				dubboInfo.setIp(host.getIp());
				dubboInfo.setPort(host.getPort());
				dubboInfo.setInterfaceX(host.getMetadata().getInterfaceX());
				dubboInfo.setMethods(host.getMetadata().getMethods().split(","));
				dubboInfo.setApplication(host.getMetadata().getApplication());
				providerList.add(dubboInfo);
			}
		}
		return providerList;
	}
	
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
			List<DubboInfo> dubboInfoList = providers.stream().map(val -> {
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
				DubboInfo dubboInfo = new DubboInfo();
				dubboInfo.setIp(ipPortArr[0]);
				dubboInfo.setPort(NumberUtils.toInt(ipPortArr[1]));
				dubboInfo.setInterfaceX(paramMap.get("interface"));
				dubboInfo.setMethods(paramMap.get("methods").split(","));
				dubboInfo.setApplication(paramMap.get("application"));
				return dubboInfo;
			}).collect(Collectors.toList());
			providerList.addAll(dubboInfoList);
		}
		return providerList;
	}
}

