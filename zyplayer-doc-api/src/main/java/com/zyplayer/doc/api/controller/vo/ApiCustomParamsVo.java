package com.zyplayer.doc.api.controller.vo;


import lombok.Data;

@Data
public class ApiCustomParamsVo {

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
}
