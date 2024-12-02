package org.dromara.zyplayer.data.service.manage;

import org.dromara.zyplayer.data.repository.manage.entity.UserSetting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户设置表 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
public interface UserSettingService extends IService<UserSetting> {
	
	String getMySettingValue(String name);
}
