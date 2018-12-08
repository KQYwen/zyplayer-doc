package com.zyplayer.doc.core.json;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import io.swagger.annotations.ApiModelProperty;

/**
 * 文档返回数据格式
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
public class DocResponseJson<T> implements ResponseJson<T> {
	private static SerializeConfig mapping = new SerializeConfig();
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	@ApiModelProperty(value = "状态码")
	private Integer errCode;
	@ApiModelProperty(value = "返回值说明")
	private String errMsg;
	@ApiModelProperty(value = "返回数据")
	private T data;

	public DocResponseJson() {
		this.errCode = 200;
	}

	public DocResponseJson(T data) {
		this.setData(data);
		this.errCode = 200;
	}

	public DocResponseJson(int errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public DocResponseJson(int errCode, String errMsg, T data) {
		super();
		this.setData(data);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public DocResponseJson(Integer errCode) {
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 提示语
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> warn(String errMsg) {
		return new DocResponseJson<>(300, errMsg);
	}
	
	/**
	 * 错误
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> error(String errMsg) {
		return new DocResponseJson<>(500, errMsg);
	}

	/**
	 * 成功的返回方法
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> ok() {
		return new DocResponseJson<>();
	}

	/**
	 * 成功的返回方法
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> ok(T data) {
		if (data == null) {
			return DocResponseJson.ok();
		}
		DocResponseJson<T> responseJson = new DocResponseJson<>();
		responseJson.setData(data);
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

}
