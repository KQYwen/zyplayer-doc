package com.zyplayer.doc.dubbo.framework.bean;

import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 暮光：城中城
 * @since 2019年1月10日
 **/
public class ReferenceConfigHolder {
	private static Map<String, ReferenceConfig> referenceConfigMap = new ConcurrentHashMap<>();
	
	public static GenericService getBean(DubboInfo.DubboNodeInfo dubboNodeInfo) {
		String url = "dubbo://" + dubboNodeInfo.getIp() + ":" + dubboNodeInfo.getPort();
		String referenceKey = url + "_" + StringUtils.defaultIfBlank(dubboNodeInfo.getVersion(), "0") + "_" + StringUtils.defaultIfBlank(dubboNodeInfo.getGroup(), "0");
		ReferenceConfig referenceConfig = referenceConfigMap.get(referenceKey);
		if (referenceConfig == null) {
			synchronized (ReferenceConfigHolder.class) {
				ApplicationConfig application = new ApplicationConfig();
				application.setName("zyplayer-doc-consume");
				// 参考：http://dubbo.apache.org/zh-cn/docs/user/configuration/api.html
				// 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
				referenceConfig = new ReferenceConfig<>();
				// 如果点对点直连，可以用reference.setUrl()指定目标地址，设置url后将绕过注册中心，
				// 其中，协议对应provider.setProtocol()的值，端口对应provider.setPort()的值，
				// 路径对应service.setPath()的值，如果未设置path，缺省path为接口名
				referenceConfig.setUrl(url);
				String groupAppend = StringUtils.isNotBlank(dubboNodeInfo.getGroup()) ? dubboNodeInfo.getGroup() + "/" : "";
				referenceConfig.setInterface(groupAppend + dubboNodeInfo.getInterfaceX());
				referenceConfig.setGeneric(true);
				referenceConfig.setApplication(application);
				referenceConfig.setTimeout(5000);
				referenceConfig.setVersion(dubboNodeInfo.getVersion());
				referenceConfig.setGroup(dubboNodeInfo.getGroup());
				referenceConfigMap.put(referenceKey, referenceConfig);
			}
		}
		// 本项目没有dubbo里面申明的类，快放弃时看源码发现可以设置generic返回一个GenericService对象，通过$invoke去操作具体方法，感觉又打开了一扇大门
		// 本项目选择的不入侵的方式管理文档，所以文档里面就必须手动加参数，写文档那些了
		return (GenericService) referenceConfig.get();
	}
	
}
