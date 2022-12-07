package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiPage;

import java.util.Date;
import java.util.List;

/**
 * wiki页面信息
 *
 * @author 暮光：城中城
 * @since 2019-03-11
 */
public class WikiPageVo {
	
	private Long id;
	
	/**
	 * 空间ID
	 */
	private Long spaceId;
	
	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * 父ID
	 */
	private Long parentId;
	
	/**
	 * 节点类型 0=有子节点 1=终节点
	 */
	private Integer nodeType;
	
	/**
	 * 赞的数量
	 */
	private Integer zanNum;
	
	/**
	 * 编辑类型 0=可编辑 1=不允许编辑
	 */
	private Integer editType;
	
	/**
	 * 是否收藏 0=否 1=是
	 */
	private Integer favorite;
	
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
	 * 修改人ID
	 */
	private Long updateUserId;
	
	/**
	 * 修改人名字
	 */
	private String updateUserName;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	/**
	 * 0=有效 1=删除
	 */
	private Integer delFlag;
	
	/**
	 * 阅读数
	 */
	private Integer viewNum;
	
	/**
	 * 顺序
	 */
	private Integer seqNo;
	
	/**
	 * 编辑框类型 1=HTML 2=Markdown
	 */
	private Integer editorType;
	
	private String path;
	private List<WikiPageVo> children;
	
	public WikiPageVo(WikiPage wikiPage) {
		this.id = wikiPage.getId();
		this.spaceId = wikiPage.getSpaceId();
		this.name = wikiPage.getName();
		this.parentId = wikiPage.getParentId();
		this.nodeType = wikiPage.getNodeType();
		this.zanNum = wikiPage.getZanNum();
		this.editType = wikiPage.getEditType();
		this.createUserId = wikiPage.getCreateUserId();
		this.createUserName = wikiPage.getCreateUserName();
		this.createTime = wikiPage.getCreateTime();
		this.updateUserId = wikiPage.getUpdateUserId();
		this.updateUserName = wikiPage.getUpdateUserName();
		this.updateTime = wikiPage.getUpdateTime();
		this.delFlag = wikiPage.getDelFlag();
		this.viewNum = wikiPage.getViewNum();
		this.seqNo = wikiPage.getSeqNo();
		this.editorType = wikiPage.getEditorType();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSpaceId() {
		return spaceId;
	}
	
	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
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
	
	public Integer getZanNum() {
		return zanNum;
	}
	
	public void setZanNum(Integer zanNum) {
		this.zanNum = zanNum;
	}
	
	public Integer getEditType() {
		return editType;
	}
	
	public void setEditType(Integer editType) {
		this.editType = editType;
	}
	
	public Integer getFavorite() {
		return favorite;
	}
	
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
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
	
	public Long getUpdateUserId() {
		return updateUserId;
	}
	
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getUpdateUserName() {
		return updateUserName;
	}
	
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getDelFlag() {
		return delFlag;
	}
	
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	public Integer getViewNum() {
		return viewNum;
	}
	
	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}
	
	public Integer getSeqNo() {
		return seqNo;
	}
	
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
	public Integer getEditorType() {
		return editorType;
	}
	
	public void setEditorType(Integer editorType) {
		this.editorType = editorType;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public List<WikiPageVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<WikiPageVo> children) {
		this.children = children;
	}
}
