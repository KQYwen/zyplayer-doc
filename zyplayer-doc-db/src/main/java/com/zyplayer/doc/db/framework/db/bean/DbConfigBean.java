package com.zyplayer.doc.db.framework.db.bean;

import lombok.Data;

/**
 * 数据库配置信息
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
@Data
public class DbConfigBean {
	private String driverClassName;
	private String url;
	private String username;
	private String password;
}
