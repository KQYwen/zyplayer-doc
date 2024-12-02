package org.dromara.zyplayer.manage.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.zyplayer.core.annotation.AuthMan;
import org.dromara.zyplayer.core.json.DocResponseJson;
import org.dromara.zyplayer.core.json.ResponseJson;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.AuthInfo;
import org.dromara.zyplayer.data.repository.manage.entity.UserAuth;
import org.dromara.zyplayer.data.repository.support.consts.DocAuthConst;
import org.dromara.zyplayer.data.service.manage.AuthInfoService;
import org.dromara.zyplayer.data.service.manage.UserAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户权限控制器
 *
 * @author 暮光：城中城
 * @since 2018-12-08
 */
@RestController
@RequestMapping("/user/auth")
@AuthMan(DocAuthConst.AUTH_ASSIGN)
public class UserAuthController {
	
	@Resource
	AuthInfoService authInfoService;
	@Resource
	UserAuthService userAuthService;
	
	@PostMapping("/list")
	public ResponseJson<Object> list(Long userId) {
		QueryWrapper<UserAuth> userAuthWrapper = new QueryWrapper<>();
		userAuthWrapper.eq("user_id", userId);
		userAuthWrapper.eq("del_flag", 0);
		List<UserAuth> userAuthList = userAuthService.list(userAuthWrapper);
		if (userAuthList == null || userAuthList.isEmpty()) {
			return DocResponseJson.ok();
		}
		QueryWrapper<AuthInfo> authQueryWrapper = new QueryWrapper<>();
		authQueryWrapper.in("id", userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toSet()));
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
