package com.zyplayer.doc.data.repository.manage.vo;

import java.io.Serializable;

/**
 * api自建文档信息
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public class ApiCustomDocVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 接口名称
	 */
	private String apiName;
	
	/**
	 * 请求方式：get、head、post、put、patch、delete、options、trace
	 */
	private String method;
	
	/**
	 * 接口url
	 */
	private String apiUrl;
	
	/**
	 * form参数
	 */
	private String formData;
	
	/**
	 * body参数
	 */
	private String bodyData;
	
	/**
	 * header参数
	 */
	private String headerData;
	
	/**
	 * cookie参数
	 */
	private String cookieData;
	
	public String getApiName() {
		return apiName;
	}
	
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	public String getApiUrl() {
		return apiUrl;
	}
	
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getFormData() {
		return formData;
	}
	
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public String getBodyData() {
		return bodyData;
	}
	
	public void setBodyData(String bodyData) {
		this.bodyData = bodyData;
	}
	
	public String getHeaderData() {
		return headerData;
	}
	
	public void setHeaderData(String headerData) {
		this.headerData = headerData;
	}
	
	public String getCookieData() {
		return cookieData;
	}
	
	public void setCookieData(String cookieData) {
		this.cookieData = cookieData;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
}
