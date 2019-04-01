package com.zyplayer.doc.grpc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.nxest.grpc.client.GrpcChannelFactory;
import com.nxest.grpc.server.GrpcService;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.grpc.controller.po.ColumnInfo;
import com.zyplayer.doc.grpc.controller.po.GrpcDocInfo;
import com.zyplayer.doc.grpc.controller.po.GrpcServiceAndColumn;
import com.zyplayer.doc.grpc.controller.po.MethodParam;
import com.zyplayer.doc.grpc.framework.config.SpringContextUtil;
import com.zyplayer.doc.grpc.framework.consts.Const;
import io.grpc.Channel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/zyplayer-doc-grpc")
public class GrpcDocController {
	
	@Resource
	GrpcChannelFactory grpcChannelFactory;
	private static Map<String, ColumnInfo> allColumnsMap = new HashMap<>();
	private static Map<String, Object> allBlockingStubMap = new HashMap<>();
	
	@RequestMapping("/service")
	public DocResponseJson service() throws Exception {
		List<Object> grpcServiceList = SpringContextUtil.getBeanWithAnnotation(GrpcService.class);
		if (grpcServiceList == null || grpcServiceList.size() <= 0) {
			return DocResponseJson.ok();
		}
		List<GrpcDocInfo> grpcDocInfoList = new LinkedList<>();
		for (Object service : grpcServiceList) {
			List<GrpcDocInfo> infoList = this.getDefinitionByJar(service.getClass());
			if (infoList != null) {
				grpcDocInfoList.addAll(infoList);
			}
		}
		// 找所有的参数列表
		Map<String, ColumnInfo> columnsMap = new HashMap<>();
		for (GrpcDocInfo grpcDocInfo : grpcDocInfoList) {
			this.setColumnsInfo(grpcDocInfo.getParamType(), columnsMap);
			this.setColumnsInfo(grpcDocInfo.getResultType(), columnsMap);
		}
		GrpcServiceAndColumn grpcServiceAndColumn = new GrpcServiceAndColumn();
		grpcServiceAndColumn.setServiceList(grpcDocInfoList);
		grpcServiceAndColumn.setColumnMap(columnsMap);
		return DocResponseJson.ok(grpcServiceAndColumn);
	}
	
	@RequestMapping("/execute")
	public String execute(String service, String params) throws Exception {
		List<GrpcDocInfo> grpcDocInfoList = this.getDefinitionByJar(Class.forName(service));
		String executeResult = "执行失败";
		if (grpcDocInfoList != null && grpcDocInfoList.size() > 0) {
			JSONObject paramMap = JSON.parseObject(params);
			executeResult = this.executeFunction(grpcDocInfoList.get(0), paramMap);
		}
		return executeResult;
	}
	
	private void setColumnsInfo(String typeName, Map<String, ColumnInfo> columnsMap) {
		if (!columnsMap.containsKey(typeName)) {
			if (allColumnsMap.containsKey(typeName)) {
				columnsMap.put(typeName, allColumnsMap.get(typeName));
			} else {
				ColumnInfo columnInfo = this.findColumnInfo(typeName);
				columnsMap.put(typeName, columnInfo);
				allColumnsMap.put(typeName, columnInfo);
			}
		}
	}
	
	private String executeFunction(GrpcDocInfo grpcDocInfo, JSONObject paramMap) throws Exception {
		Object newBuilder = this.createParamBuilder(grpcDocInfo.getParamType(), paramMap);
		if (newBuilder == null) {
			return "组装参数失败";
		}
		// 创建参数对象
		Method build = newBuilder.getClass().getMethod("build");
		Object request = build.invoke(newBuilder);
		System.out.println(request.toString());
		// 为创建过则创建
		Object blockingStub = allBlockingStubMap.get(grpcDocInfo.getService());
		if (blockingStub == null) {
			// 找到父类
			Class<?> serviceClass = Class.forName(grpcDocInfo.getService());
			String serviceSuperName = serviceClass.getSuperclass().getName();
			String superClassName = serviceSuperName.substring(0, serviceSuperName.indexOf("$"));
			// 注册
			Class<?> superClass = Class.forName(superClassName);
			Method newBlockingStubMethod = superClass.getMethod("newBlockingStub", Channel.class);
			blockingStub = newBlockingStubMethod.invoke(null, grpcChannelFactory.createChannel());
			allBlockingStubMap.put(grpcDocInfo.getService(), blockingStub);
		}
		Method sayHello = blockingStub.getClass().getMethod(grpcDocInfo.getMethod(), Class.forName(grpcDocInfo.getParamType()));
		// 执行请求
		Object response = sayHello.invoke(blockingStub, request);
		
//		Method messageMethod = response.getClass().getMethod("getMessage");
//		Object resultStr = messageMethod.invoke(response);
//		return resultStr.toString();
		
//		return JSON.toJSONString(response);
		return response.toString();
	}
	
	private Object createParamBuilder(String paramType, JSONObject paramMap) {
		try {
			Class<?> aClassSub = Class.forName(paramType);
			Method newBuilderMethod = aClassSub.getMethod("newBuilder");
			Object newBuilder = newBuilderMethod.invoke(aClassSub);
			List<MethodParam> functionTemp = this.getSetterFunction(newBuilder.getClass());
			for (MethodParam paramTemp : functionTemp) {
				Class<?> baseTypeClass = Const.BASE_TYPE.get(paramTemp.getType());
				if (baseTypeClass != null) {
					Method setNameSub = newBuilder.getClass().getMethod(paramTemp.getSetterName(), baseTypeClass);
					Object paramObjTemp = paramMap.getObject(paramTemp.getName(), baseTypeClass);
					if (paramObjTemp != null) {
						newBuilder = setNameSub.invoke(newBuilder, paramObjTemp);
					}
				} else {
					Class<?> typeClassSub = Class.forName(paramTemp.getType());
					Object newBuilderSub = this.createParamBuilder(paramTemp.getType(), paramMap);
					Method build = newBuilderSub.getClass().getMethod("build");
					Object request = build.invoke(newBuilderSub);
					Method setNameSub = newBuilder.getClass().getMethod(paramTemp.getSetterName(), typeClassSub);
					newBuilder = setNameSub.invoke(newBuilder, request);
				}
			}
			return newBuilder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String toLowerCaseFirstOne(String str) {
		if (Character.isLowerCase(str.charAt(0))) {
			return str;
		} else {
			return Character.toLowerCase(str.charAt(0)) + str.substring(1);
		}
	}
	
	private List<MethodParam> getSetterFunction(Class clazz) {
		List<MethodParam> result = new LinkedList<>();
		Method[] methods = clazz.getDeclaredMethods();
		StringBuilder nameSb = new StringBuilder();
		for (Method method : methods) {
			String methodName = method.getName();
			if (!methodName.startsWith("set") || methodName.endsWith("Bytes")
					|| methodName.equals("setField") || methodName.equals("setRepeatedField")
					|| methodName.equals("setUnknownFields")) {
				continue;
			}
			Type[] parameterTypes = method.getGenericParameterTypes();
			if (parameterTypes.length == 1) {
				String typeName = parameterTypes[0].getTypeName();
				if (typeName.endsWith("$Builder")) {
					continue;
				}
				MethodParam param = new MethodParam();
				param.setSetterName(methodName);
				param.setType(typeName);
				String paramName = methodName.substring(3);
				param.setName(this.toLowerCaseFirstOne(paramName));
				result.add(param);
			}
			nameSb.append(methodName).append(",");
		}
		System.out.println(nameSb);
		return result;
	}
	
	private ColumnInfo findColumnInfo(String paramType) {
		ColumnInfo columnInfo = new ColumnInfo();
		try {
			String paramName = paramType.substring(paramType.lastIndexOf(".") + 1);
			columnInfo.setName(this.toLowerCaseFirstOne(paramName));
			columnInfo.setType(paramType);
			List<ColumnInfo> columnInfos = this.findClassColumns(Class.forName(paramType));
			columnInfo.setParam(columnInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnInfo;
	}
	
	private List<ColumnInfo> findClassColumns(Class clazz) throws Exception {
		Method getMoney = clazz.getMethod("newBuilder");
		Object newBuilder = getMoney.invoke(clazz);
		
		List<MethodParam> paramList = this.getSetterFunction(newBuilder.getClass());
		List<ColumnInfo> subInfoList = new LinkedList<>();
		for (MethodParam param : paramList) {
			ColumnInfo info = new ColumnInfo();
			info.setType(param.getType());
			info.setName(param.getName());
			if (!Const.BASE_TYPE.containsKey(param.getType())) {
				List<ColumnInfo> classColumn = this.findClassColumns(Class.forName(param.getType()));
				info.setParam(classColumn);
			}
			subInfoList.add(info);
		}
		return subInfoList;
	}
	
	private List<GrpcDocInfo> getDefinitionByJar(Class clazz) {
		List<GrpcDocInfo> providerList = new LinkedList<>();
		try {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				String resultType = "";
				String paramType = "";
				Type[] parameterTypes = method.getGenericParameterTypes();
				for (int i = 0; i < parameterTypes.length; i++) {
					String typeName = parameterTypes[i].getTypeName();
					if (i == 0) {
						paramType = parameterTypes[i].getTypeName();
					} else if (typeName.matches("io.grpc.stub.StreamObserver<.+>")) {
						Pattern pattern = Pattern.compile("io.grpc.stub.StreamObserver<(.+)>");
						Matcher matcher = pattern.matcher(typeName);
						if (matcher.find()) {
							resultType = matcher.group(1);
						}
					}
				}
				GrpcDocInfo grpcDocInfo = new GrpcDocInfo();
				grpcDocInfo.setMethod(methodName);
				grpcDocInfo.setService(clazz.getName());
				grpcDocInfo.setParamType(paramType);
				grpcDocInfo.setResultType(resultType);
				providerList.add(grpcDocInfo);
			}
		} catch (Exception e) {
			return null;
		}
		return providerList;
	}
	
}
