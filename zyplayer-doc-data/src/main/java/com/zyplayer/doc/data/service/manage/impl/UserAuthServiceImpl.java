package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.config.security.UserAuthVo;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.mapper.UserAuthMapper;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import org.apache.commons.collections.CollectionUtils;
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
	public List<UserAuthVo> getUserAuthSet(Long userId) {
		QueryWrapper<UserAuth> authWrapper = new QueryWrapper<>();
		authWrapper.eq("user_id", userId).eq("del_flag", "0");
		List<UserAuth> userAuthList = this.list(authWrapper);
		if (CollectionUtils.isEmpty(userAuthList)) {
			return Collections.emptyList();
		}
		List<Long> authIdList = userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toList());
		Collection<AuthInfo> authInfoList = authInfoService.listByIds(authIdList);
		Map<Long, String> authNameMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getId, AuthInfo::getAuthName));
		// 组装
		List<UserAuthVo> userAuthVoList = userAuthList.stream().map(UserAuthVo::new).collect(Collectors.toList());
		for (UserAuthVo userAuthVo : userAuthVoList) {
			userAuthVo.setAuthCode(authNameMap.get(userAuthVo.getAuthId()));
		}
		return userAuthVoList;
	}
	
	@Override
	public List<UserAuth> getModuleAuthList(Integer sysType, Integer sysModuleType, Long sysModuleId) {
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("sys_type", sysType);
		queryWrapper.eq("sys_module_type", sysModuleType);
		queryWrapper.eq("sys_module_id", sysModuleId);
		queryWrapper.eq("del_flag", 0);
		return this.list(queryWrapper);
	}
	
	@Override
	public List<UserAuth> getUserModuleAuthList(Long userId, Integer sysType, Integer sysModuleType, Long sysModuleId) {
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		queryWrapper.eq("sys_type", sysType);
		queryWrapper.eq("sys_module_type", sysModuleType);
		// 不传时查询所有有权限的文档
		queryWrapper.eq(sysModuleId != null, "sys_module_id", sysModuleId);
		queryWrapper.eq("del_flag", 0);
		return this.list(queryWrapper);
	}
	
	@Override
	public boolean deleteModuleAuth(Integer sysType, Integer sysModuleType, Long sysModuleId) {
		QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("sys_type", sysType);
		updateWrapper.eq("sys_module_type", sysModuleType);
		updateWrapper.eq("sys_module_id", sysModuleId);
		updateWrapper.eq("del_flag", 0);
		return this.remove(updateWrapper);
	}
	
	@Override
	public boolean deleteUserModuleAuth(Long userId, Integer sysType, Integer sysModuleType, Long sysModuleId) {
		QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("user_id", userId);
		updateWrapper.eq("sys_type", sysType);
		updateWrapper.eq("sys_module_type", sysModuleType);
		updateWrapper.eq("sys_module_id", sysModuleId);
		updateWrapper.eq("del_flag", 0);
		return this.remove(updateWrapper);
	}
}
