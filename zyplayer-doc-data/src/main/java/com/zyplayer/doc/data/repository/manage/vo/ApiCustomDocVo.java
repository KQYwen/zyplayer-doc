package com.zyplayer.doc.data.repository.manage.vo;

import java.io.Serializable;
import java.util.List;

/**
 * api自建文档信息
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public class ApiCustomDocVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 节点类型 0=目录 1=接口
	 */
	private Integer nodeType;
	
	/**
	 * 接口ID
	 */
	private Long nodeId;
	
	/**
	 * 接口名称
	 */
	private String nodeName;
	
	/**
	 * 接口说明
	 */
	private String nodeDesc;
	
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
	
	/**
	 * 子目录列表
	 */
	private List<ApiCustomDocVo> children;
	
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
	
	public Long getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public List<ApiCustomDocVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<ApiCustomDocVo> children) {
		this.children = children;
	}
	
	public String getNodeDesc() {
		return nodeDesc;
	}
	
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	
	public Integer getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
}
