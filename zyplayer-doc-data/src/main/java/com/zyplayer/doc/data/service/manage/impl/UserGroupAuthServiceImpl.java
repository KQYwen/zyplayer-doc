package com.zyplayer.doc.data.service.manage.impl;

import com.zyplayer.doc.data.repository.manage.entity.UserGroupAuth;
import com.zyplayer.doc.data.repository.manage.mapper.UserGroupAuthMapper;
import com.zyplayer.doc.data.service.manage.UserGroupAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组在各项目内的授权关系 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
@Service
public class UserGroupAuthServiceImpl extends ServiceImpl<UserGroupAuthMapper, UserGroupAuth> implements UserGroupAuthService {

}
