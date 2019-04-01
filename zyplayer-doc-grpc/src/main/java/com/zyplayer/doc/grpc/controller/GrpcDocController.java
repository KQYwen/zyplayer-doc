package com.zyplayer.doc.grpc.controller;

import com.alibaba.fastjson.JSON;
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
			String paramType = grpcDocInfo.getParamType();
			String resultType = grpcDocInfo.getResultType();
			if (!columnsMap.containsKey(paramType)) {
				columnsMap.put(paramType, this.findColumnInfo(paramType));
			}
			if (!columnsMap.containsKey(resultType)) {
				columnsMap.put(resultType, this.findColumnInfo(resultType));
			}
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
			Map<String, Object> paramMap = JSON.parseObject(params, new TypeReference<HashMap<String, Object>>() {});
			executeResult = this.executeFunction(grpcDocInfoList.get(0), paramMap);
		}
		return executeResult;
	}
	
	private String executeFunction(GrpcDocInfo grpcDocInfo, Map<String, Object> paramMap) throws Exception {
		Class<?> aClass = Class.forName(grpcDocInfo.getParamType());
		Method getMoney = aClass.getMethod("newBuilder");
		Object newBuilder = getMoney.invoke(aClass);
		
		List<MethodParam> setterFunction = this.getSetterFunction(newBuilder.getClass());
		for (MethodParam methodParam : setterFunction) {
			if (!Const.BASE_TYPE.contains(methodParam.getType())) {
				Method setName = newBuilder.getClass().getMethod(methodParam.getSetterName(), Class.forName(methodParam.getType()));
				Object paramObj = paramMap.get(methodParam.getName());
				if (paramObj != null) {
					newBuilder = setName.invoke(newBuilder, paramObj);
				}
			}
		}
		
		Method build = newBuilder.getClass().getMethod("build");
		Object request = build.invoke(newBuilder);
		
		Class<?> serviceClass = Class.forName(grpcDocInfo.getService());
		String serviceSuperName = serviceClass.getSuperclass().getName();
		String superClassName = serviceSuperName.substring(0, serviceSuperName.indexOf("$"));
		
		Class<?> superClass = Class.forName(superClassName);
		Method newBlockingStubMethod = superClass.getMethod("newBlockingStub", Channel.class);
		Object blockingStub = newBlockingStubMethod.invoke(null, grpcChannelFactory.createChannel());
		
		Method sayHello = blockingStub.getClass().getMethod(grpcDocInfo.getMethod(), Class.forName(grpcDocInfo.getParamType()));
		Object response = sayHello.invoke(blockingStub, request);
		
//		Method messageMethod = response.getClass().getMethod("getMessage");
//		Object resultStr = messageMethod.invoke(response);
//		return resultStr.toString();
		
//		return JSON.toJSONString(response);
		return response.toString();
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
			if (!Const.BASE_TYPE.contains(param.getType())) {
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
