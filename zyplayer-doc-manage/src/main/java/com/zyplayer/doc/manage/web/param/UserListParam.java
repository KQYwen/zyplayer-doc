package com.zyplayer.doc.manage.web.param;

import lombok.*;

/**
 * 域账号用户列表查询参数
 *
 * @author 暮光：城中城
 * @since 2021年8月2日
 */
@Data
public class UserListParam {
	private Integer type;
	private String keyword;
	private Integer pageNum;
	private Integer pageSize;

}
