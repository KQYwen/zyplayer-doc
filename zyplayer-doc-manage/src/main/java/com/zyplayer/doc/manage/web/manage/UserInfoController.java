package com.zyplayer.doc.manage.web.manage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.manage.framework.config.security.DocUserDetails;
import com.zyplayer.doc.manage.framework.config.security.DocUserUtil;
import com.zyplayer.doc.manage.repository.manage.entity.UserInfo;
import com.zyplayer.doc.manage.service.manage.UserInfoService;

@RestController
@RequestMapping("/user/info")
@PreAuthorize("hasAuthority('USER_MANAGE')")
public class UserInfoController {

	@Autowired
	UserInfoService userInfoService;

	@PostMapping("/list")
	public ResponseJson<Object> list(String userName) {
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(userName)) {
			queryWrapper.like("user_name", userName);
		}
		List<UserInfo> userInfoList = userInfoService.list(queryWrapper);
		return DocResponseJson.ok(userInfoList);
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
