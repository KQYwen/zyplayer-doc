package com.zyplayer.doc.api.controller.param;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 代理请求参数
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
public class ProxyRequestParam {
	private String url;
	private String host;
	private String method;
	private String contentType;
	private String headerParam;
	private String cookieParam;
	private String formParam;
	private String formEncodeParam;
	private String bodyParam;
	
	public List<ParamData> getHeaderParamData() {
		return JSON.parseArray(headerParam, ParamData.class);
	}
	
	public List<ParamData> getCookieParamData() {
		return JSON.parseArray(cookieParam, ParamData.class);
	}
	
	public List<ParamData> getFormParamData() {
		return JSON.parseArray(formParam, ParamData.class);
	}
	
	public List<ParamData> getFormEncodeParamData() {
		return JSON.parseArray(formEncodeParam, ParamData.class);
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getHeaderParam() {
		return headerParam;
	}
	
	public void setHeaderParam(String headerParam) {
		this.headerParam = headerParam;
	}
	
	public String getCookieParam() {
		return cookieParam;
	}
	
	public void setCookieParam(String cookieParam) {
		this.cookieParam = cookieParam;
	}
	
	public String getFormParam() {
		return formParam;
	}
	
	public void setFormParam(String formParam) {
		this.formParam = formParam;
	}
	
	public String getFormEncodeParam() {
		return formEncodeParam;
	}
	
	public void setFormEncodeParam(String formEncodeParam) {
		this.formEncodeParam = formEncodeParam;
	}
	
	public String getBodyParam() {
		return bodyParam;
	}
	
	public void setBodyParam(String bodyParam) {
		this.bodyParam = bodyParam;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
}
