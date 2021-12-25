package com.zyplayer.doc.api.controller;

import com.zyplayer.doc.api.controller.vo.DocUserAuthVo;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.repository.support.consts.ApiAuthType;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiDocService;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * api权限控制器
 *
 * @author 暮光：城中城
 * @since 2021年12月12日
 */
@AuthMan
@RestController
@RequestMapping("/doc-api/doc/auth")
public class ApiDocAuthController {
	private static Logger logger = LoggerFactory.getLogger(ApiDocAuthController.class);
	
	@Resource
	UserAuthService userAuthService;
	@Resource
	AuthInfoService authInfoService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	ApiDocService apiDocService;
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	
	@PostMapping("/list")
	public ResponseJson<Object> list(Long docId) {
		if (!apiDocAuthJudgeService.haveManageAuth(docId)) {
			return DocResponseJson.warn("没有此文档的操作权限");
		}
		List<UserAuth> authList = userAuthService.getModuleAuthList(DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), docId);
		if (CollectionUtils.isEmpty(authList)) {
			return DocResponseJson.ok();
		}
		// 权限ID对应的权限名
		Collection<AuthInfo> authInfoList = authInfoService.listByIds(authList.stream().map(UserAuth::getAuthId).collect(Collectors.toSet()));
		Map<Long, String> authInfoMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getId, AuthInfo::getAuthName));
		Collection<UserInfo> userInfoList = userInfoService.listByIds(authList.stream().map(UserAuth::getUserId).collect(Collectors.toSet()));
		Map<Long, UserInfo> userInfoMap = userInfoList.stream().collect(Collectors.toMap(UserInfo::getId, val -> val));
		// 返回结果组装
		List<DocUserAuthVo> authVoList = new LinkedList<>();
		for (UserAuth userAuth : authList) {
			UserInfo userInfo = userInfoMap.get(userAuth.getUserId());
			String authCode = authInfoMap.get(userAuth.getAuthId());
			DocUserAuthVo authVo = new DocUserAuthVo();
			authVo.setAuthType(ApiAuthType.typeOf(authCode).getType());
			authVo.setUserId(userAuth.getUserId());
			authVo.setUserNo(userInfo.getUserNo());
			authVo.setUserName(userInfo.getUserName());
			authVo.setEmail(userInfo.getEmail());
			authVo.setPhone(userInfo.getPhone());
			authVo.setSex(userInfo.getSex());
			authVoList.add(authVo);
		}
		return DocResponseJson.ok(authVoList);
	}
	
	@PostMapping("/assign")
	public ResponseJson<Object> assign(Long docId, Long userId, Integer authType) {
		if (!apiDocAuthJudgeService.haveManageAuth(docId)) {
			return DocResponseJson.warn("没有此文档的操作权限");
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		String authCode = ApiAuthType.typeOf(authType).getCode();
		AuthInfo authInfo = authInfoService.getByCode(authCode);
		List<UserAuth> userModuleAuthList = userAuthService.getUserModuleAuthList(userId, DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), docId);
		if (CollectionUtils.isNotEmpty(userModuleAuthList)) {
			UserAuth userAuth = userModuleAuthList.remove(0);
			// 错误数据兼容移除
			if (userModuleAuthList.size() > 0) {
				List<Long> authIdList = userModuleAuthList.stream().map(UserAuth::getId).collect(Collectors.toList());
				userAuthService.removeByIds(authIdList);
			}
			userAuth.setAuthId(authInfo.getId());
			userAuth.setUpdateTime(new Date());
			userAuth.setUpdateUid(currentUser.getUserId());
			userAuthService.updateById(userAuth);
		} else {
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId(userId);
			userAuth.setSysType(DocSysType.API.getType());
			userAuth.setSysModuleType(DocSysModuleType.Api.DOC.getType());
			userAuth.setSysModuleId(docId);
			userAuth.setAuthId(authInfo.getId());
			userAuth.setCreationTime(new Date());
			userAuth.setCreateUid(currentUser.getUserId());
			userAuth.setUpdateTime(new Date());
			userAuth.setUpdateUid(currentUser.getUserId());
			userAuth.setDelFlag(0);
			userAuthService.save(userAuth);
		}
		return DocResponseJson.ok();
	}
	
	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long docId, Long userId) {
		if (!apiDocAuthJudgeService.haveManageAuth(docId)) {
			return DocResponseJson.warn("没有此文档的操作权限");
		}
		userAuthService.deleteUserModuleAuth(userId, DocSysType.API.getType(), DocSysModuleType.Api.DOC.getType(), docId);
		return DocResponseJson.ok();
	}
}

