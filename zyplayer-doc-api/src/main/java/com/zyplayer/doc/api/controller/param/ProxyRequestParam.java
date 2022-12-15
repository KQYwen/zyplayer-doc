package com.zyplayer.doc.api.controller.param;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

/**
 * 代理请求参数
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
@Data
public class ProxyRequestParam {
	private Long docId;
	private Long nodeId;
	private String url;
	private String host;
	private String method;
	private String contentType;
	private String headerParam;
	private String cookieParam;
	private String formParam;
	private String formEncodeParam;
	private String bodyParam;
	private String apiName;

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

}
