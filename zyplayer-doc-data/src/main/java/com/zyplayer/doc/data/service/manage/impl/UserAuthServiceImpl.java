package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.mapper.UserAuthMapper;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
	
	@Resource
	AuthInfoService authInfoService;
	
	@Override
	public Set<String> getUserAuthSet(Long id) {
		QueryWrapper<UserAuth> authWrapper = new QueryWrapper<>();
		authWrapper.eq("user_id", id).eq("del_flag", "0");
		List<UserAuth> userAuthList = this.list(authWrapper);
		Set<String> userAuthSet = Collections.emptySet();
		if (userAuthList != null && userAuthList.size() > 0) {
			List<Long> authIdList = userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toList());
			Collection<AuthInfo> authInfoList = authInfoService.listByIds(authIdList);
			Map<Long, String> authNameMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getId, AuthInfo::getAuthName));
			userAuthSet = userAuthList.stream().map(val -> {
				String authName = Optional.ofNullable(authNameMap.get(val.getAuthId())).orElse("");
				return authName + Optional.ofNullable(val.getAuthCustomSuffix()).orElse("");
			}).collect(Collectors.toSet());
		}
		return userAuthSet;
	}
}
