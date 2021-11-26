package com.zyplayer.doc.api.controller.vo;

/**
 * cookie返回值对象
 *
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
public class HttpCookieVo {
	private String name;
	private String value;
	
	public HttpCookieVo() {
	
	}
	
	public HttpCookieVo(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
