package com.zyplayer.doc.data.repository.manage.vo;

import java.io.Serializable;
import java.util.List;

/**
 * api自建文档信息
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public class ApiCustomVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 节点ID
	 */
	private Long nodeId;
	
	/**
	 * 文件夹名称
	 */
	private String name;
	
	/**
	 * 文件夹说明
	 */
	private String desc;
	
	/**
	 * 子目录列表
	 */
	private List<ApiCustomVo> children;
	
	/**
	 * 接口列表
	 */
	private List<ApiCustomDocVo> apis;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public List<ApiCustomVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<ApiCustomVo> children) {
		this.children = children;
	}
	
	public List<ApiCustomDocVo> getApis() {
		return apis;
	}
	
	public void setApis(List<ApiCustomDocVo> apis) {
		this.apis = apis;
	}
	
	public Long getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
}
