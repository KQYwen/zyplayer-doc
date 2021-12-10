package com.zyplayer.doc.data.config.security;

import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;

import java.io.Serializable;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-05-31
 */
public class UserAuthVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public UserAuthVo() {
	
	}
	
	public UserAuthVo(UserAuth userAuth) {
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
	
	public Long getAuthId() {
		return authId;
	}
	
	public void setAuthId(Long authId) {
		this.authId = authId;
	}
	
	public Integer getSysType() {
		return sysType;
	}
	
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}
	
	public Integer getSysModuleType() {
		return sysModuleType;
	}
	
	public void setSysModuleType(Integer sysModuleType) {
		this.sysModuleType = sysModuleType;
	}
	
	public Long getSysModuleId() {
		return sysModuleId;
	}
	
	public void setSysModuleId(Long sysModuleId) {
		this.sysModuleId = sysModuleId;
	}
	
	public String getAuthCode() {
		return authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}
