package com.zyplayer.doc.manage.web.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/auth")
@AuthMan("AUTH_ASSIGN")
public class UserAuthController {

	@Autowired
	AuthInfoService authInfoService;
	@Autowired
	UserAuthService userAuthService;

	@PostMapping("/list")
	public ResponseJson<Object> list(Long userId) {
		QueryWrapper<UserAuth> userAuthWrapper = new QueryWrapper<>();
		userAuthWrapper.eq("user_id", userId);
		List<UserAuth> userAuthList = userAuthService.list(userAuthWrapper);
		if (userAuthList == null || userAuthList.isEmpty()) {
			return DocResponseJson.ok();
		}
		QueryWrapper<AuthInfo> authQueryWrapper = new QueryWrapper<>();
		authQueryWrapper.in("id", userAuthList.stream().collect(Collectors.mapping(UserAuth::getAuthId, Collectors.toList())));
		List<AuthInfo> authList = authInfoService.list(authQueryWrapper);
		return DocResponseJson.ok(authList);
	}

	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long id) {
		userAuthService.removeById(id);
		return DocResponseJson.ok();
	}

	@PostMapping("/insert")
	public ResponseJson<Object> insert(Long id, Long userId, Long authId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UserAuth userAuth = new UserAuth();
		userAuth.setAuthId(authId);
		userAuth.setUserId(userId);
		if (id != null && id > 0) {
			userAuth.setId(id);
			userAuthService.updateById(userAuth);
		} else {
			userAuth.setCreationTime(new Date());
			userAuth.setCreateUid(currentUser.getUserId());
			userAuthService.save(userAuth);
		}
		return DocResponseJson.ok();
	}

}
