package com.zyplayer.doc.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.config.security.UserAuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageZan;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.manage.mapper.UserGroupAuthMapper;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.AuthInfoService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.data.service.manage.UserMessageService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiPageZanService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.controller.vo.UserPageAuthVo;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
import com.zyplayer.doc.wiki.service.common.WikiPageAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@Slf4j
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/auth")
@RequiredArgsConstructor
public class WikiPageAuthController {

    private final WikiPageZanService wikiPageZanService;
    private final WikiSpaceService wikiSpaceService;
    private final UserInfoService userInfoService;
    private final WikiPageService wikiPageService;
    private final UserAuthService userAuthService;
    private final AuthInfoService authInfoService;
    private final WikiPageAuthService wikiPageAuthService;
    private final UserMessageService userMessageService;
    private final UserGroupAuthMapper userGroupAuthMapper;

    @PostMapping("/assign")
    public ResponseJson<List<WikiPageZan>> assign(Long pageId, String authList) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        WikiPage wikiPageSel = wikiPageService.getById(pageId);
        WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
        String canConfigAuth = wikiPageAuthService.canConfigAuth(wikiSpaceSel, pageId, currentUser.getUserId());
        if (canConfigAuth != null) {
            return DocResponseJson.warn(canConfigAuth);
        }
        List<String> authNameList = Stream.of(WikiAuthType.values()).map(WikiAuthType::getCode).collect(Collectors.toList());
        QueryWrapper<AuthInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("auth_name", authNameList);
        Collection<AuthInfo> authInfoList = authInfoService.list(queryWrapper);
        Map<String, Long> authInfoMap = authInfoList.stream().collect(Collectors.toMap(AuthInfo::getAuthName, AuthInfo::getId));
        // 先删除页面的所有用户的权限
        userAuthService.deleteModuleAuth(DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId);

        List<UserPageAuthVo> authVoList = JSON.parseArray(authList, UserPageAuthVo.class);
        for (UserPageAuthVo authVo : authVoList) {
            List<UserAuth> userAuthList = new LinkedList<>();
            if (Objects.equals(authVo.getEditPage(), 1)) {
                Long authId = authInfoMap.get(WikiAuthType.EDIT_PAGE.getCode());
                UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
                userAuthList.add(userAuth);
            }
            if (Objects.equals(authVo.getDeletePage(), 1)) {
                Long authId = authInfoMap.get(WikiAuthType.DELETE_PAGE.getCode());
                UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
                userAuthList.add(userAuth);
            }
            if (Objects.equals(authVo.getPageFileUpload(), 1)) {
                Long authId = authInfoMap.get(WikiAuthType.PAGE_FILE_UPLOAD.getCode());
                UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
                userAuthList.add(userAuth);
            }
            if (Objects.equals(authVo.getPageFileDelete(), 1)) {
                Long authId = authInfoMap.get(WikiAuthType.PAGE_FILE_DELETE.getCode());
                UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
                userAuthList.add(userAuth);
            }
            if (Objects.equals(authVo.getPageAuthManage(), 1)) {
                Long authId = authInfoMap.get(WikiAuthType.PAGE_AUTH_MANAGE.getCode());
                UserAuth userAuth = this.createUserAuth(pageId, currentUser.getUserId(), authVo.getUserId(), authId);
                userAuthList.add(userAuth);
            }
            if (userAuthList.isEmpty()) {
                continue;
            }
            // 保存权限，重新登录后可用，后期可以考虑在这里直接修改缓存里的用户权限
            userAuthService.saveBatch(userAuthList);
            // 给相关人发送消息
            UserInfo userInfo = userInfoService.getById(authVo.getUserId());
            UserMessage userMessage = userMessageService.createUserMessage(currentUser, pageId, wikiPageSel.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_AUTH);
            userMessage.setAffectUserId(userInfo.getId());
            userMessage.setAffectUserName(userInfo.getUserName());
            userMessageService.addWikiMessage(userMessage);
            // 刷新用户权限
            List<UserAuthInfo> userAuthListNew = userAuthService.getUserAuthSet(authVo.getUserId());
            DocUserUtil.setUserAuth(authVo.getUserId(), userAuthListNew);
        }
        return DocResponseJson.ok();
    }

    @PostMapping("/list")
    public ResponseJson<Object> list(Long pageId) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        WikiPage wikiPageSel = wikiPageService.getById(pageId);
        WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
        String canConfigAuth = wikiPageAuthService.canConfigAuth(wikiSpaceSel, pageId, currentUser.getUserId());
        if (canConfigAuth != null) {
            return DocResponseJson.warn(canConfigAuth);
        }
        List<UserAuth> authList = userAuthService.getModuleAuthList(DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId);
        if (CollectionUtils.isEmpty(authList)) {
            return DocResponseJson.ok();
        }
        // 权限ID对应的权限名
        Collection<AuthInfo> authInfoList = authInfoService.listByIds(authList.stream().map(UserAuth::getAuthId).collect(Collectors.toSet()));
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

    private Integer haveAuth(Set<String> authNameSet, WikiAuthType wikiAuthType) {
        return authNameSet.contains(wikiAuthType.getCode()) ? 1 : 0;
    }

    private UserAuth createUserAuth(Long pageId, Long loginUserId, Long userId, Long authId) {
        UserAuth userAuth = new UserAuth();
        userAuth.setSysType(DocSysType.WIKI.getType());
        userAuth.setSysModuleType(DocSysModuleType.Wiki.PAGE.getType());
        userAuth.setSysModuleId(pageId);
        userAuth.setCreationTime(new Date());
        userAuth.setCreateUid(loginUserId);
        userAuth.setDelFlag(0);
        userAuth.setUserId(userId);
        userAuth.setAuthId(authId);
        return userAuth;
    }
}

