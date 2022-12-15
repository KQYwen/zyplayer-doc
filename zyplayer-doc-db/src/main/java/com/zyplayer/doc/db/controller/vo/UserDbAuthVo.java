package com.zyplayer.doc.db.controller.vo;

import lombok.Data;

/**
 * 用户数据库授权信息
 *
 * @author 暮光：城中城
 * @since 2019-08-22
 */
@Data
public class UserDbAuthVo {
	private String userName;
	private Long userId;
	private Integer executeAuth;
	private Integer descEditAuth;
	private Integer procEditAuth;
}
