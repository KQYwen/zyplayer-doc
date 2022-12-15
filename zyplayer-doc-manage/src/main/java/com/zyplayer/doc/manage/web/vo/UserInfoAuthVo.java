package com.zyplayer.doc.manage.web.vo;

import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import lombok.*;

/**
 * 用户授权信息
 *
 * @author 暮光：城中城
 * @since 2018-12-05
 */
@Data
public class UserInfoAuthVo {
	private UserInfo userInfo;
	private UserAuthVo userAuth;

}
