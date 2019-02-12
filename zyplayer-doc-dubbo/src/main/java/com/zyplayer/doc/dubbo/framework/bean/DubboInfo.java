package com.zyplayer.doc.dubbo.framework.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 暮光：城中城
 * @since 2019年1月10日
 **/
public class DubboInfo {
	private Integer port;
	private String ip;
	@JSONField(name = "interface")
	private String interfaceX;
	private String[] methods;
	private String application;
	
	public Integer getPort() {
		return port;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getInterfaceX() {
		return interfaceX;
	}
	
	public void setInterfaceX(String interfaceX) {
		this.interfaceX = interfaceX;
	}
	
	public String[] getMethods() {
		return methods;
	}
	
	public void setMethods(String[] methods) {
		this.methods = methods;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
}
