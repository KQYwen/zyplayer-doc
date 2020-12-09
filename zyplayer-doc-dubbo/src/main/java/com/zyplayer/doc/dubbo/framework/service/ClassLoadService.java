package com.zyplayer.doc.dubbo.framework.service;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.annotation.DocMethod;
import com.zyplayer.doc.annotation.DocParam;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.dubbo.framework.bean.DubboResponseInfo;
import com.zyplayer.doc.dubbo.framework.bean.InterfaceType;
import com.zyplayer.doc.dubbo.framework.constant.BaseType;
import com.zyplayer.doc.dubbo.framework.constant.DubboDocConst;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类加载服务
 *
 * @author 暮光：城中城
 * @since 2020年11月08日
 */
@Service
public class ClassLoadService {
	private static Logger logger = LoggerFactory.getLogger(ClassLoadService.class);
	
	@Value("${zyplayer.doc.dubbo.doc-lib-path}")
	private String zyplayerDocDubboLibPath;
	
	private URLClassLoader docClassLoader;
	
	/**
	 * 获取文档jar类加载器
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	private URLClassLoader getDocClassLoader() throws Exception {
		if (docClassLoader != null) {
			return docClassLoader;
		}
		if (!FileUtil.isFile(zyplayerDocDubboLibPath + "/" + DubboDocConst.DUBBO_DOC_LIB_NAME)) {
			throw new ConfirmException("请先上传文档JAR");
		}
		synchronized (ClassLoadService.class) {
			// file:D:/maven/repository/com/zyplayer/dubbo-api/1.0/dubbo-api-1.0.jar
			if (docClassLoader == null) {
				URL fileUrl = new URL("file:/" + zyplayerDocDubboLibPath + "/" + DubboDocConst.DUBBO_DOC_LIB_NAME);
				docClassLoader = new URLClassLoader(new URL[]{fileUrl}, Thread.currentThread().getContextClassLoader());
			}
		}
		return docClassLoader;
	}
	
	/**
	 * 关闭类加载器，callback中可对文件进行覆盖上传
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	public void closeClassLoad(ClassLoaderCallback callback) throws Exception {
		synchronized (ClassLoadService.class) {
			try {
				if (docClassLoader != null) {
					docClassLoader.close();
				}
			} catch (Exception e) {
				logger.error("关闭类加载器失败", e);
			}
			docClassLoader = null;
			if (callback != null) {
				// callback方式，防止刚close，马上又被别人new出来了
				// callback主要是删除文件，如果文件被加载了应该删除和创建不了（我没测，理论上会是这样）
				callback.callback();
			}
		}
	}
	
	/**
	 * 加载类
	 *
	 * @author 暮光：城中城
	 * @since 2020年11月08日
	 */
	public Class<?> loadClass(String serverName) throws Exception {
		try {
			return this.getDocClassLoader().loadClass(serverName);
		} catch (Exception e) {
			// 失败之后先关闭再去加载一次
			this.closeClassLoad(null);
			return this.getDocClassLoader().loadClass(serverName);
		}
	}

//	public static void main(String[] args) throws Exception {
//		String serviceName = "com.zyplayer.dubbo.service.AnnotateService";
//		String jarGroup = "com.zyplayer";
//		String jarArtifact = "dubbo-api";
//		String jarVersion = "1.0";
//		String basePath = "file:D:/maven/repository";
////		String basePath = "http://nexus.zyplayer.com:8081/nexus/content/groups/public";
//		new ClassLoadService().loadServerMethod(serviceName, basePath, jarGroup, jarArtifact, jarVersion);
//	}

//	public static void main(String[] args) throws Exception {
//		String serviceName = "com.zyplayer.data.service.dubbo.DataIndicatorsService";
//		String jarGroup = "com.zyplayer.data";
//		String jarArtifact = "data-api-client";
//		String jarVersion = "1.0.9.SNAPSHOTS";
//		String basePath = "http://nexus.zyplayer.com:8081/nexus/content/groups/public";
//		new ClassLoadService().loadServerMethod(serviceName, basePath, jarGroup, jarArtifact, jarVersion);
//	}
	
	public void loadServerMethod(String serverName) throws Exception {
		// jar:file:D:/maven/repository/com/zyplayer/dubbo-api/1.0/dubbo-api-1.0.jar!/
		String docJarFileUrl = "file:/" + zyplayerDocDubboLibPath + "/" + DubboDocConst.DUBBO_DOC_LIB_NAME;
		URL jarUrl = new URL("jar:" + docJarFileUrl + "!/");
		JarFile jar = ((JarURLConnection) jarUrl.openConnection()).getJarFile();
		JarEntry jarEntry = jar.getJarEntry(serverName.replaceAll("\\.", "/") + ".class");
		if (jarEntry == null) {
			logger.info("未找到类");
			return;
		}
		URL fileUrl = new URL(docJarFileUrl);
		URLClassLoader classLoader = new URLClassLoader(new URL[]{fileUrl}, Thread.currentThread().getContextClassLoader());
		Class<?> clazz = classLoader.loadClass(serverName);
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			Map<String, String> docParamMap = new HashMap<>();
			DocParam[] annotation = method.getAnnotationsByType(DocParam.class);
			if (annotation != null) {
				for (DocParam docParam : annotation) {
					docParamMap.put(docParam.name(), docParam.value());
				}
			}
			String methodDesc = null;
			Class<?> returnType = method.getReturnType();
			DubboResponseInfo responseInfo = this.getInfoByClass(classLoader, returnType, 0);
			DocMethod docMethod = method.getAnnotation(DocMethod.class);
			if (docMethod != null) {
				methodDesc = docMethod.value();
				responseInfo.setDesc(docMethod.response());
			}
			String methodName = method.getName();
			Type[] parameterTypes = method.getGenericParameterTypes();
			Parameter[] parameters = method.getParameters();
			List<DubboResponseInfo> paramList = new LinkedList<>();
			for (int i = 0; i < parameterTypes.length; i++) {
				DubboResponseInfo responseInfoParam = this.getInfoByType(classLoader, parameterTypes[i], parameters[i].getType(), 0);
				String desc = docParamMap.get(parameters[i].getName());
				if (StringUtils.isNotBlank(desc)) {
					responseInfoParam.setDesc(desc);
				}
				responseInfoParam.setName(parameters[i].getName());
				paramList.add(responseInfoParam);
			}
			logger.info("methodName：{}，methodDesc：{}，paramList：{}，responseInfo：{}", methodName, methodDesc, JSON.toJSONString(paramList), JSON.toJSONString(responseInfo));
		}
	}
	
	private DubboResponseInfo getInfoByType(URLClassLoader classLoader, String resultType, Integer recursion) {
		DubboResponseInfo dubboResponseInfo = new DubboResponseInfo(resultType);
		if (BaseType.isBaseType(resultType)) {
			return dubboResponseInfo;
		}
		try {
			Class<?> clazz = classLoader.loadClass(resultType);
			DubboResponseInfo responseInfo = this.getInfoByClass(classLoader, clazz, recursion);
			if (responseInfo.getDesc() == null) {
				responseInfo.setDesc(dubboResponseInfo.getDesc());
			}
			return responseInfo;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dubboResponseInfo;
	}
	
	private DubboResponseInfo getInfoByType(URLClassLoader classLoader, Type type, Class<?> typeClazz, Integer recursion) throws Exception {
		String typeName = type.getTypeName();
		DubboResponseInfo dubboResponseInfo = new DubboResponseInfo(typeName);
		dubboResponseInfo.setName(type.getTypeName());
		if (BaseType.isBaseType(typeName)) {
			return dubboResponseInfo;
		}
		Class<?> clazz = null;
		if (typeClazz.isArray()) {
			dubboResponseInfo.setInterfaceType(InterfaceType.arr.name());
			clazz = typeClazz.getComponentType();
		} else if (typeClazz.isAssignableFrom(Map.class)) {
			dubboResponseInfo.setInterfaceType(InterfaceType.map.name());
			if (type instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
				Class<?> clazzKey = classLoader.loadClass(actualTypeArguments[0].getTypeName());
				dubboResponseInfo.setKeyInfo(this.getInfoByClass(classLoader, clazzKey, recursion));
				clazz = classLoader.loadClass(actualTypeArguments[1].getTypeName());
			}
		} else if (typeClazz.isAssignableFrom(List.class) || typeClazz.isAssignableFrom(Set.class)) {
			if (type instanceof ParameterizedType) {
				String typeNameSub = ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
				clazz = classLoader.loadClass(typeNameSub);
			}
			if (typeClazz.isAssignableFrom(List.class)) {
				dubboResponseInfo.setInterfaceType(InterfaceType.list.name());
			} else if (typeClazz.isAssignableFrom(Set.class)) {
				dubboResponseInfo.setInterfaceType(InterfaceType.set.name());
			}
		} else {
			clazz = classLoader.loadClass(typeName);
		}
		if (clazz != null) {
			DubboResponseInfo responseInfo = this.getInfoByClass(classLoader, clazz, recursion);
			responseInfo.setName(typeName);
			// 字段注释 优先于 类上的注释
			if (StringUtils.isNotBlank(dubboResponseInfo.getDesc())) {
				responseInfo.setDesc(dubboResponseInfo.getDesc());
			}
			responseInfo.setKeyInfo(dubboResponseInfo.getKeyInfo());
			responseInfo.setInterfaceType(dubboResponseInfo.getInterfaceType());
			return responseInfo;
		}
		return dubboResponseInfo;
	}
	
	private DubboResponseInfo getInfoByField(URLClassLoader classLoader, Field field, Integer recursion) throws Exception {
		String typeName = field.getType().getTypeName();
		DubboResponseInfo dubboResponseInfo = new DubboResponseInfo(typeName);
		dubboResponseInfo.setName(field.getName());
		DocParam docResponse = field.getAnnotation(DocParam.class);
		if (docResponse != null) {
			dubboResponseInfo.setDesc(docResponse.value());
		}
		if (BaseType.isBaseType(typeName)) {
			return dubboResponseInfo;
		}
		Class<?> clazz = null;
		if (field.getType().isArray()) {
			dubboResponseInfo.setInterfaceType(InterfaceType.arr.name());
			clazz = field.getType().getComponentType();
		} else if (field.getType().isAssignableFrom(Map.class)) {
			dubboResponseInfo.setInterfaceType(InterfaceType.map.name());
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
				Class<?> clazzKey = classLoader.loadClass(actualTypeArguments[0].getTypeName());
				dubboResponseInfo.setKeyInfo(this.getInfoByClass(classLoader, clazzKey, recursion));
				clazz = classLoader.loadClass(actualTypeArguments[1].getTypeName());
			}
		} else if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(Set.class)) {
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {
				String typeNameSub = ((ParameterizedType) genericType).getActualTypeArguments()[0].getTypeName();
				clazz = classLoader.loadClass(typeNameSub);
			}
			if (field.getType().isAssignableFrom(List.class)) {
				dubboResponseInfo.setInterfaceType(InterfaceType.list.name());
			} else if (field.getType().isAssignableFrom(Set.class)) {
				dubboResponseInfo.setInterfaceType(InterfaceType.set.name());
			}
		} else {
			clazz = classLoader.loadClass(typeName);
		}
		if (clazz != null) {
			DubboResponseInfo responseInfo = this.getInfoByClass(classLoader, clazz, recursion);
			responseInfo.setName(field.getName());
			// 字段注释 优先于 类上的注释
			if (StringUtils.isNotBlank(dubboResponseInfo.getDesc())) {
				responseInfo.setDesc(dubboResponseInfo.getDesc());
			}
			responseInfo.setKeyInfo(dubboResponseInfo.getKeyInfo());
			responseInfo.setInterfaceType(dubboResponseInfo.getInterfaceType());
			return responseInfo;
		}
		return dubboResponseInfo;
	}
	
	private DubboResponseInfo getInfoByClass(URLClassLoader classLoader, Class<?> clazz, Integer recursion) {
		DubboResponseInfo dubboResponseInfo = new DubboResponseInfo(clazz.getTypeName());
		DocParam docResponse = clazz.getAnnotation(DocParam.class);
		if (docResponse != null) {
			dubboResponseInfo.setDesc(docResponse.value());
		}
		if (BaseType.isBaseType(clazz.getTypeName())) {
			return dubboResponseInfo;
		}
		recursion++;
		// 默认最大支持3个层级，感觉已经满足了
		if (recursion > 3) {
			return dubboResponseInfo;
		}
		List<DubboResponseInfo> paramList = new LinkedList<>();
		Field[] fieldArr = clazz.getDeclaredFields();
		for (Field field : fieldArr) {
			try {
				field.setAccessible(true);
				paramList.add(this.getInfoByField(classLoader, field, recursion));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dubboResponseInfo.setParams(paramList);
		return dubboResponseInfo;
	}
}
