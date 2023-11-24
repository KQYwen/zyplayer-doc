package com.zyplayer.doc.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 文档返回数据格式
 *
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
public class DocResponseJson<T> implements ResponseJson<T> {
	private static final SerializeConfig mapping = new SerializeConfig();
	
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	
	private Integer errCode;
	private String errMsg;
	private Object data;
	private Long total;
	private Integer pageNum;
	private Integer pageSize;
	private Integer totalPage;
	
	public DocResponseJson() {
		this.errCode = 200;
	}
	
	public DocResponseJson(Object data) {
		this.setData(data);
		this.errCode = 200;
	}
	
	public DocResponseJson(int errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public DocResponseJson(int errCode, String errMsg, Object data) {
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
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		if (null != data) {
			if (data instanceof IPage) {
				IPage<?> iPage = (IPage<?>) data;
				this.data = iPage.getRecords();
				this.total = iPage.getTotal();
				this.pageNum = (int) iPage.getCurrent();
				this.pageSize = (int) iPage.getSize();
				this.totalPage = (int) iPage.getPages();
			} else {
				this.data = data;
			}
		}
	}
	
	/**
	 * 提示语
	 *
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 */
	public static <T> DocResponseJson<T> warn(String errMsg) {
		return new DocResponseJson<>(300, errMsg);
	}
	
	/**
	 * 错误
	 *
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 */
	public static <T> DocResponseJson<T> error(String errMsg) {
		return new DocResponseJson<>(500, errMsg);
	}
	
	/**
	 * 失败
	 *
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 */
	public static <T> DocResponseJson<T> failure(int errCode, String errMsg) {
		return new DocResponseJson<>(errCode, errMsg);
	}
	
	/**
	 * 成功的返回方法
	 *
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 */
	public static <T> DocResponseJson<T> ok() {
		return new DocResponseJson<>();
	}
	
	/**
	 * 成功的返回方法
	 *
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 */
	public static <T> DocResponseJson<T> ok(Object data) {
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
	
	@JSONField(serialize = false)
	public boolean isOk() {
		return Objects.equals(this.errCode, 200);
	}
	
	@Override
	public String toString() {
		return "DefaultResponseJson [errCode=" + errCode + ", errMsg=" + errMsg + ", data=" + data + "]";
	}
	
}
