package com.zyplayer.doc.data.repository.manage.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * api自建文档信息
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@Data
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

}
