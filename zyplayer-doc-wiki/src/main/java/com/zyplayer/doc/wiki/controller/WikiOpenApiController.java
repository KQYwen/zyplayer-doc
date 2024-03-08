package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.enums.PageFileSource;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageContent;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageContentMapper;
import com.zyplayer.doc.data.repository.manage.param.SearchByEsParam;
import com.zyplayer.doc.data.repository.manage.vo.SpaceNewsVo;
import com.zyplayer.doc.data.service.manage.WikiPageContentService;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.controller.vo.WikiPageContentVo;
import com.zyplayer.doc.wiki.controller.vo.WikiPageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@Slf4j
@RestController
@RequestMapping("/zyplayer-doc-wiki/open-api")
@RequiredArgsConstructor
public class WikiOpenApiController {

    private final WikiPageService wikiPageService;
    private final WikiSpaceService wikiSpaceService;
    private final WikiPageContentService wikiPageContentService;
    private final WikiPageFileService wikiPageFileService;
    private final WikiPageContentMapper wikiPageContentMapper;

    @PostMapping("/space/info")
    public ResponseJson<WikiSpace> spaceInfo(String space) {
        WikiSpace wikiSpace = this.getWikiSpace(space);
        if (wikiSpace == null) {
            return DocResponseJson.warn("未找到该文档");
        }
        return DocResponseJson.ok(wikiSpace);
    }

    @PostMapping("/page/news")
    public ResponseJson<List<WikiPageVo>> news(SearchByEsParam param, String space) {
        WikiSpace wikiSpace = this.getWikiSpace(space);
        if (wikiSpace == null) {
            return DocResponseJson.warn("未找到该文档");
        }
        String keywords = param.getKeywords();
        if (StringUtils.isNotBlank(keywords)) {
            param.setKeywords("%" + keywords + "%");
        }
        // 分页查询
        param.setSpaceIds(Collections.singletonList(wikiSpace.getId()));
        List<SpaceNewsVo> spaceNewsVoList = wikiPageContentMapper.getNewsList(param);
        if (CollectionUtils.isNotEmpty(spaceNewsVoList)) {
            spaceNewsVoList.forEach(val -> {
                val.setSpace(wikiSpace.getUuid());
                val.setSpaceName(wikiSpace.getName());
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
        }
        return DocResponseJson.ok(spaceNewsVoList);
    }

    @PostMapping("/page/list")
    public ResponseJson<List<WikiPageVo>> list(String space) {
        WikiSpace wikiSpace = this.getWikiSpace(space);
        if (wikiSpace == null) {
            return DocResponseJson.warn("未找到该文档");
        }
        QueryWrapper<WikiPage> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", 0);
        wrapper.eq("space_id", wikiSpace.getId());
        List<WikiPage> wikiPageList = wikiPageService.list(wrapper);
        if (CollectionUtils.isEmpty(wikiPageList)) {
            return DocResponseJson.ok();
        }
        Map<Long, List<WikiPageVo>> listMap = wikiPageList.stream().map(WikiPageVo::new).collect(Collectors.groupingBy(WikiPageVo::getParentId));
        List<WikiPageVo> nodePageList = listMap.get(0L);
        if (CollectionUtils.isNotEmpty(nodePageList)) {
            nodePageList = nodePageList.stream().sorted(Comparator.comparingInt(WikiPageVo::getSeqNo)).collect(Collectors.toList());
            this.setChildren(listMap, nodePageList);
        }
        return DocResponseJson.ok(nodePageList);
    }

    @PostMapping("/page/detail")
    public ResponseJson<WikiPageContentVo> detail(String space, Long pageId) {
        WikiSpace wikiSpace = this.getWikiSpace(space);
        if (wikiSpace == null) {
            return DocResponseJson.warn("未找到该文档");
        }
        WikiPage wikiPageSel = wikiPageService.getById(pageId);
        // 不存在或不属于该空间
        if (wikiPageSel == null || !Objects.equals(wikiPageSel.getSpaceId(), wikiSpace.getId())) {
            return DocResponseJson.warn("未找到该文档");
        }
        LambdaQueryWrapper<WikiPageContent> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WikiPageContent::getPageId, pageId);
        WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
        LambdaQueryWrapper<WikiPageFile> wrapperFile = new LambdaQueryWrapper<>();
        wrapperFile.eq(WikiPageFile::getPageId, pageId);
        wrapperFile.eq(WikiPageFile::getDelFlag, 0);
        wrapperFile.eq(WikiPageFile::getFileSource, PageFileSource.UPLOAD_FILES.getSource());
        List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
        for (WikiPageFile pageFile : pageFiles) {
            pageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + pageFile.getUuid());
        }
        // 高并发下会有覆盖问题，但不重要~
        int viewNum = Optional.ofNullable(wikiPageSel.getViewNum()).orElse(0);
        WikiPage wikiPageUp = new WikiPage();
        wikiPageUp.setId(wikiPageSel.getId());
        wikiPageUp.setViewNum(viewNum + 1);
        wikiPageService.updateById(wikiPageUp);
        // 修改返回值里的查看数+1
        wikiPageSel.setViewNum(viewNum + 1);
        WikiPageContentVo vo = new WikiPageContentVo();
        vo.setWikiPage(wikiPageSel);
        vo.setPageContent(pageContent);
        vo.setFileList(pageFiles);
        return DocResponseJson.ok(vo);
    }

    private void setChildren(Map<Long, List<WikiPageVo>> listMap, List<WikiPageVo> nodePageList) {
        if (nodePageList == null || listMap == null) {
            return;
        }
        for (WikiPageVo page : nodePageList) {
            List<WikiPageVo> wikiPageVos = listMap.get(page.getId());
            if (CollectionUtils.isNotEmpty(wikiPageVos)) {
                wikiPageVos = wikiPageVos.stream().sorted(Comparator.comparingInt(WikiPageVo::getSeqNo)).collect(Collectors.toList());
                page.setChildren(wikiPageVos);
                this.setChildren(listMap, wikiPageVos);
            }
        }
    }

    /**
     * 获取空间信息
     *
     * @param space
     * @return
     */
    private WikiSpace getWikiSpace(String space) {
        QueryWrapper<WikiSpace> wrapperSpace = new QueryWrapper<>();
        wrapperSpace.eq("uuid", space);
        wrapperSpace.eq("del_flag", 0);
        WikiSpace wikiSpace = wikiSpaceService.getOne(wrapperSpace);
        // 不存在或未开放
        if (wikiSpace == null || wikiSpace.getOpenDoc() != 1) {
            return null;
        }
        return wikiSpace;
    }
}

