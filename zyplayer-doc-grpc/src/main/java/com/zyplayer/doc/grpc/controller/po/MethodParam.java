package com.zyplayer.doc.grpc.controller.po;

/**
 * grpc方法信息
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
public class MethodParam {
	private String name;
	private String type;
	private String setterName;
	
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
	
	public String getSetterName() {
		return setterName;
	}
	
	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}
}
