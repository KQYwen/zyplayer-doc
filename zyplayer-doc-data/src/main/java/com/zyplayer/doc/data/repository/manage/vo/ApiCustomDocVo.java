package com.zyplayer.doc.data.repository.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * api自建文档信息
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@Data
public class ApiCustomDocVo implements Serializable {

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
}
