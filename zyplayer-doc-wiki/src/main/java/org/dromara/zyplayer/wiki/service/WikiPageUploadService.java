package org.dromara.zyplayer.wiki.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.dromara.zyplayer.core.exception.ConfirmException;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.UserMessage;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPage;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPageContent;
import org.dromara.zyplayer.data.repository.manage.entity.WikiSpace;
import org.dromara.zyplayer.data.repository.manage.mapper.WikiPageMapper;
import org.dromara.zyplayer.data.repository.support.consts.DocSysType;
import org.dromara.zyplayer.data.repository.support.consts.UserMsgType;
import org.dromara.zyplayer.wiki.framework.common.MDToText;
import org.dromara.zyplayer.wiki.framework.consts.SpaceType;
import org.dromara.zyplayer.wiki.service.common.WikiPageAuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.dromara.zyplayer.data.service.manage.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @author sh1yu
 * @since 2019年2月17日
 */
@Service
@RequiredArgsConstructor
public class WikiPageUploadService {
    private final WikiPageService wikiPageService;
    private final WikiPageContentService wikiPageContentService;
    private final WikiSpaceService wikiSpaceService;
    private final WikiPageMapper wikiPageMapper;
    private final WikiPageAuthService wikiPageAuthService;
    private final UserMessageService userMessageService;
    private final WikiPageHistoryService wikiPageHistoryService;

    public Object update(WikiPage wikiPage, String content, String preview) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        WikiPageContent pageContent = new WikiPageContent();
        pageContent.setContent(content);
        pageContent.setPreview(preview);
        if (StringUtils.isBlank(wikiPage.getName())) {
            return "标题不能为空！";
        }
        Long pageId = wikiPage.getId();
        Long spaceId = wikiPage.getSpaceId();
        if (pageId != null && pageId > 0) {
            WikiPage wikiPageSel = wikiPageService.getById(pageId);
            // 编辑权限判断
            WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
            String canEdit = wikiPageAuthService.canEdit(wikiSpaceSel, wikiPageSel.getEditType(), wikiPageSel.getId(), currentUser.getUserId());
            if (canEdit != null) {
                return canEdit;
            }
            spaceId = wikiPageSel.getSpaceId();
            wikiPage.setSpaceId(null);
            wikiPage.setEditType(null);
            wikiPage.setUpdateTime(new Date());
            wikiPage.setUpdateUserId(currentUser.getUserId());
            wikiPage.setUpdateUserName(currentUser.getUsername());
            wikiPageService.updateById(wikiPage);
            // 详情
            pageContent.setUpdateTime(new Date());
            pageContent.setUpdateUserId(currentUser.getUserId());
            pageContent.setUpdateUserName(currentUser.getUsername());
            UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
            wrapper.eq("page_id", pageId);
            wikiPageContentService.update(pageContent, wrapper);
            // 给相关人发送消息
            UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_UPDATE);
            userMessageService.addWikiMessage(userMessage);
        } else {
            Long parentId = Optional.ofNullable(wikiPage.getParentId()).orElse(0L);
            WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
            if (wikiSpaceSel == null) {
                return "未找到指定的空间！";
            }
            // 空间不是自己的
            if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
                return "您没有权限新增该空间的文章！";
            }
            // 空间不是自己的
            if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
                return "您没有权限新增该空间的文章！";
            }
            if (parentId > 0) {
                WikiPage wikiPageParent = wikiPageService.getById(parentId);
                if (!Objects.equals(wikiPage.getSpaceId(), wikiPageParent.getSpaceId())) {
                    return "当前空间和父页面的空间不一致，请重新选择父页面！";
                }
            }
            Integer lastSeq = wikiPageMapper.getLastSeq(wikiPage.getSpaceId(), parentId);
            lastSeq = Optional.ofNullable(lastSeq).orElse(99999);
            wikiPage.setSeqNo(lastSeq + 1);
            wikiPage.setCreateTime(new Date());
            wikiPage.setUpdateTime(new Date());
            wikiPage.setCreateUserId(currentUser.getUserId());
            wikiPage.setCreateUserName(currentUser.getUsername());
            wikiPageService.save(wikiPage);
            // 重置当前分支的所有节点seq值
            wikiPageMapper.updateChildrenSeq(wikiPage.getSpaceId(), parentId);
            // 详情
            pageContent.setPageId(wikiPage.getId());
            pageContent.setCreateTime(new Date());
            pageContent.setCreateUserId(currentUser.getUserId());
            pageContent.setCreateUserName(currentUser.getUsername());
            wikiPageContentService.save(pageContent);
            // 给相关人发送消息
            UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPage.getId(), wikiPage.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_CREATE);
            userMessageService.addWikiMessage(userMessage);
        }
        try {
            // 创建历史记录
            wikiPageHistoryService.saveRecord(spaceId, wikiPage.getId(), content);
        } catch (ConfirmException e) {
            return e.getMessage();
        }
        return wikiPage;
    }
}
