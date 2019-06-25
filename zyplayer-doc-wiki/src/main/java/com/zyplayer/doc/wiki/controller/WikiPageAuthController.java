package com.zyplayer.doc.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.service.manage.*;
import com.zyplayer.doc.wiki.controller.vo.UserPageAuthVo;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
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
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/auth")
public class WikiPageAuthController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageAuthController.class);
	
	@Resource
	WikiPageZanService wikiPageZanService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	WikiPageService wikiPageService;
	@Resource
	UserAuthService userAuthService;
	@Resource
	AuthInfoService authInfoService;
	
	@PostMapping("/assign")
	public ResponseJson<List<WikiPageZan>> assign(Long pageId, String authList) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
//		if (SpaceType.isPrivate(wikiSpaceSel.getType())) {
//			return DocResponseJson.warn("私人空间不可以编辑权限");
//		}
//		if (SpaceType.isPublic(wikiSpaceSel.getType())) {
//			return DocResponseJson.warn("公共空间不需要编辑权限");
//		}
		if (!SpaceType.isPersonal(wikiSpaceSel.getType())) {
			return DocResponseJson.warn("只有个人空间才可以编辑权限");
		}
		if (!Objects.equals(currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			if (!DocUserUtil.havePageAuth(WikiAuthType.PAGE_AUTH_MANAGE.getName(), pageId)) {
				return DocResponseJson.warn("您不是创建人或没有权限修改");
			}
		}
		List<String> authNameList = Stream.of(WikiAuthType.values()).map(WikiAuthType::getName).collect(Collectors.toList());
		QueryWrapper<AuthInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("auth_name", authNameList);
		Collection<AuthInfo> authInfoList = authInfoService.list(queryWrapper);
		Map<String, Long> authInfoMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getAuthName, AuthInfo::getId));
		
		// 先删除页面的所有用户的权限
		UserAuth userAuthDel = new UserAuth();
		userAuthDel.setDelFlag(1);
		QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("auth_custom_suffix", pageId);
		updateWrapper.eq("del_flag", 0);
		userAuthService.update(userAuthDel, updateWrapper);
		
		List<UserPageAuthVo> authVoList = JSON.parseArray(authList, UserPageAuthVo.class);
		for (UserPageAuthVo authVo : authVoList) {
			List<UserAuth> userAuthList = new LinkedList<>();
			if (Objects.equals(authVo.getEditPage(), 1)) {
				Long authId = authInfoMap.get(WikiAuthType.EDIT_PAGE.getName());
				UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getCommentPage(), 1)) {
				Long authId = authInfoMap.get(WikiAuthType.COMMENT_PAGE.getName());
				UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getDeletePage(), 1)) {
				Long authId = authInfoMap.get(WikiAuthType.DELETE_PAGE.getName());
				UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getPageFileUpload(), 1)) {
				Long authId = authInfoMap.get(WikiAuthType.PAGE_FILE_UPLOAD.getName());
				UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getPageFileDelete(), 1)) {
				Long authId = authInfoMap.get(WikiAuthType.PAGE_FILE_DELETE.getName());
				UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
				userAuthList.add(userAuth);
			}
			if (Objects.equals(authVo.getPageAuthManage(), 1)) {
				Long authId = authInfoMap.get(WikiAuthType.PAGE_AUTH_MANAGE.getName());
				UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
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
	public ResponseJson<Object> list(Long pageId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		if (!Objects.equals(currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			if (!DocUserUtil.havePageAuth(WikiAuthType.PAGE_AUTH_MANAGE.getName(), pageId)) {
				return DocResponseJson.warn("您没有权限管理该页面的权限");
			}
		}
		QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("auth_custom_suffix", pageId);
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
		List<UserPageAuthVo> authVoList = new LinkedList<>();
		// 组装结果集
		userAuthGroup.forEach((key, value) -> {
			Set<String> authNameSet = value.stream().map(auth -> authInfoMap.get(auth.getAuthId())).collect(Collectors.toSet());
			UserPageAuthVo authVo = new UserPageAuthVo();
			authVo.setEditPage(this.haveAuth(authNameSet, WikiAuthType.EDIT_PAGE));
			authVo.setCommentPage(this.haveAuth(authNameSet, WikiAuthType.COMMENT_PAGE));
			authVo.setDeletePage(this.haveAuth(authNameSet, WikiAuthType.DELETE_PAGE));
			authVo.setPageFileUpload(this.haveAuth(authNameSet, WikiAuthType.PAGE_FILE_UPLOAD));
			authVo.setPageFileDelete(this.haveAuth(authNameSet, WikiAuthType.PAGE_FILE_DELETE));
			authVo.setPageAuthManage(this.haveAuth(authNameSet, WikiAuthType.PAGE_AUTH_MANAGE));
			authVo.setUserId(key);
			authVo.setUserName(userInfoMap.get(key));
			authVoList.add(authVo);
		});
		return DocResponseJson.ok(authVoList);
	}
	
	private Integer haveAuth(Set<String> authNameSet, WikiAuthType wikiAuthType){
		return authNameSet.contains(wikiAuthType.getName()) ? 1 : 0;
	}
	
	private UserAuth createUserAuth(Long pageId, Long loginUserId, Long userId, Long authId){
		UserAuth userAuth = new UserAuth();
		userAuth.setAuthCustomSuffix(String.valueOf(pageId));
		userAuth.setCreationTime(new Date());
		userAuth.setCreateUid(loginUserId);
		userAuth.setDelFlag(0);
		userAuth.setUserId(userId);
		userAuth.setAuthId(authId);
		return userAuth;
	}
}

