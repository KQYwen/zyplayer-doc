package com.zyplayer.doc.dubbo.controller.param;

/**
 * 请求参数对象
 *
 * @author 暮光：城中城
 * @since 2019年2月10日
 */
public class DubboRequestParam {
	private String application;
	private String service;
	private String method;
	private String ip;
	private Integer port;
	private String paramTypes;
	private String params;
	
	public String getParamTypes() {
		return paramTypes;
	}
	
	public void setParamTypes(String paramTypes) {
		this.paramTypes = paramTypes;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getPort() {
		return port;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
}
