package com.zyplayer.doc.dubbo.framework.bean;

import java.util.List;

/**
 * 请求参数对象
 *
 * @author 暮光：城中城
 * @since 2019年2月10日
 */
public class DubboResponseInfo {
	private String name;
	private String type;
	private String desc;
	private Object value;
	
	private String interfaceType;
	private DubboResponseInfo keyInfo;
	private List<DubboResponseInfo> params;
	
	public DubboResponseInfo(String type) {
		this.type = type;
	}
	
	public DubboResponseInfo(String name, String type, String desc, Object value) {
		this.name = name;
		this.type = type;
		this.desc = desc;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public List<DubboResponseInfo> getParams() {
		return params;
	}
	
	public void setParams(List<DubboResponseInfo> params) {
		this.params = params;
	}
	
	public DubboResponseInfo getKeyInfo() {
		return keyInfo;
	}
	
	public void setKeyInfo(DubboResponseInfo keyInfo) {
		this.keyInfo = keyInfo;
	}
	
	public String getInterfaceType() {
		return interfaceType;
	}
	
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
}
