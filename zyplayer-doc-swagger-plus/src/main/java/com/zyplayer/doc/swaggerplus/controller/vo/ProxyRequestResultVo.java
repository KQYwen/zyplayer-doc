
package com.zyplayer.doc.swaggerplus.controller.vo;

import java.util.List;

public class ProxyRequestResultVo {
	private List<HttpCookieVo> cookies;
	private List<HttpHeaderVo> headers;
	private Integer status;
	private Long useTime;
	private Integer contentLength;
	private String data;
	private String errorMsg;
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getUseTime() {
		return useTime;
	}
	
	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public List<HttpCookieVo> getCookies() {
		return cookies;
	}
	
	public void setCookies(List<HttpCookieVo> cookies) {
		this.cookies = cookies;
	}
	
	public List<HttpHeaderVo> getHeaders() {
		return headers;
	}
	
	public void setHeaders(List<HttpHeaderVo> headers) {
		this.headers = headers;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public Integer getContentLength() {
		return contentLength;
	}
	
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}
}
