package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;

import java.util.Date;

/**
 * wiki空间信息
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
public class WikiSpaceVo {
	
	/**
	 * 主键自增ID
	 */
	private Long id;
	/**
	 * 空间名
	 */
	private String name;
	/**
	 * 空间类型 1=公开 2=个人
	 */
	private Integer type;
	/**
	 * 描述
	 */
	private String spaceExplain;
	/**
	 * 目录延迟加载 0=否 1=是
	 */
	private Integer treeLazyLoad;
	/**
	 * 是否是开放文档 0=否 1=是
	 */
	private Integer openDoc;
	/**
	 * 唯一UUID
	 */
	private String uuid;
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
	 * 创建时间
	 */
	private Integer favorite;
	
	public WikiSpaceVo(WikiSpace space) {
		this.id = space.getId();
		this.name = space.getName();
		this.type = space.getType();
		this.spaceExplain = space.getSpaceExplain();
		this.treeLazyLoad = space.getTreeLazyLoad();
		this.openDoc = space.getOpenDoc();
		this.uuid = space.getUuid();
		this.createUserId = space.getCreateUserId();
		this.createUserName = space.getCreateUserName();
		this.createTime = space.getCreateTime();
	}
	
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
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getSpaceExplain() {
		return spaceExplain;
	}
	
	public void setSpaceExplain(String spaceExplain) {
		this.spaceExplain = spaceExplain;
	}
	
	public Integer getTreeLazyLoad() {
		return treeLazyLoad;
	}
	
	public void setTreeLazyLoad(Integer treeLazyLoad) {
		this.treeLazyLoad = treeLazyLoad;
	}
	
	public Integer getOpenDoc() {
		return openDoc;
	}
	
	public void setOpenDoc(Integer openDoc) {
		this.openDoc = openDoc;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
	public Integer getFavorite() {
		return favorite;
	}
	
	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}
}
