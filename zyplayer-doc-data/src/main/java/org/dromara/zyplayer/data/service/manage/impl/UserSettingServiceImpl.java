package org.dromara.zyplayer.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.UserSetting;
import org.dromara.zyplayer.data.repository.manage.mapper.UserSettingMapper;
import org.dromara.zyplayer.data.repository.support.consts.UserSettingConst;
import org.dromara.zyplayer.data.service.manage.UserSettingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户设置表 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
@Service
public class UserSettingServiceImpl extends ServiceImpl<UserSettingMapper, UserSetting> implements UserSettingService {
	
	@Override
	public String getMySettingValue(String name) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		LambdaQueryWrapper<UserSetting> settingWrapper = new LambdaQueryWrapper<>();
		settingWrapper.eq(UserSetting::getUserId, currentUser.getUserId());
		settingWrapper.eq(UserSetting::getName, UserSettingConst.WIKI_ONLY_SHOW_FAVORITE);
		settingWrapper.eq(UserSetting::getDelFlag, 0);
		UserSetting userSetting = this.getOne(settingWrapper);
		if (userSetting == null) return null;
		return userSetting.getValue();
	}
}
