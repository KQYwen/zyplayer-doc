package com.zyplayer.doc.grpc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.nxest.grpc.client.GrpcChannelFactory;
import com.nxest.grpc.server.GrpcService;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.grpc.controller.po.ColumnInfo;
import com.zyplayer.doc.grpc.controller.po.GrpcDocInfo;
import com.zyplayer.doc.grpc.controller.po.GrpcServiceAndColumn;
import com.zyplayer.doc.grpc.controller.po.MethodParam;
import com.zyplayer.doc.grpc.framework.config.DocGrpcContext;
import com.zyplayer.doc.grpc.framework.config.DocGrpcService;
import com.zyplayer.doc.grpc.framework.config.SpringContextUtil;
import com.zyplayer.doc.grpc.framework.consts.Const;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.MetadataUtils;
import io.netty.handler.ssl.SslContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * grpc文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@RestController
@RequestMapping("/zyplayer-doc-grpc")
public class GrpcDocController {
	
	@Autowired(required = false)
	DocGrpcContext docGrpcContext;
	
	private static Map<String, ColumnInfo> allColumnsMap = new HashMap<>();
	private static Map<String, Object> allBlockingStubMap = new HashMap<>();
	
	/**
	 * 查找所有文档
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	@RequestMapping("/service")
	public DocResponseJson service() {
		List<Object> grpcServiceList = SpringContextUtil.getBeanWithAnnotation(DocGrpcService.class);
		if (grpcServiceList == null || grpcServiceList.size() <= 0) {
			return DocResponseJson.ok();
		}
		List<GrpcDocInfo> grpcDocInfoList = new LinkedList<>();
		for (Object service : grpcServiceList) {
			List<GrpcDocInfo> infoList = this.getServiceInfoByJar(service.getClass());
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
	
	/**
	 * 执行grpc请求
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	@RequestMapping("/execute")
	public DocResponseJson execute(String docService, String params) throws Exception {
		List<GrpcDocInfo> grpcDocInfoList = this.getServiceInfoByJar(Class.forName(docService));
		JSONObject executeResult = null;
		if (grpcDocInfoList != null && grpcDocInfoList.size() > 0) {
			JSONObject paramMap = JSON.parseObject(params);
			try {
				executeResult = this.executeFunction(grpcDocInfoList.get(0), paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return DocResponseJson.warn("执行方法失败");
			}
		}
		return DocResponseJson.ok(executeResult);
	}
	
	/**
	 * 设置字段信息到map
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
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
	
	/**
	 * 执行grpc方法
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private JSONObject executeFunction(GrpcDocInfo grpcDocInfo, JSONObject paramMap) throws Exception {
		Object newBuilder = this.createParamBuilder(grpcDocInfo.getParamType(), paramMap);
		if (newBuilder == null) {
			throw new ConfirmException("参数组装失败");
		}
		if (docGrpcContext == null) {
			throw new ConfirmException("注入grpc服务失败");
		}
		// 创建参数对象
		Method build = newBuilder.getClass().getMethod("build");
		Object request = build.invoke(newBuilder);
		System.out.println(request.toString());
		// 为创建过则创建
		Object blockingStub = allBlockingStubMap.get(grpcDocInfo.getService());
		if (blockingStub == null) {
			// 找到父类
//			Class<?> serviceClass = Class.forName(grpcDocInfo.getService());
//			String serviceSuperName = serviceClass.getSuperclass().getName();
			String serviceSuperName = grpcDocInfo.getService();
			int index = serviceSuperName.lastIndexOf(".");
			serviceSuperName = serviceSuperName.substring(0, index) + "$" + serviceSuperName.substring(index + 1);
			String superClassName = serviceSuperName.substring(0, serviceSuperName.indexOf("$"));
			// 注册
			Class<?> superClass = Class.forName(superClassName);
			Method newBlockingStubMethod = superClass.getMethod("newBlockingStub", Channel.class);
			SslContext sslContext = docGrpcContext.getSslContext();
			ManagedChannel channel = NettyChannelBuilder.forAddress(docGrpcContext.getHost(), docGrpcContext.getPort())
					.sslContext(sslContext)
					.build();
			blockingStub = newBlockingStubMethod.invoke(null, channel);
			Metadata metadata = docGrpcContext.getMetadata();
			if (metadata != null) {
				blockingStub = MetadataUtils.attachHeaders((AbstractStub) blockingStub, metadata);
			}
			allBlockingStubMap.put(grpcDocInfo.getService(), blockingStub);
		}
		Method sayHello = blockingStub.getClass().getMethod(grpcDocInfo.getMethod(), Class.forName(grpcDocInfo.getParamType()));
		// 执行请求
		Object response = sayHello.invoke(blockingStub, request);
		List<ColumnInfo> columnInfos = this.findClassColumns(response.getClass());
		return this.protobufToJson(response, columnInfos);
	}
	
	/**
	 * grpc对象转json字符串
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private JSONObject protobufToJson(Object response, List<ColumnInfo> columnInfos) throws Exception {
		JSONObject jsonObject = new JSONObject();
		for (ColumnInfo columnInfo : columnInfos) {
			Method getMethod = response.getClass().getMethod("get" + this.toUpperCaseFirstOne(columnInfo.getName()));
			Object paramValue = getMethod.invoke(response);
			if (columnInfo.getParam() != null && columnInfo.getParam().size() > 0) {
				JSONObject jsonObjectSub = this.protobufToJson(paramValue, columnInfo.getParam());
				jsonObject.put(columnInfo.getName(), jsonObjectSub);
			} else {
				if ("com.google.protobuf.ByteString".equals(columnInfo.getType())) {
					ByteString byteString = (ByteString) paramValue;
					jsonObject.put(columnInfo.getName(), byteString.toStringUtf8());
				} else {
					jsonObject.put(columnInfo.getName(), paramValue);
				}
			}
		}
		return jsonObject;
	}
	
	/**
	 * 创建参数的builder对象
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
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
					Object paramObjTemp;
					// 特殊类型参数处理
					if ("com.google.protobuf.ByteString".equals(paramTemp.getType())) {
						String stringValue = paramMap.getString(paramTemp.getName());
						paramObjTemp = ByteString.copyFrom(stringValue, "UTF-8");
					} else {
						paramObjTemp = paramMap.getObject(paramTemp.getName(), baseTypeClass);
					}
					// 不为空则设置参数值
					if (paramObjTemp != null) {
						newBuilder = setNameSub.invoke(newBuilder, paramObjTemp);
					}
				} else {
					Class<?> typeClassSub = Class.forName(paramTemp.getType());
					Object newBuilderSub = this.createParamBuilder(paramTemp.getType(), paramMap.getJSONObject(paramTemp.getName()));
					if (newBuilderSub == null) {
						return null;
					}
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
	
	/**
	 * 首字母小写
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private String toLowerCaseFirstOne(String str) {
		if (Character.isLowerCase(str.charAt(0))) {
			return str;
		} else {
			return Character.toLowerCase(str.charAt(0)) + str.substring(1);
		}
	}
	
	/**
	 * 首字母大写
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private String toUpperCaseFirstOne(String str) {
		if (Character.isUpperCase(str.charAt(0))) {
			return str;
		} else {
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		}
	}
	
	/**
	 * 找到所有的setter方法
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private List<MethodParam> getSetterFunction(Class<?> clazz) {
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
		//System.out.println(nameSb);
		return result;
	}
	
	/**
	 * 找到所有的字段信息
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
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
	
	/**
	 * 找到所有的字段信息
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private List<ColumnInfo> findClassColumns(Class<?> clazz) throws Exception {
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
	
	/**
	 * 找到服务，组装方法、参数和返回值等
	 *
	 * @author 暮光：城中城
	 * @since 2019年3月31日
	 */
	private List<GrpcDocInfo> getServiceInfoByJar(Class<?> clazz) {
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
				DocGrpcService annotation = clazz.getAnnotation(DocGrpcService.class);
				if (annotation != null) {
					grpcDocInfo.setService(annotation.service());
				} else {
					grpcDocInfo.setService(clazz.getName());
				}
				grpcDocInfo.setDocService(clazz.getName());
				grpcDocInfo.setMethod(methodName);
				grpcDocInfo.setParamType(paramType);
				grpcDocInfo.setResultType(resultType);
				providerList.add(grpcDocInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return providerList;
	}
	
}
