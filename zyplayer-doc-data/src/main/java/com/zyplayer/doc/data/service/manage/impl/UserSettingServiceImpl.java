package com.zyplayer.doc.data.service.manage.impl;

import com.zyplayer.doc.data.repository.manage.entity.UserSetting;
import com.zyplayer.doc.data.repository.manage.mapper.UserSettingMapper;
import com.zyplayer.doc.data.service.manage.UserSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
