
package com.zyplayer.doc.api.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * 代理请求结果
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
@Data
public class ProxyRequestResultVo {
	private List<HttpCookieVo> cookies;
	private List<HttpHeaderVo> headers;
	private Integer status;
	private Long useTime;
	private Integer contentLength;
	private String data;
	private String errorMsg;
}
