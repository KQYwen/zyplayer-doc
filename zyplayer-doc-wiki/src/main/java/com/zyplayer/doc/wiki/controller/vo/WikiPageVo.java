package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * wiki页面信息
 *
 * @author 暮光：城中城
 * @since 2019-03-11
 */
@Data
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

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 子节点
	 */
	private List<WikiPageVo> children;

	/**
	 * 模板类型 0：个人模板 1：共享模板
	 */
	private Integer shareStatus;

	/**
	 * 模板标签
	 */
	private String tags;


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

	public WikiPageVo(WikiPageTemplateInfoVo wikiPageTemplate) {
		this.id = wikiPageTemplate.getId();
		this.spaceId = wikiPageTemplate.getSpaceId();
		this.name = wikiPageTemplate.getName();
		this.parentId = wikiPageTemplate.getParentId();
		this.nodeType = wikiPageTemplate.getNodeType();
		this.zanNum = wikiPageTemplate.getZanNum();
		this.editType = wikiPageTemplate.getEditType();
		this.createUserId = wikiPageTemplate.getCreateUserId();
		this.createUserName = wikiPageTemplate.getCreateUserName();
		this.createTime = wikiPageTemplate.getCreateTime();
		this.updateUserId = wikiPageTemplate.getUpdateUserId();
		this.updateUserName = wikiPageTemplate.getUpdateUserName();
		this.updateTime = wikiPageTemplate.getUpdateTime();
		this.delFlag = wikiPageTemplate.getDelFlag();
		this.viewNum = wikiPageTemplate.getViewNum();
		this.seqNo = wikiPageTemplate.getSeqNo();
		this.editorType = wikiPageTemplate.getEditorType();
		this.tags = wikiPageTemplate.getTags();
		this.shareStatus = wikiPageTemplate.getShareStatus();
	}


}
