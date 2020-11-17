package com.zyplayer.doc.dubbo.framework.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.dubbo.controller.vo.NacosServiceInfoVo;
import com.zyplayer.doc.dubbo.controller.vo.NacosServiceListVo;
import com.zyplayer.doc.dubbo.framework.bean.DubboInfo;
import com.zyplayer.doc.dubbo.framework.bean.NacosDubboInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * nacos方式加载文档服务
 *
 * @author 暮光：城中城
 * @since 2020年11月08日
 */
@Service
public class NacosDocService {
	
	@Value("${zyplayer.doc.dubbo.nacos.url:}")
	private String nacosUrl;
//	@Value("${zyplayer.doc.dubbo.nacos.service:}")
//	private String nacosService;
	
	/**
	 * 是否开启nacos文档
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	public boolean isEnable() {
		return StringUtils.isNotBlank(nacosUrl);
	}
	
	/**
	 * 通过nacos方式获取所有服务
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	public List<DubboInfo> getDubboInfoByNacos() {
		List<DubboInfo> providerList = new LinkedList<>();
		// 获取所有的服务列表
		String serviceListStr = HttpUtil.get(nacosUrl + "/v1/ns/catalog/services?withInstances=false&pageNo=1&pageSize=100000");
		NacosServiceInfoVo nacosServiceInfoVo = JSON.parseObject(serviceListStr, NacosServiceInfoVo.class);
		if (nacosServiceInfoVo == null || nacosServiceInfoVo.getServiceList().isEmpty()) {
			return providerList;
		}
		for (NacosServiceListVo service : nacosServiceInfoVo.getServiceList()) {
			String serviceName = service.getName();
			String resultStr = HttpUtil.get(nacosUrl + "/v1/ns/instance/list?serviceName=" + serviceName);
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
			if (serviceName.contains(":")) {
				serviceName = serviceName.substring(serviceName.indexOf(":") + 1);
			}
			dubboInfo.setInterfaceX(serviceName);
			dubboInfo.setNodeList(nodeList);
			providerList.add(dubboInfo);
		}
		return providerList;
	}
}
