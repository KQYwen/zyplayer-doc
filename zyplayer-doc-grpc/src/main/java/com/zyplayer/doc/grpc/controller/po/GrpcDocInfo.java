package com.zyplayer.doc.grpc.controller.po;

/**
 * 请求参数对象
 *
 * @author 暮光：城中城
 * @since 2019年2月10日
 */
public class GrpcDocInfo {
	private String method;
	private String service;
	private String explain;
	private String result;
	private String paramType;
	private String resultType;
	private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getExplain() {
		return explain;
	}
	
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResultType() {
		return resultType;
	}
	
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	
	public String getParamType() {
		return paramType;
	}
	
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
}
