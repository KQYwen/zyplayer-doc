package com.zyplayer.doc.data.repository.manage.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * api文档地址Vo
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public class ApiDocVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 权限类型
	 */
	private Integer authType;
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 文档名称
	 */
	private String name;
	
	/**
	 * 文档类型 1=swagger url 2=swagger json 3=openapi url 4=openapi json 5=自建API分组
	 */
	private Integer docType;
	
	/**
	 * 文档URL地址
	 */
	private String docUrl;
	
	/**
	 * 文档json内容
	 */
	private String jsonContent;
	
	/**
	 * 重写的域名
	 */
	private String rewriteDomain;
	
	/**
	 * 是否开放访问 0=否 1=是
	 */
	private Integer openVisit;
	
	/**
	 * 状态 1=启用 2=禁用
	 */
	private Integer docStatus;
	
	/**
	 * 开放文档UUID
	 */
	private String shareUuid;
	
	/**
	 * 开放文档使用说明
	 */
	private String shareInstruction;
	
	/**
	 * 创建人ID
	 */
	private Long createUserId;
	
	/**
	 * 创建人名字
	 */
	private String createUserName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 是否有效 0=无效 1=有效
	 */
	private Integer yn;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getDocType() {
		return docType;
	}
	
	public void setDocType(Integer docType) {
		this.docType = docType;
	}
	
	public String getDocUrl() {
		return docUrl;
	}
	
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}
	
	public String getJsonContent() {
		return jsonContent;
	}
	
	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}
	
	public String getRewriteDomain() {
		return rewriteDomain;
	}
	
	public void setRewriteDomain(String rewriteDomain) {
		this.rewriteDomain = rewriteDomain;
	}
	
	public Integer getOpenVisit() {
		return openVisit;
	}
	
	public void setOpenVisit(Integer openVisit) {
		this.openVisit = openVisit;
	}
	
	public Integer getDocStatus() {
		return docStatus;
	}
	
	public void setDocStatus(Integer docStatus) {
		this.docStatus = docStatus;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getCreateUserName() {
		return createUserName;
	}
	
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getYn() {
		return yn;
	}
	
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
	@Override
	public String toString() {
		return "ApiDoc{" +
				"id=" + id +
				", name=" + name +
				", docType=" + docType +
				", docUrl=" + docUrl +
				", jsonContent=" + jsonContent +
				", rewriteDomain=" + rewriteDomain +
				", openVisit=" + openVisit +
				", docStatus=" + docStatus +
				", createUserId=" + createUserId +
				", createUserName=" + createUserName +
				", createTime=" + createTime +
				", yn=" + yn +
				"}";
	}
	
	public String getShareUuid() {
		return shareUuid;
	}
	
	public void setShareUuid(String shareUuid) {
		this.shareUuid = shareUuid;
	}
	
	public String getShareInstruction() {
		return shareInstruction;
	}
	
	public void setShareInstruction(String shareInstruction) {
		this.shareInstruction = shareInstruction;
	}
	
	public Integer getAuthType() {
		return authType;
	}
	
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
}
