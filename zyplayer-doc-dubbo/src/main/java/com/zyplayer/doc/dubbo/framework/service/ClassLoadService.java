package com.zyplayer.doc.dubbo.framework.service;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.annotation.DocMethod;
import com.zyplayer.doc.annotation.DocParam;
import com.zyplayer.doc.dubbo.framework.bean.DubboResponseInfo;
import com.zyplayer.doc.dubbo.framework.bean.InterfaceType;
import com.zyplayer.doc.dubbo.framework.constant.BaseType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoadService {
	private static Logger logger = LoggerFactory.getLogger(ClassLoadService.class);
	
//	public static void main(String[] args) throws Exception {
//		String serviceName = "com.zyplayer.dubbo.service.AnnotateService";
//		String jarGroup = "com.zyplayer";
//		String jarArtifact = "dubbo-api";
//		String jarVersion = "1.0";
//		String basePath = "file:D:/maven/repository";
////		String basePath = "http://nexus.dmall.com:8081/nexus/content/groups/public";
//		new ClassLoadService().loadServerMethod(serviceName, basePath, jarGroup, jarArtifact, jarVersion);
//	}
	
	public static void main(String[] args) throws Exception {
		String serviceName = "com.dmall.data.service.dubbo.DataIndicatorsService";
		String jarGroup = "com.dmall.data";
		String jarArtifact = "data-api-client";
		String jarVersion = "1.0.9.SNAPSHOTS";
		String basePath = "http://nexus.dmall.com:8081/nexus/content/groups/public";
		new ClassLoadService().loadServerMethod(serviceName, basePath, jarGroup, jarArtifact, jarVersion);
	}
	
	public void loadServerMethod(String serverName, String basePath, String jarGroup, String jarArtifact, String jarVersion) throws Exception {
		// jar:file:D:/maven/repository/com/zyplayer/dubbo-api/1.0/dubbo-api-1.0.jar!/
		String jarPath = jarGroup.replaceAll("\\.", "/") + "/" + jarArtifact + "/" + jarVersion + "/" + jarArtifact + "-" + jarVersion + ".jar";
		URL jarUrl = new URL("jar:" + basePath + "/" + jarPath + "!/");
		JarFile jar = ((JarURLConnection) jarUrl.openConnection()).getJarFile();
		JarEntry jarEntry = jar.getJarEntry(serverName.replaceAll("\\.", "/") + ".class");
		if (jarEntry == null) {
			logger.info("未找到类");
			return;
		}
		URL fileUrl = new URL(basePath + "/" + jarPath);
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
			field.setAccessible(true);
			try {
				paramList.add(this.getInfoByField(classLoader, field, recursion));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dubboResponseInfo.setParams(paramList);
		return dubboResponseInfo;
	}
}
