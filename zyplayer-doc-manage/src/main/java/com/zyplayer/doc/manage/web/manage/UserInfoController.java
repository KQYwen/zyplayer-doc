package com.zyplayer.doc.manage.web.manage;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.manage.web.manage.param.UserListParam;
import com.zyplayer.doc.manage.web.manage.vo.AuthInfoVo;
import com.zyplayer.doc.manage.web.manage.vo.UserInfoAuthVo;
import com.zyplayer.doc.manage.web.manage.vo.UserAuthVo;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/info")
public class UserInfoController {

	@Resource
	UserInfoService userInfoService;
	@Resource
	AuthInfoService authInfoService;
	@Resource
	UserAuthService userAuthService;
	@Resource
	Mapper mapper;
	
	@AuthMan
	@PostMapping("/selfInfo")
	public ResponseJson<Object> selfInfo() {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserInfo userInfo = userInfoService.getById(currentUser.getUserId());
		userInfo.setPassword(null);
		return DocResponseJson.ok(userInfo);
	}
	
	@AuthMan
	@PostMapping("/selfInfoWithAuth")
	public ResponseJson<UserInfoAuthVo> selfInfoWithAuth() {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserInfo userInfo = userInfoService.getById(currentUser.getUserId());
		userInfo.setPassword(null);
		UserAuthVo sysAuthInfoVo = new UserAuthVo();
		sysAuthInfoVo.setUserManage(DocUserUtil.haveAuth(DocAuthConst.USER_MANAGE));
		UserInfoAuthVo selfInfoVo = new UserInfoAuthVo();
		selfInfoVo.setUserInfo(userInfo);
		selfInfoVo.setUserAuth(sysAuthInfoVo);
		return DocResponseJson.ok(selfInfoVo);
	}
	
	@AuthMan(DocAuthConst.USER_MANAGE)
	@PostMapping("/list")
	public ResponseJson<Object> list(UserListParam param) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(param.getKeyword())) {
			queryWrapper.like(param.getType() == 1, "id", param.getKeyword());
			queryWrapper.like(param.getType() == 2, "user_no", param.getKeyword());
			queryWrapper.like(param.getType() == 3, "user_name", param.getKeyword());
			queryWrapper.like(param.getType() == 4, "phone", param.getKeyword());
			queryWrapper.like(param.getType() == 5, "email", param.getKeyword());
		}
		queryWrapper.ne("del_flag", 1);
		PageHelper.startPage(param.getPageNum(), param.getPageSize(), true);
		List<UserInfo> userInfoList = userInfoService.list(queryWrapper);
		if (userInfoList != null && userInfoList.size() > 0) {
			userInfoList.forEach(val -> val.setPassword(""));
		}
		PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
		return DocResponseJson.ok(pageInfo);
	}
	
	@AuthMan(DocAuthConst.USER_MANAGE)
	@PostMapping("/update")
	public ResponseJson<Object> update(UserInfo userInfo) {
		if (StringUtils.isBlank(userInfo.getUserNo())) {
			return DocResponseJson.warn("用户账号必填");
		}
		if (StringUtils.isBlank(userInfo.getUserName())) {
			return DocResponseJson.warn("用户名必填");
		}
		Long userId = Optional.ofNullable(userInfo.getId()).orElse(0L);
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_no", userInfo.getUserNo());
		queryWrapper.ne(userId > 0, "id", userInfo.getId());
		int count = userInfoService.count(queryWrapper);
		if (count > 0) {
			return DocResponseJson.warn("改用户账号已存在");
		}
		// 密码支持自定义修改，管理权限都有，开放了随便改吧~
		String password = userInfo.getPassword();
		if (StringUtils.isNotBlank(password)) {
			String newPassword = DigestUtils.md5DigestAsHex(password.getBytes());
			userInfo.setPassword(newPassword);
		} else {
			// 防止改为空
			userInfo.setPassword(null);
		}
		if (userId > 0) {
			userInfo.setUpdateTime(new Date());
			userInfoService.updateById(userInfo);
		} else {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			userInfo.setCreationTime(new Date());
			userInfo.setCreateUid(currentUser.getUserId());
			userInfoService.save(userInfo);
		}
		return DocResponseJson.ok();
	}
	
	@AuthMan(DocAuthConst.USER_MANAGE)
	@PostMapping("/resetPassword")
	public ResponseJson<Object> resetPassword(UserInfo userInfo) {
		String password = RandomUtil.randomNumbers(6);
		UserInfo userInfoUp = new UserInfo();
		if (StringUtils.isNotBlank(password)) {
			String newPassword = DigestUtils.md5DigestAsHex(password.getBytes());
			userInfoUp.setPassword(newPassword);
		}
		userInfoUp.setId(userInfo.getId());
		userInfoUp.setUpdateTime(new Date());
		userInfoService.updateById(userInfoUp);
		return DocResponseJson.ok(password);
	}
	
	@AuthMan(DocAuthConst.USER_MANAGE)
	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long id) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setDelFlag(1);
		userInfo.setUpdateTime(new Date());
		userInfoService.updateById(userInfo);
		return DocResponseJson.ok();
	}
	
	@AuthMan(DocAuthConst.AUTH_ASSIGN)
	@PostMapping("/auth/list")
	public ResponseJson<Object> authList(String userIds) {
		// 所有权限
		QueryWrapper<AuthInfo> authWrapper = new QueryWrapper<>();
		authWrapper.eq("auth_type", 1);
		List<AuthInfo> authList = authInfoService.list(authWrapper);
		// 用户权限
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("del_flag", 0);
		queryWrapper.in("user_id", Arrays.asList(userIds.split(",")));
		List<UserAuth> userAuthList = userAuthService.list(queryWrapper);
		Map<Long, UserAuth> userAuthMap = userAuthList.stream().collect(Collectors.toMap(UserAuth::getAuthId, Function.identity(), (val1, val2) -> val1));
		List<AuthInfoVo> authInfoVoList = new LinkedList<>();
		authList.forEach(val -> {
			UserAuth userAuth = userAuthMap.get(val.getId());
			AuthInfoVo infoVo = mapper.map(val, AuthInfoVo.class);
			infoVo.setChecked((userAuth == null) ? 0 : 1);
			authInfoVoList.add(infoVo);
		});
		return DocResponseJson.ok(authInfoVoList);
	}
	
	@AuthMan(DocAuthConst.AUTH_ASSIGN)
	@PostMapping("/auth/update")
	public ResponseJson<Object> updateAuth(String userIds, String authIds) {
		List<Long> userIdsList = Arrays.stream(userIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
		List<Long> authIdsList = Collections.emptyList();
		if (StringUtils.isNotBlank(authIds)) {
			authIdsList = Arrays.stream(authIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		
		UserAuth userAuthUp = new UserAuth();
		userAuthUp.setDelFlag(1);
		userAuthUp.setUpdateTime(new Date());
		userAuthUp.setUpdateUid(currentUser.getUserId());
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("user_id", userIdsList);
		userAuthService.update(userAuthUp, queryWrapper);
		
		List<UserAuth> createList = new LinkedList<>();
		for (int i = 0; i < userIdsList.size(); i++) {
			for (int j = 0; j < authIdsList.size(); j++) {
				UserAuth userAuth = new UserAuth();
				userAuth.setUserId(userIdsList.get(i));
				userAuth.setAuthId(authIdsList.get(j));
				userAuth.setCreateUid(currentUser.getUserId());
				userAuth.setCreationTime(new Date());
				userAuth.setDelFlag(0);
				createList.add(userAuth);
			}
		}
		userAuthService.saveBatch(createList);
		return DocResponseJson.ok();
	}
}
