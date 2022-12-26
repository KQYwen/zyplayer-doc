package com.zyplayer.doc.manage.web.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户授权信息
 *
 * @author 暮光：城中城
 * @since 2018-12-15
 */
@Data
public class AuthInfoVo implements Serializable {

	/**
	 * 主键自增ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 是否选中
	 */
	private Integer checked;

	/**
	 * 权限名
	 */
	private String authName;

	/**
	 * 权限说明
	 */
	private String authDesc;

	/**
	 * 是否可编辑 0=否 1=是
	 */
	private Integer canEdit;

	/**
	 * 创建人
	 */
	private Long createUid;

	/**
	 * 创建时间
	 */
	private Date creationTime;

	public AuthInfoVo(AuthInfo authInfo) {
		this.id = authInfo.getId();
		this.authName = authInfo.getAuthName();
		this.authDesc = authInfo.getAuthDesc();
		this.canEdit = authInfo.getCanEdit();
		this.createUid = authInfo.getCreateUid();
		this.creationTime = authInfo.getCreationTime();
	}

}
