package com.zyplayer.doc.wiki.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.aspect.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.service.manage.*;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
	WikiPageService wikiPageService;
	@Resource
	UserAuthService userAuthService;
	@Resource
	AuthInfoService authInfoService;
	
	@PostMapping("/assign")
	public ResponseJson<List<WikiPageZan>> assign(Long pageId, Long userId, Long authId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		if (SpaceType.isPrivate(wikiSpaceSel.getType())) {
			return DocResponseJson.warn("");
		}
		if (SpaceType.isPublic(wikiSpaceSel.getType())) {
			return DocResponseJson.warn("");
		}
		if (!SpaceType.isPersonal(wikiSpaceSel.getType())) {
			return DocResponseJson.warn("");
		}
		if (!Objects.equals(currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			if (!DocUserUtil.havePageAuth(WikiAuthType.PAGE_AUTH_MANAGE.getName(), pageId)) {
				return DocResponseJson.warn("");
			}
		}
		AuthInfo authInfo = authInfoService.getById(authId);
		if (authInfo == null) {
			return DocResponseJson.warn("");
		}
		UserAuth userAuth = new UserAuth();
		userAuth.setAuthId(authId);
		userAuth.setCreateUid(currentUser.getUserId());
		userAuth.setCreationTime(new Date());
		userAuth.setDelFlag(0);
		userAuth.setUserId(userId);
		userAuth.setAuthCustomSuffix(String.valueOf(pageId));
		userAuthService.save(userAuth);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageZan wikiPageZan) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		return DocResponseJson.ok();
	}
}

