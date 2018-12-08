package com.zyplayer.doc.swagger.controller.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * request返回值对象
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
public class HttpRequestVo {
	@ApiModelProperty(value = "代理请求返回的cookie")
	private List<HttpCookieVo> cookie;
	@ApiModelProperty(value = "代理请求返回的header")
	private List<HttpHeaderVo> header;
	@ApiModelProperty(value = "代理请求返回的status")
	private Integer status;
	@ApiModelProperty(value = "返回数据")
	private Object data;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<HttpCookieVo> getCookie() {
		return cookie;
	}

	public void setCookie(List<HttpCookieVo> cookie) {
		this.cookie = cookie;
	}

	public List<HttpHeaderVo> getHeader() {
		return header;
	}

	public void setHeader(List<HttpHeaderVo> header) {
		this.header = header;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
