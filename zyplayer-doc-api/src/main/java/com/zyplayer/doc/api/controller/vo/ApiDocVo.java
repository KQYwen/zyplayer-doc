package com.zyplayer.doc.api.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * api文档地址Vo
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@Data
public class ApiDocVo implements Serializable {

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
}
