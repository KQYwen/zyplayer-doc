package com.zyplayer.doc.data.repository.manage.vo;

import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import lombok.*;

import java.io.Serializable;

/**
 * api文档地址Vo
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiDocVo extends ApiDoc implements Serializable {

	/**
	 * 权限类型
	 */
	private Integer authType;

}
