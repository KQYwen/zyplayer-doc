package com.zyplayer.doc.data.config.security;

import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-05-31
 */
@Data
@NoArgsConstructor
public class UserAuthInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserAuthInfo(UserAuth userAuth) {
		this.authId = userAuth.getAuthId();
		this.sysType = userAuth.getSysType();
		this.sysModuleType = userAuth.getSysModuleType();
		this.sysModuleId = userAuth.getSysModuleId();
	}

	/**
	 * 权限ID
	 */
	private Long authId;

	/**
	 * 权限code
	 */
	private String authCode;

	/**
	 * 系统类型，{@link DocSysType}
	 */
	private Integer sysType;

	/**
	 * 系统模块类型，{@link DocSysModuleType}
	 */
	private Integer sysModuleType;

	/**
	 * 系统模块ID
	 */
	private Long sysModuleId;
}
