package com.zyplayer.doc.data.repository.manage.vo;

import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;

import java.io.Serializable;

/**
 * api文档地址Vo
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public class ApiDocVo extends ApiDoc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 权限类型
	 */
	private Integer authType;
	
	public Integer getAuthType() {
		return authType;
	}
	
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
}
