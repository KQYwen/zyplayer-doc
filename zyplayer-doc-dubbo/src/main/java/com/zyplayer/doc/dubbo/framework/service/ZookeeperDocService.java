package com.zyplayer.doc.dubbo.framework.service;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.dubbo.controller.param.DubboRequestParam;
import com.zyplayer.doc.dubbo.framework.bean.DubboDocInfo;
import com.zyplayer.doc.dubbo.framework.bean.DubboInfo;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * zookeeper方式加载文档服务
 *
 * @author 暮光：城中城
 * @since 2020年11月08日
 */
@Service
public class ZookeeperDocService {
	
	@Value("${zyplayer.doc.dubbo.zookeeper.url:}")
	private String serviceZookeeperUrl;
	@Value("${zyplayer.doc.dubbo.zookeeper.metadata-url:}")
	private String metadataZookeeperUrl;
	
	@Resource
	ClassLoadService classLoadService;
	
	private CuratorFramework serverClient;
	private CuratorFramework metadataClient;
	
	private final static String DEFAULT_ROOT = "dubbo";
	private final static String METADATA_NODE_NAME = "service.data";
	private String root;
	
	/**
	 * zookeeper初始化
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	@PostConstruct
	private void init() {
		// 初始化zk注册中心连接
		if (StringUtils.isNotBlank(serviceZookeeperUrl)) {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
			serverClient = CuratorFrameworkFactory.newClient(serviceZookeeperUrl, retryPolicy);
			serverClient.start();
		}
		// 初始化zk注册中心元数据信息
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
	
	/**
	 * 是否启用了zk文档
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	public boolean isEnable() {
		return StringUtils.isNotBlank(serviceZookeeperUrl);
	}
	
	/**
	 * 通过Zookeeper方式获取所有服务
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	public List<DubboInfo> getDubboInfoByZookeeper() throws Exception {
		if (serverClient == null) {
			return null;
		}
		List<String> dubboList = serverClient.getChildren().forPath("/dubbo");
		if (dubboList == null || dubboList.isEmpty()) {
			return Collections.emptyList();
		}
		List<DubboInfo> providerList = new LinkedList<>();
		for (String dubboStr : dubboList) {
			String path = "/dubbo/" + dubboStr + "/providers";
			if (serverClient.checkExists().forPath(path) == null) {
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
				String params = tempStr.substring(tempStr.indexOf("?") + 1);
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
				dubboNodeInfo.setVersion(paramMap.get("version"));
				dubboNodeInfo.setGroup(paramMap.get("group"));
				return dubboNodeInfo;
			}).collect(Collectors.toList());
			DubboInfo dubboInfo = new DubboInfo();
			dubboInfo.setInterfaceX(dubboStr);
			dubboInfo.setNodeList(nodeList);
			providerList.add(dubboInfo);
		}
		return providerList;
	}
	
	/**
	 * 通过zk中的meta信息获取文档
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	public DubboDocInfo getDefinitionByMetadata(DubboRequestParam param) {
		if (metadataClient == null) {
			return null;
		}
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
	
	/**
	 * 通过jar方式获取文档
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	public DubboDocInfo getDefinitionByJar(DubboRequestParam param) {
		String resultType = null;
		List<DubboDocInfo.DubboDocParam> paramList = new LinkedList<>();
		try {
			Class clazz = classLoadService.loadClass(param.getService());
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
			e.printStackTrace();
			return null;
		}
		DubboDocInfo dubboDocInfo = new DubboDocInfo();
		dubboDocInfo.setParams(paramList);
		dubboDocInfo.setResultType(resultType);
		return dubboDocInfo;
	}
	
	private String toRootDir() {
		if (Objects.isNull(root)) {
			return Constants.PATH_SEPARATOR;
		}
		if (Objects.equals(Constants.PATH_SEPARATOR, root)) {
			return root;
		}
		return root + Constants.PATH_SEPARATOR;
	}
	
	private String getNodePath(String serviceInterface, String version, String group, String application) {
		MetadataIdentifier metadataIdentifier = new MetadataIdentifier(serviceInterface, version, group, Constants.PROVIDER_SIDE, application);
		return toRootDir() + metadataIdentifier.getUniqueKey(MetadataIdentifier.KeyTypeEnum.PATH) + Constants.PATH_SEPARATOR + METADATA_NODE_NAME;
	}
}
