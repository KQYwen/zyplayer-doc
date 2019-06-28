package com.zyplayer.doc.manage.web.manage;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.manage.web.manage.param.UserListParam;
import com.zyplayer.doc.manage.web.manage.vo.AuthInfoVo;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@AuthMan("USER_MANAGE")
public class UserInfoController {

	@Resource
	UserInfoService userInfoService;
	@Resource
	AuthInfoService authInfoService;
	@Resource
	UserAuthService userAuthService;
	@Resource
	Mapper mapper;

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
			userInfoList.forEach(val -> val.setPassword(null));
		}
		PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
		return DocResponseJson.ok(pageInfo);
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(UserInfo userInfo) {
		// 不允许修改密码
		userInfo.setPassword(null);
		if (userInfo.getId() != null && userInfo.getId() > 0) {
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

	@PostMapping("/auth/list")
	public ResponseJson<Object> authList(String userIds) {
		List<AuthInfo> authList = authInfoService.list();
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("user_id", new Object[]{userIds.split(",")});
		queryWrapper.eq("del_flag", 0);
		List<UserAuth> userAuths = userAuthService.list(queryWrapper);
		Map<Long, UserAuth> userAuthMap = userAuths.stream().collect(Collectors.toMap(UserAuth::getAuthId, Function.identity(), (val1, val2) -> val1));
		List<AuthInfoVo> authInfoVoList = new LinkedList<>();
		authList.forEach(val -> {
			UserAuth userAuth = userAuthMap.get(val.getId());
			AuthInfoVo infoVo = mapper.map(val, AuthInfoVo.class);
			infoVo.setChecked((userAuth == null) ? 0 : 1);
			authInfoVoList.add(infoVo);
		});
		return DocResponseJson.ok(authInfoVoList);
	}
	
	@AuthMan("AUTH_ASSIGN")
	@PostMapping("/auth/update")
	public ResponseJson<Object> updateAuth(String userIds, String authIds) {
		List<Long> userIdsList = Arrays.stream(userIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
		List<Long> authIdsList = Arrays.stream(authIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
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

	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long id) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setDelFlag(1);
		userInfo.setUpdateTime(new Date());
		userInfoService.updateById(userInfo);
		return DocResponseJson.ok();
	}
}
