package org.dromara.zyplayer.wiki.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.dromara.zyplayer.core.annotation.AuthMan;
import org.dromara.zyplayer.core.enums.PageFileSource;
import org.dromara.zyplayer.core.exception.ConfirmException;
import org.dromara.zyplayer.core.json.DocResponseJson;
import org.dromara.zyplayer.core.json.ResponseJson;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.*;
import org.dromara.zyplayer.data.repository.manage.mapper.WikiPageContentMapper;
import org.dromara.zyplayer.data.repository.manage.mapper.WikiPageMapper;
import org.dromara.zyplayer.data.repository.manage.param.SearchByEsParam;
import org.dromara.zyplayer.data.repository.manage.vo.SpaceNewsVo;
import org.dromara.zyplayer.data.repository.manage.vo.WikiPageTemplateInfoVo;
import org.dromara.zyplayer.data.repository.support.consts.DocSysType;
import org.dromara.zyplayer.data.repository.support.consts.UserMsgType;
import org.dromara.zyplayer.data.service.manage.*;
import org.dromara.zyplayer.data.service.params.PageDeleteParam;
import org.dromara.zyplayer.data.service.params.PageRecycleListParam;
import org.dromara.zyplayer.data.service.params.RecycleDeletePageParam;
import org.dromara.zyplayer.data.service.params.RestorePageParam;
import org.dromara.zyplayer.data.utils.CachePrefix;
import org.dromara.zyplayer.data.utils.CacheUtil;
import org.dromara.zyplayer.wiki.controller.vo.WikiPageContentVo;
import org.dromara.zyplayer.wiki.controller.vo.WikiPageVo;
import org.dromara.zyplayer.wiki.framework.consts.SpaceType;
import org.dromara.zyplayer.wiki.service.WikiPageUploadService;
import org.dromara.zyplayer.wiki.service.WikiPageWebService;
import org.dromara.zyplayer.wiki.service.common.WikiPageAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @author Sh1yu
 * @since 2019年2月17日
 */
@Slf4j
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page")
@RequiredArgsConstructor
public class WikiPageController {

    private final WikiPageService wikiPageService;
    private final WikiPageContentService wikiPageContentService;
    private final WikiPageContentMapper wikiPageContentMapper;
    private final WikiPageFileService wikiPageFileService;
    private final WikiPageZanService wikiPageZanService;
    private final WikiSpaceService wikiSpaceService;
    private final WikiPageAuthService wikiPageAuthService;
    private final WikiPageUploadService wikipageUploadService;
    private final UserMessageService userMessageService;
    private final WikiPageHistoryService wikiPageHistoryService;
    private final WikiPageMapper wikiPageMapper;
    private final WikiPageCommentService wikiPageCommentService;
    private final WikiPageTemplateService wikiPageTemplateService;
    private final WikiPageWebService wikiPageWebService;

    @PostMapping("/list")
    public ResponseJson<List<WikiPageVo>> list(WikiPage wikiPage) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
        // 私人空间
        if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
            return DocResponseJson.warn("您没有权限查看该空间的文章列表！");
        }
        List<WikiPageTemplateInfoVo> wikiPageList = wikiPageService.wikiPageTemplateInfos(wikiPage.getSpaceId());
        Map<Long, List<WikiPageVo>> listMap = wikiPageList.stream().map(WikiPageVo::new).collect(Collectors.groupingBy(WikiPageVo::getParentId));
        List<WikiPageVo> nodePageList = listMap.get(0L);
        if (CollectionUtils.isNotEmpty(nodePageList)) {
            nodePageList = nodePageList.stream().sorted(Comparator.comparingInt(WikiPageVo::getSeqNo)).collect(Collectors.toList());
            this.setChildren(listMap, nodePageList, "");
        }
        return DocResponseJson.ok(nodePageList);
    }
    
    @PostMapping("/recycleList")
    public ResponseJson<List<WikiPageVo>> recycleList(PageRecycleListParam param) {
        return wikiPageWebService.recycleList(param);
    }
    
    @PostMapping("/restore")
    public ResponseJson<Object> restore(RestorePageParam param) {
        return wikiPageWebService.restore(param.getPageIds());
    }
    
    @PostMapping("/recycleDelete")
    public ResponseJson<Object> recycleDelete(RecycleDeletePageParam param) {
        return wikiPageWebService.recycleDelete(param);
    }
    
    @PostMapping("/detail")
    public ResponseJson<WikiPageContentVo> detail(WikiPage wikiPage) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
        // 页面已删除
        if (wikiPageSel == null || Objects.equals(wikiPageSel.getDelFlag(), 1)) {
            return DocResponseJson.warn("该页面不存在或已删除！");
        }
        WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
        // 空间已删除
        if (wikiSpaceSel == null || Objects.equals(wikiSpaceSel.getDelFlag(), 1)) {
            return DocResponseJson.warn("该页面不存在或已删除！");
        }
        // 私人空间
        if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
            return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
        }
        LambdaQueryWrapper<WikiPageContent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WikiPageContent::getPageId, wikiPage.getId());
        WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
        // 查询附件
        LambdaQueryWrapper<WikiPageFile> wrapperFile = new LambdaQueryWrapper<>();
        wrapperFile.eq(WikiPageFile::getPageId, wikiPage.getId());
        wrapperFile.eq(WikiPageFile::getDelFlag, 0);
        wrapperFile.eq(WikiPageFile::getFileSource, PageFileSource.UPLOAD_FILES.getSource());
        List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
        for (WikiPageFile pageFile : pageFiles) {
            pageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + pageFile.getUuid());
        }
        LambdaQueryWrapper<WikiPageZan> wrapperZan = new LambdaQueryWrapper<>();
        wrapperZan.eq(WikiPageZan::getPageId, wikiPage.getId());
        wrapperZan.eq(WikiPageZan::getCreateUserId, currentUser.getUserId());
        wrapperZan.eq(WikiPageZan::getYn, 1);
        WikiPageZan pageZan = wikiPageZanService.getOne(wrapperZan);
        WikiPageContentVo vo = new WikiPageContentVo();
        vo.setWikiPage(wikiPageSel);
        vo.setPageContent(pageContent);
        vo.setFileList(pageFiles);
        vo.setSelfZan((pageZan != null) ? 1 : 0);
        vo.setSelfUserId(currentUser.getUserId());
        // 上传附件、编辑、权限设置、删除 的权限
        String canEdit = wikiPageAuthService.canEdit(wikiSpaceSel, wikiPageSel.getEditType(), wikiPageSel.getId(), currentUser.getUserId());
        String canDelete = wikiPageAuthService.canDelete(wikiSpaceSel, wikiPageSel.getEditType(), wikiPageSel.getId(), currentUser.getUserId());
        String canUploadFile = wikiPageAuthService.canUploadFile(wikiSpaceSel, wikiPageSel.getId(), currentUser.getUserId());
        String canDeleteFile = wikiPageAuthService.canDeleteFile(wikiSpaceSel, wikiPageSel.getId(), currentUser.getUserId());
        String canConfigAuth = wikiPageAuthService.canConfigAuth(wikiSpaceSel, wikiPageSel.getId(), currentUser.getUserId());
        vo.setCanEdit((canEdit == null) ? 1 : 0);
        vo.setCanDelete((canDelete == null) ? 1 : 0);
        vo.setCanDeleteFile((canDeleteFile == null) ? 1 : 0);
        vo.setCanUploadFile((canUploadFile == null) ? 1 : 0);
        vo.setCanConfigAuth((canConfigAuth == null) ? 1 : 0);
        // 高并发下会有覆盖问题，但不重要~
        int viewNum = Optional.ofNullable(wikiPageSel.getViewNum()).orElse(0);
        WikiPage wikiPageUp = new WikiPage();
        wikiPageUp.setId(wikiPageSel.getId());
        wikiPageUp.setViewNum(viewNum + 1);
        wikiPageService.updateById(wikiPageUp);
        // 修改返回值里的查看数+1
        wikiPageSel.setViewNum(viewNum + 1);
        return DocResponseJson.ok(vo);
    }

    @PostMapping("/changeParent")
    public ResponseJson<Object> changeParent(WikiPage wikiPage, Integer beforeSeq, Integer afterSeq) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
        // 编辑权限判断
        WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
        String canEdit = wikiPageAuthService.canEdit(wikiSpaceSel, wikiPageSel.getEditType(), wikiPageSel.getId(), currentUser.getUserId());
        if (canEdit != null) {
            return DocResponseJson.warn(canEdit);
        }
        WikiPage wikiPageUp = new WikiPage();
        wikiPageUp.setId(wikiPage.getId());
        wikiPageUp.setParentId(wikiPage.getParentId());
        wikiPageUp.setUpdateTime(new Date());
        wikiPageUp.setUpdateUserId(currentUser.getUserId());
        wikiPageUp.setUpdateUserName(currentUser.getUsername());
        wikiPageService.changeParent(wikiPageUp, beforeSeq, afterSeq);
        return DocResponseJson.ok();
    }

    @PostMapping("/delete")
    public ResponseJson<Object> delete(PageDeleteParam param) {
        wikiPageWebService.delete(param);
        return DocResponseJson.ok();
    }

    @PostMapping("/update")
    public ResponseJson<Object> update(WikiPage wikiPage, String content, String preview) {
        Object info = wikipageUploadService.update(wikiPage, content, preview);
        if (null != info) {
            if (info instanceof WikiPage) {
                return DocResponseJson.ok(info);
            }
            return DocResponseJson.warn((String) info);
        }
        return DocResponseJson.warn("状态异常");
    }

    public boolean isLassoDoll(WikiPage wikiPage, Long moveToPageId) {
        if (0L != moveToPageId) {
            if (wikiPage.getId().equals(moveToPageId)) {
                return true;
            }
            UpdateWrapper<WikiPage> wrapper = new UpdateWrapper<>();
            wrapper.eq("parent_id", wikiPage.getId());
            wrapper.eq("space_id", wikiPage.getSpaceId());
            //处理子节点也需要进行移动
            List<WikiPage> wikiPageList = wikiPageService.list(wrapper);
            for (WikiPage page : wikiPageList) {
                if (isLassoDoll(page, moveToPageId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @PostMapping("/move")
    public ResponseJson<Object> move(WikiPage wikiPage, Long moveToPageId, Long moveToSpaceId) {
        if (isLassoDoll(wikiPage, moveToPageId)) {
            return DocResponseJson.warn("不能移动自己到自己或自己的子节点下");
        }
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        //获取原page信息
        WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
        wikiPageSel.setSpaceId(moveToSpaceId);
        wikiPageSel.setParentId(moveToPageId);
        wikiPageSel.setUpdateTime(new Date());
        wikiPageSel.setUpdateUserId(currentUser.getUserId());
        wikiPageSel.setUpdateUserName(currentUser.getUsername());
        wikiPageService.updateById(wikiPageSel);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("page_id",wikiPage.getId());
        queryWrapper.eq("space_id",wikiPage.getSpaceId());
        WikiPageTemplate bindTemplate = wikiPageTemplateService.getOne(queryWrapper);
        if(null != bindTemplate){
            bindTemplate.setSpaceId(moveToSpaceId);
            wikiPageTemplateService.updateById(bindTemplate);
        }

        UpdateWrapper<WikiPage> wrapper = new UpdateWrapper<>();
        wrapper.eq("parent_id", wikiPage.getId());
        wrapper.eq("space_id", wikiPage.getSpaceId());
        //处理子节点也需要进行移动
        List<WikiPage> wikiPageList = wikiPageService.list(wrapper);
        for (WikiPage page : wikiPageList) {
            move(page, wikiPageSel.getId() , moveToSpaceId);
        }
        // 给相关人发送消息
        UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_MOVE);
        userMessageService.addWikiMessage(userMessage);
        return DocResponseJson.ok();
    }

    @PostMapping("/copy")
    public ResponseJson<Object> copy(WikiPage wikiPage, Long moveToPageId, Long moveToSpaceId) {
        if (isLassoDoll(wikiPage, moveToPageId)) {
            return DocResponseJson.warn("不能移动自己到自己或自己的子节点下");
        }
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        //获取原page信息
        WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
        Integer lastSeq = wikiPageMapper.getLastSeq(wikiPage.getSpaceId(), moveToPageId);
        lastSeq = Optional.ofNullable(lastSeq).orElse(99999);
        wikiPageSel.setSeqNo(lastSeq + 1);
        wikiPageSel.setId(null);
        wikiPageSel.setSpaceId(moveToSpaceId);
        wikiPageSel.setParentId(moveToPageId);
        wikiPageSel.setCreateTime(new Date());
        wikiPageSel.setUpdateTime(new Date());
        wikiPageSel.setCreateUserId(currentUser.getUserId());
        wikiPageSel.setCreateUserName(currentUser.getUsername());
        wikiPageService.save(wikiPageSel);
        // 重置当前分支的所有节点seq值
        wikiPageMapper.updateChildrenSeq(wikiPage.getSpaceId(), moveToPageId);
        // 详情处理
        UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
        wrapper.eq("page_id", wikiPage.getId());
        WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
        pageContent.setId(null);
        pageContent.setPageId(wikiPageSel.getId());
        pageContent.setCreateTime(new Date());
        pageContent.setCreateUserId(currentUser.getUserId());
        pageContent.setCreateUserName(currentUser.getUsername());
        wikiPageContentService.save(pageContent);
        //文件
        UpdateWrapper<WikiPageFile> wrapperFile = new UpdateWrapper<>();
        wrapperFile.eq("page_id", wikiPageSel.getId());
        List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
        for (WikiPageFile pageFile : pageFiles) {
            pageFile.setId(null);
            pageFile.setUuid(IdUtil.simpleUUID());
            pageFile.setPageId(wikiPageSel.getId());
            wikiPageFileService.save(pageFile);
        }
        //点赞
        UpdateWrapper<WikiPageZan> wrapperZan = new UpdateWrapper<>();
        wrapperZan.eq("page_id", wikiPage.getId());
        List<WikiPageZan> list = wikiPageZanService.list(wrapperZan);
        for (WikiPageZan wikiPageZan : list) {
            wikiPageZan.setId(null);
            wikiPageZan.setPageId(wikiPageSel.getId());
            wikiPageZanService.save(wikiPageZan);
        }
        //评论
        UpdateWrapper<WikiPageComment> commentWrapper = new UpdateWrapper<>();
        commentWrapper.eq("page_id", wikiPageSel.getId());
        List<WikiPageComment> pageCommentList = wikiPageCommentService.list(commentWrapper);
        for (WikiPageComment wikiPageComment : pageCommentList) {
            wikiPageComment.setId(null);
            wikiPageComment.setPageId(wikiPageSel.getId());
            wikiPageCommentService.save(wikiPageComment);
        }
        //处理子节点也需要进行复制
        UpdateWrapper<WikiPage> childWrapper = new UpdateWrapper<>();
        childWrapper.eq("parent_id", wikiPage.getId());
        childWrapper.eq("space_id", wikiPage.getSpaceId());
        List<WikiPage> wikiPageList = wikiPageService.list(childWrapper);
        for (WikiPage page : wikiPageList) {
            copy(page, wikiPageSel.getId(), moveToSpaceId);
        }
        // 给相关人发送消息
        UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_COPY);
        userMessageService.addWikiMessage(userMessage);
        return DocResponseJson.ok();
    }

    @PostMapping("/rename")
    public ResponseJson<Object> rename(WikiPage wikiPage) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        if (StringUtils.isBlank(wikiPage.getName())) {
            return DocResponseJson.warn("标题不能为空！");
        }
        if (StringUtils.isBlank(wikiPage.getId() + "")) {
            return DocResponseJson.warn("不能为新建的文档改名！");
        }
        Long pageId = wikiPage.getId();
        Long spaceId = wikiPage.getSpaceId();
        WikiPage wikiPageSel = wikiPageService.getById(pageId);
        // 编辑权限判断
        WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
        String canEdit = wikiPageAuthService.canEdit(wikiSpaceSel, wikiPageSel.getEditType(), wikiPageSel.getId(), currentUser.getUserId());
        if (canEdit != null) {
            return DocResponseJson.warn(canEdit);
        }
        spaceId = wikiPageSel.getSpaceId();
        WikiPage oldWikiPage = wikiPageService.getById(pageId);
        oldWikiPage.setName(wikiPage.getName());
        wikiPage.setUpdateTime(new Date());
        wikiPage.setUpdateUserId(currentUser.getUserId());
        wikiPage.setUpdateUserName(currentUser.getUsername());
        wikiPageService.updateById(oldWikiPage);
        UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
        wrapper.eq("page_id", wikiPage.getId());
        WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
        // 给相关人发送消息
        UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), DocSysType.WIKI, UserMsgType.WIKI_PAGE_UPDATE);
        userMessageService.addWikiMessage(userMessage);
        try {
            // 创建历史记录
            wikiPageHistoryService.saveRecord(spaceId, wikiPage.getId(), pageContent.getContent());
        } catch (ConfirmException e) {
            return DocResponseJson.warn(e.getMessage());
        }
        return DocResponseJson.ok(wikiPage);
    }

    @PostMapping("/unlock")
    public ResponseJson<Object> unlock(Long pageId) {
        String lockKey = CachePrefix.WIKI_LOCK_PAGE + pageId;
        DocUserDetails pageLockUser = CacheUtil.get(lockKey);
        if (pageLockUser != null) {
            DocUserDetails currentUser = DocUserUtil.getCurrentUser();
            if (Objects.equals(pageLockUser.getUserId(), currentUser.getUserId())) {
                CacheUtil.remove(lockKey);
            }
        }
        return DocResponseJson.ok();
    }

    @PostMapping("/lock")
    public ResponseJson<Object> editLock(Long pageId) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        String lockKey = CachePrefix.WIKI_LOCK_PAGE + pageId;
        DocUserDetails pageLockUser = CacheUtil.get(lockKey);
        if (pageLockUser != null) {
            if (!Objects.equals(pageLockUser.getUserId(), currentUser.getUserId())) {
                return DocResponseJson.warn("当前页面正在被：" + pageLockUser.getUsername() + " 编辑");
            }
        }
        CacheUtil.put(lockKey, new DocUserDetails(currentUser.getUserId(), currentUser.getUsername()));
        return DocResponseJson.ok();
    }

    @PostMapping("/searchByEs")
    public ResponseJson<Object> searchByEs(SearchByEsParam param) {
        param.setNewsType(1);
        return this.news(param);
    }

    @PostMapping("/download")
    public ResponseJson<Object> download(Long pageId, String content, HttpServletRequest request, HttpServletResponse response) {
        return wikiPageWebService.download(pageId, content, request, response);
    }

    @PostMapping("/news")
    public ResponseJson<Object> news(SearchByEsParam param) {
        // 空间不是自己的
        Map<Long, WikiSpace> wikiSpaceMap = this.getCanVisitWikiSpace(param.getSpaceId());
        if (wikiSpaceMap.isEmpty()) {
            return DocResponseJson.ok();
        }
        param.setSpaceIds(new ArrayList<>(wikiSpaceMap.keySet()));
        String keywords = param.getKeywords();
        if (StringUtils.isNotBlank(keywords)) {
            param.setKeywords("%" + keywords + "%");
        }
        // 分页查询
        List<SpaceNewsVo> spaceNewsVoList = wikiPageContentMapper.getNewsList(param);
        if (spaceNewsVoList == null || spaceNewsVoList.isEmpty()) {
            return DocResponseJson.ok();
        }
        spaceNewsVoList.forEach(val -> {
            val.setSpaceName(wikiSpaceMap.get(val.getSpaceId()).getName());
            String preview = val.getPreviewContent();
            if (preview != null) {
                if (preview.length() > 200) {
                    preview = preview.substring(0, 200);
                }
                if (keywords != null) {
                    preview = StringUtils.replaceIgnoreCase(preview, keywords, "<span style=\"color:red\">" + keywords + "</span>");
                }
            }
            val.setPreviewContent(preview);
            String pageTitle = val.getPageTitle();
            if (pageTitle != null && keywords != null) {
                pageTitle = StringUtils.replaceIgnoreCase(pageTitle, keywords, "<span style=\"color:red\">" + keywords + "</span>");
            }
            val.setPageTitle(pageTitle);
        });
        return DocResponseJson.ok(spaceNewsVoList);
    }

    private Map<Long, WikiSpace> getCanVisitWikiSpace(Long spaceId) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        List<WikiSpace> spaceList;
        // 空间不是自己的
        if (spaceId == null || spaceId <= 0) {
            QueryWrapper<WikiSpace> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("del_flag", 0);
            queryWrapper.ne("type", SpaceType.privateSpace);
            spaceList = wikiSpaceService.list(queryWrapper);
        } else {
            WikiSpace wikiSpaceSel = wikiSpaceService.getById(spaceId);
            if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
                return Collections.emptyMap();
            }
            spaceList = Collections.singletonList(wikiSpaceSel);
        }
        return spaceList.stream().collect(Collectors.toMap(WikiSpace::getId, val -> val));
    }

    private void setChildren(Map<Long, List<WikiPageVo>> listMap, List<WikiPageVo> nodePageList, String path) {
        if (nodePageList == null || listMap == null) {
            return;
        }
        for (WikiPageVo page : nodePageList) {
            String nowPath = path + "/" + page.getName();
            page.setPath(nowPath);
            List<WikiPageVo> wikiPageVos = listMap.get(page.getId());
            if (CollectionUtils.isNotEmpty(wikiPageVos)) {
                wikiPageVos = wikiPageVos.stream().sorted(Comparator.comparingInt(WikiPageVo::getSeqNo)).collect(Collectors.toList());
                page.setChildren(wikiPageVos);
                this.setChildren(listMap, wikiPageVos, nowPath);
            }
        }
    }
}

