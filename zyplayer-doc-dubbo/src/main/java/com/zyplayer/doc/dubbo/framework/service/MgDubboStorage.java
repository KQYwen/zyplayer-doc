package com.zyplayer.doc.dubbo.framework.service;

import java.io.Serializable;

/**
 * 数据类型
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public class MgDubboStorage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String value;
	
	public MgDubboStorage() {
	
	}
	
	public MgDubboStorage(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
