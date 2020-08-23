package com.zyplayer.doc.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;

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
	@ApiModelProperty(value = "总数")
	private Long total;
	@ApiModelProperty(value = "当前页数")
	private Integer pageNum;
	@ApiModelProperty(value = "每页条数")
	private Integer pageSize;
	@ApiModelProperty(value = "总页数")
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
			if (data instanceof PageInfo) {
				PageInfo<?> pageInfo = (PageInfo<?>) data;
				this.data = pageInfo.getList();
				this.total = pageInfo.getTotal();
				this.pageNum = pageInfo.getPageNum();
				this.pageSize = pageInfo.getPageSize();
				this.totalPage = pageInfo.getPages();
			} else if (data instanceof IPage) {
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
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> warn(String errMsg) {
		return new DocResponseJson<T>(300, errMsg);
	}
	
	/**
	 * 错误
	 *
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> error(String errMsg) {
		return new DocResponseJson<T>(500, errMsg);
	}
	
	/**
	 * 失败
	 *
	 * @author 暮光：城中城
	 * @since 2018年8月7日
	 * @return
	 */
	public static <T> DocResponseJson<T> failure(int errCode, String errMsg) {
		return new DocResponseJson<T>(errCode, errMsg);
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
	
	public boolean isOk() {
		return Objects.equals(this.errCode, 200);
	}
	
	@Override
	public String toString() {
		return "DefaultResponseJson [errCode=" + errCode + ", errMsg=" + errMsg + ", data=" + data + "]";
	}

}
