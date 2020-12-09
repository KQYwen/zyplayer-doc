package com.zyplayer.doc.dubbo.controller;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.dubbo.controller.param.DubboRequestParam;
import com.zyplayer.doc.dubbo.controller.vo.DubboInfoVo;
import com.zyplayer.doc.dubbo.framework.bean.DubboDocInfo;
import com.zyplayer.doc.dubbo.framework.bean.DubboInfo;
import com.zyplayer.doc.dubbo.framework.bean.ReferenceConfigHolder;
import com.zyplayer.doc.dubbo.framework.constant.DubboDocConst;
import com.zyplayer.doc.dubbo.framework.constant.StorageKeys;
import com.zyplayer.doc.dubbo.framework.service.ClassLoadService;
import com.zyplayer.doc.dubbo.framework.service.MgDubboStorageService;
import com.zyplayer.doc.dubbo.framework.service.NacosDocService;
import com.zyplayer.doc.dubbo.framework.service.ZookeeperDocService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
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
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-dubbo/doc-dubbo")
public class DubboController {
	private static Logger logger = LoggerFactory.getLogger(DubboController.class);
	
	@Value("${zyplayer.doc.dubbo.doc-lib-path}")
	private String zyplayerDocDubboLibPath;
	
	@Resource
	private MgDubboStorageService mgDubboStorageService;
	@Resource
	private ZookeeperDocService zookeeperDocService;
	@Resource
	private NacosDocService nacosDocService;
	@Resource
	ClassLoadService classLoadService;
	
	/**
	 * 重新获取所有的服务列表
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月10日
	 **/
	@PostMapping(value = "/reloadService")
	public DocResponseJson loadService() {
		List<DubboInfo> providerList;
		try {
			if (!zookeeperDocService.isEnable()) {
				if (!nacosDocService.isEnable()) {
					return DocResponseJson.warn("zyplayer.doc.dubbo.zookeeper.url、zyplayer.doc.dubbo.nacos.url 参数均未配置");
				}
				logger.info("zookeeper参数未配置，使用nacos配置");
				providerList = nacosDocService.getDubboInfoByNacos();
			} else {
				providerList = zookeeperDocService.getDubboInfoByZookeeper();
			}
			mgDubboStorageService.put(StorageKeys.DUBBO_SERVICE_LIST, JSON.toJSONString(providerList));
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("获取服务列表失败");
		}
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
		dubboNodeInfo.setVersion(param.getVersion());
		dubboNodeInfo.setGroup(param.getGroup());
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
					Class<?> aClass = classLoadService.loadClass(type);
					List<?> objects = JSON.parseArray(paramStr, aClass);
					queryTypeList.add(typeStr);
					queryParamList.add(objects);
				} else if (typeStr.matches("java\\.util\\.List<.+>")) {
					Pattern pattern = Pattern.compile("java\\.util\\.List<(.+)>");
					Matcher matcher = pattern.matcher(typeStr);
					if (matcher.find()) {
						String group = matcher.group(1);
						Class<?> aClass = classLoadService.loadClass(group);
						List<?> objects = JSON.parseArray(paramStr, aClass);
						queryParamList.add(objects);
						queryTypeList.add("java.util.List");
					}
				} else {
					Class<?> aClass = classLoadService.loadClass(typeStr);
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
		try {
			GenericService bean = ReferenceConfigHolder.getBean(dubboNodeInfo);
			if (bean == null) {
				return DocResponseJson.warn("操作失败，获取dubbo服务失败");
			}
			Object result = bean.$invoke(param.getMethod(), queryTypeList.toArray(new String[]{}), queryParamList.toArray());
			return DocResponseJson.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("操作失败，" + e.getMessage());
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
		if (StringUtils.isBlank(dubboServiceList)) {
			return DocResponseJson.ok();
		}
		DubboInfoVo dubboInfoVo = new DubboInfoVo();
		List<DubboInfo> providerList = JSON.parseArray(dubboServiceList, DubboInfo.class);
		dubboInfoVo.setServerList(providerList);
		// TODO 感觉不该在这里返回，应该每次点击的时候去包里面重新找一次文档
		//  但是这样的话只存了一个大的json，容易出现覆盖问题，看来还得分接口方法一条一条记录的存，才能针对方法来修改
		String dubboServiceDoc = mgDubboStorageService.get(StorageKeys.DUBBO_SERVICE_DOC);
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
		DubboDocInfo definition = zookeeperDocService.getDefinitionByJar(param);
		if (definition == null) {
			definition = zookeeperDocService.getDefinitionByMetadata(param);
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
			dubboDocInfo.setResultType(definition.getResultType());
			dubboDocInfo.setService(param.getService());
			dubboDocInfo.setMethod(param.getMethod());
			dubboDocInfo.setFunction(function);
			dubboDocInfo.setVersion(1);
			docInfoMap.put(function, dubboDocInfo);
		} else {
			// 根据参数顺序，把之前写的参数说明放到新的上面去
			if (CollectionUtils.isNotEmpty(definition.getParams()) && CollectionUtils.isNotEmpty(dubboDocInfo.getParams())) {
				for (int i = 0; i < definition.getParams().size() && i < dubboDocInfo.getParams().size(); i++) {
					DubboDocInfo.DubboDocParam dubboDocNew = definition.getParams().get(i);
					DubboDocInfo.DubboDocParam dubboDocOld = dubboDocInfo.getParams().get(i);
					dubboDocNew.setParamDesc(StringUtils.defaultIfBlank(dubboDocOld.getParamDesc(), dubboDocNew.getParamDesc()));
				}
			}
			dubboDocInfo.setParams(definition.getParams());
			dubboDocInfo.setResultType(definition.getResultType());
		}
		List<DubboDocInfo> docInfoList = new ArrayList<>(docInfoMap.values());
		mgDubboStorageService.put(StorageKeys.DUBBO_SERVICE_DOC, JSON.toJSONString(docInfoList));
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
	 * 上传文档jar
	 *
	 * @author 暮光：城中城
	 * @since 2020年10月08日
	 **/
	@PostMapping(value = "/uploadDocJar")
	public DocResponseJson uploadDocJar(@RequestParam("file") MultipartFile file) {
		File newFileDir = new File(zyplayerDocDubboLibPath);
		if (!newFileDir.exists() && !newFileDir.mkdirs()) {
			return DocResponseJson.warn("创建文件夹失败");
		}
		String fileSuffix = null;
		String fileName = file.getOriginalFilename();
		if (fileName != null) {
			fileSuffix = fileName.substring(fileName.lastIndexOf("."));
		}
		if (!Objects.equals(".jar", fileSuffix)) {
			return DocResponseJson.warn("仅支持jar后缀的文件格式上传");
		}
		try {
			classLoadService.closeClassLoad(() -> {
				File docJarFile = new File(zyplayerDocDubboLibPath + "/" + DubboDocConst.DUBBO_DOC_LIB_NAME);
				if (docJarFile.exists()) {
					docJarFile.delete();
				}
				file.transferTo(docJarFile);
			});
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("保存文件失败：" + e.getMessage());
		}
		return DocResponseJson.ok();
	}
	
}

