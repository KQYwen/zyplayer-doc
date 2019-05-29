package com.zyplayer.doc.manage.web.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.aspect.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.manage.web.manage.vo.AuthInfoVo;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/info")
@AuthMan("USER_MANAGE")
public class UserInfoController {

	@Autowired
	UserInfoService userInfoService;
	@Autowired
	AuthInfoService authInfoService;
	@Autowired
	UserAuthService userAuthService;
	@Autowired
	Mapper mapper;

	@PostMapping("/list")
	public ResponseJson<Object> list(String userName) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(userName)) {
			queryWrapper.like("user_name", userName);
		}
		queryWrapper.eq("del_flag", 0);
		List<UserInfo> userInfoList = userInfoService.list(queryWrapper);
		if (userInfoList != null && userInfoList.size() > 0) {
			userInfoList.forEach(val -> val.setPassword(null));
		}
		return DocResponseJson.ok(userInfoList);
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

	@PostMapping("/update")
	public ResponseJson<Object> update(UserInfo userInfo) {
		String password = userInfo.getPassword();
		if (StringUtils.isNotBlank(password)) {
			password = DigestUtils.md5DigestAsHex(password.getBytes());
			userInfo.setPassword(password);
		}
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
}
