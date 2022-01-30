package com.zyplayer.doc.api.controller.vo;

public class ApiCustomParamsVo {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 节点ID
	 */
	private Long id;
	
	/**
	 * 父文件夹ID
	 */
	private Long parentId;
	
	/**
	 * 节点类型 0=目录 1=接口
	 */
	private Integer nodeType;
	
	/**
	 * 节点名称
	 */
	private String nodeName;
	
	/**
	 * 节点说明
	 */
	private String nodeDesc;
	
	/**
	 * 节点顺序
	 */
	private Integer seqNo;
	
	/**
	 * api_doc主键ID
	 */
	private Long docId;
	
	/**
	 * 节点ID
	 */
	private Long nodeId;
	
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
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Integer getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getNodeDesc() {
		return nodeDesc;
	}
	
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	
	public Integer getSeqNo() {
		return seqNo;
	}
	
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
	public Long getDocId() {
		return docId;
	}
	
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
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
	
	public Long getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
