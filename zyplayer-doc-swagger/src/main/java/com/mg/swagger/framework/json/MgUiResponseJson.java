package com.mg.swagger.framework.json;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.mg.swagger.controller.vo.HttpCookieVo;
import com.mg.swagger.controller.vo.HttpHeaderVo;

import cn.hutool.http.HttpResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * MgUi返回数据格式
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
public class MgUiResponseJson implements ResponseJson {
	private static SerializeConfig mapping = new SerializeConfig();
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	@ApiModelProperty(value = "状态码")
	private Integer errCode;
	@ApiModelProperty(value = "返回值说明")
	private String errMsg;
	@ApiModelProperty(value = "返回数据")
	private Object data;
	@ApiModelProperty(value = "代理请求返回的cookie")
	private List<HttpCookieVo> cookie;
	@ApiModelProperty(value = "代理请求返回的header")
	private List<HttpHeaderVo> header;
	@ApiModelProperty(value = "代理请求返回的status")
	private Integer status;

	public MgUiResponseJson() {
		this.errCode = 200;
	}

	public MgUiResponseJson(Object data) {
		this.setData(data);
		this.errCode = 200;
	}

	public MgUiResponseJson(int errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public MgUiResponseJson(int errCode, String errMsg, Object data) {
		super();
		this.setData(data);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public MgUiResponseJson(Integer errCode) {
		super();
		this.errCode = errCode;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 提示语
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static MgUiResponseJson warn(String errMsg) {
		return new MgUiResponseJson(300, errMsg);
	}
	
	/**
	 * 错误
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static MgUiResponseJson error(String errMsg) {
		return new MgUiResponseJson(500, errMsg);
	}

	/**
	 * 成功的返回方法
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static MgUiResponseJson ok() {
		return new MgUiResponseJson();
	}

	/**
	 * 成功的返回方法
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static MgUiResponseJson ok(Object data) {
		if (data == null) {
			return MgUiResponseJson.ok();
		}
		MgUiResponseJson responseJson = new MgUiResponseJson();
		if (data instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) data;
			responseJson.setData(response.body());
			responseJson.setStatus(response.getStatus());
			List<HttpCookie> cookies = response.getCookies();
			if (cookies != null && cookies.size() > 0) {
				List<HttpCookieVo> cookie = new ArrayList<>(cookies.size());
				for (HttpCookie httpCookie : cookies) {
					HttpCookieVo vo = new HttpCookieVo();
					vo.setName(httpCookie.getName());
					vo.setValue(httpCookie.getValue());
					cookie.add(vo);
				}
				responseJson.setCookie(cookie);
			}
			Map<String, List<String>> headers = response.headers();
			if (headers != null && headers.size() > 0) {
				List<HttpHeaderVo> header = new ArrayList<>(headers.size());
				for (Entry<String, List<String>> httpHeader : headers.entrySet()) {
					HttpHeaderVo vo = new HttpHeaderVo();
					vo.setName(httpHeader.getKey());
					vo.setValue(String.join(";", httpHeader.getValue()));
					header.add(vo);
				}
				responseJson.setHeader(header);
			}
		} else {
			responseJson.setData(data);
		}
		return responseJson;
	}

	public String toJson() {
		return JSON.toJSONString(this, mapping);
	}
	
	public void send(HttpServletResponse response) {
		try {
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.getWriter().write(toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "DefaultResponseJson [errCode=" + errCode + ", errMsg=" + errMsg + ", data=" + data + "]";
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
