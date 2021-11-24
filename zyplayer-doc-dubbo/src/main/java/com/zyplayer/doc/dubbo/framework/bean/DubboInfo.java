package com.zyplayer.doc.dubbo.framework.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * dubbo信息
 *
 * @author 暮光：城中城
 * @since 2019年1月10日
 **/
public class DubboInfo {
	@JSONField(name = "interface")
	private String interfaceX;
	private List<DubboNodeInfo> nodeList;
	
	public static class DubboNodeInfo {
		private Integer port;
		private String ip;
		@JSONField(name = "interface")
		private String interfaceX;
		private String[] methods;
		private String application;
		private String version;
		private String group;
		
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
		
		public String getVersion() {
			return version;
		}
		
		public void setVersion(String version) {
			this.version = version;
		}
		
		public String getGroup() {
			return group;
		}
		
		public void setGroup(String group) {
			this.group = group;
		}
	}
	
	public List<DubboNodeInfo> getNodeList() {
		return nodeList;
	}
	
	public void setNodeList(List<DubboNodeInfo> nodeList) {
		this.nodeList = nodeList;
	}
	
	public String getInterfaceX() {
		return interfaceX;
	}
	
	public void setInterfaceX(String interfaceX) {
		this.interfaceX = interfaceX;
	}
	
}
