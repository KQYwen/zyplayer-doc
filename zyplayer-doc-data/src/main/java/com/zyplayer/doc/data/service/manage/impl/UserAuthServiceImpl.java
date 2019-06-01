package com.zyplayer.doc.data.service.manage.impl;

import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.mapper.UserAuthMapper;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-05-31
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

}
