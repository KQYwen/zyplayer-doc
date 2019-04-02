package com.zyplayer.doc.grpc.controller.po;

import java.util.List;

/**
 * grpc字段信息
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
public class ColumnInfo {
	private String name;
	private String type;
	private String desc;
	private List<ColumnInfo> param;
	
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
	
	public List<ColumnInfo> getParam() {
		return param;
	}
	
	public void setParam(List<ColumnInfo> param) {
		this.param = param;
	}
}
