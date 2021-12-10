package com.zyplayer.doc.db.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.db.controller.vo.UserDbAuthVo;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数据库权限控制器
 *
 * @author 暮光：城中城
 * @since 2019年8月18日
 */
@RestController
@AuthMan(DocAuthConst.DB_DATASOURCE_MANAGE)
@RequestMapping("/zyplayer-doc-db/auth")
public class DbDataSourceAuthController {
	private static Logger logger = LoggerFactory.getLogger(DbDataSourceAuthController.class);
	
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAuthService userAuthService;
	@Resource
	AuthInfoService authInfoService;
	
	@PostMapping("/assign")
	public ResponseJson<Object> assign(Long sourceId, String authList) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		List<String> authNameList = Stream.of(DbAuthType.values()).map(DbAuthType::getName).collect(Collectors.toList());
		QueryWrapper<AuthInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("auth_name", authNameList);
		Collection<AuthInfo> authInfoList = authInfoService.list(queryWrapper);
		Map<String, Long> authInfoMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getAuthName, AuthInfo::getId));
		
		// 先删除所有用户的权限
		QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("auth_custom_suffix", DocAuthConst.DB + sourceId);
		updateWrapper.eq("del_flag", 0);
		userAuthService.remove(updateWrapper);
		
		List<UserDbAuthVo> authVoList = JSON.parseArray(authList, UserDbAuthVo.class);
		for (UserDbAuthVo authVo : authVoList) {
			List<UserAuth> userAuthList = new LinkedList<>();
			Integer executeAuth = Optional.ofNullable(authVo.getExecuteAuth()).orElse(0);
			if (executeAuth <= 0) {
				Long authId = authInfoMap.get(DbAuthType.NO_AUTH.getName());
				UserAuth userAuth = this.createUserAuth(sourceId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (executeAuth >= 1) {
				Long authId = authInfoMap.get(DbAuthType.VIEW.getName());
				UserAuth userAuth = this.createUserAuth(sourceId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (executeAuth >= 2) {
				Long authId = authInfoMap.get(DbAuthType.SELECT.getName());
				UserAuth userAuth = this.createUserAuth(sourceId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (executeAuth >= 3) {
				Long authId = authInfoMap.get(DbAuthType.UPDATE.getName());
				UserAuth userAuth = this.createUserAuth(sourceId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getDescEditAuth(), 1)) {
				Long authId = authInfoMap.get(DbAuthType.DESC_EDIT.getName());
				UserAuth userAuth = this.createUserAuth(sourceId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getProcEditAuth(), 1)) {
				Long authId = authInfoMap.get(DbAuthType.PROC_EDIT.getName());
				UserAuth userAuth = this.createUserAuth(sourceId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (userAuthList.size() <= 0) {
				continue;
			}
			// 保存权限，重新登录后可用，后期可以考虑在这里直接修改缓存里的用户权限
			userAuthService.saveBatch(userAuthList);
		}
		return DocResponseJson.ok();
	}
	
	@PostMapping("/list")
	public ResponseJson<Object> list(Long sourceId) {
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("auth_custom_suffix", DocAuthConst.DB + sourceId);
		queryWrapper.eq("del_flag", 0);
		List<UserAuth> authList = userAuthService.list(queryWrapper);
		if (CollectionUtils.isEmpty(authList)) {
			return DocResponseJson.ok();
		}
		// 权限ID对应的权限名
		Collection<AuthInfo> authInfoList = authInfoService.listByIds(authList.stream().map(UserAuth::getAuthId).collect(Collectors.toList()));
		Map<Long, String> authInfoMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getId, AuthInfo::getAuthName));
		// 查询用户信息
		Map<Long, List<UserAuth>> userAuthGroup = authList.stream().collect(Collectors.groupingBy(UserAuth::getUserId));
		Collection<UserInfo> userInfos = userInfoService.listByIds(userAuthGroup.keySet());
		Map<Long, String> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getId, UserInfo::getUserName));
		List<UserDbAuthVo> authVoList = new LinkedList<>();
		// 组装结果集
		userAuthGroup.forEach((key, value) -> {
			Set<String> authNameSet = value.stream().map(auth -> authInfoMap.get(auth.getAuthId())).collect(Collectors.toSet());
			UserDbAuthVo authVo = new UserDbAuthVo();
			if (this.haveAuth(authNameSet, DbAuthType.UPDATE) == 1) {
				authVo.setExecuteAuth(3);
			} else if (this.haveAuth(authNameSet, DbAuthType.SELECT) == 1) {
				authVo.setExecuteAuth(2);
			} else if (this.haveAuth(authNameSet, DbAuthType.VIEW) == 1) {
				authVo.setExecuteAuth(1);
			}
			authVo.setDescEditAuth(this.haveAuth(authNameSet, DbAuthType.DESC_EDIT));
			authVo.setProcEditAuth(this.haveAuth(authNameSet, DbAuthType.PROC_EDIT));
			authVo.setUserId(key);
			authVo.setUserName(userInfoMap.get(key));
			authVoList.add(authVo);
		});
		return DocResponseJson.ok(authVoList);
	}
	
	private Integer haveAuth(Set<String> authNameSet, DbAuthType dbAuthType) {
		return authNameSet.contains(dbAuthType.getName()) ? 1 : 0;
	}
	
	private UserAuth createUserAuth(Long sourceId, Long loginUserId, Long userId, Long authId) {
		UserAuth userAuth = new UserAuth();
		userAuth.setSysType(DocSysType.DB.getType());
		userAuth.setSysModuleType(DocSysModuleType.Db.DATASOURCE.getType());
		userAuth.setSysModuleId(sourceId);
		userAuth.setCreationTime(new Date());
		userAuth.setCreateUid(loginUserId);
		userAuth.setDelFlag(0);
		userAuth.setUserId(userId);
		userAuth.setAuthId(authId);
		return userAuth;
	}
}

