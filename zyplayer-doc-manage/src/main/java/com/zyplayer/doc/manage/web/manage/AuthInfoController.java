package com.zyplayer.doc.manage.web.manage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.manage.framework.config.security.DocUserDetails;
import com.zyplayer.doc.manage.framework.config.security.DocUserUtil;
import com.zyplayer.doc.manage.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.manage.service.manage.AuthInfoService;

@RestController
@RequestMapping("/auth/info")
@PreAuthorize("hasAuthority('AUTH_MANAGE')")
public class AuthInfoController {

	@Autowired
	AuthInfoService authInfoService;

	@PostMapping("/list")
	public ResponseJson<Object> list() {
		List<AuthInfo> authList = authInfoService.list();
		return DocResponseJson.ok(authList);
	}

	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long id) {
		AuthInfo authInfo = authInfoService.getById(id);
		if (authInfo == null || authInfo.getCanEdit() == 0) {
			return DocResponseJson.warn("该权限不允许删除");
		}
		authInfoService.removeById(id);
		return DocResponseJson.ok();
	}

	@PostMapping("/update")
	public ResponseJson<Object> update(Long id, String authName, String authDesc) {
		AuthInfo authInfo = new AuthInfo();
		authInfo.setAuthDesc(authDesc);
		authInfo.setAuthName(authName);
		if (id != null && id > 0) {
			AuthInfo authInfoSel = authInfoService.getById(id);
			if (authInfoSel == null || authInfoSel.getCanEdit() == 0) {
				return DocResponseJson.warn("该权限不允许编辑");
			}
			authInfo.setId(id);
			authInfoService.updateById(authInfo);
		} else {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			authInfo.setCreationTime(new Date());
			authInfo.setCreateUid(currentUser.getUserId());
			authInfoService.save(authInfo);
		}
		return DocResponseJson.ok();
	}
}
