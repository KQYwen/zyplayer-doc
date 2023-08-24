package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageContentMapper;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageMapper;
import com.zyplayer.doc.data.repository.manage.param.SearchByEsParam;
import com.zyplayer.doc.data.repository.manage.vo.SpaceNewsVo;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;
import com.zyplayer.doc.data.repository.manage.vo.WikiTemplateTagVo;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.*;
import com.zyplayer.doc.data.utils.CachePrefix;
import com.zyplayer.doc.data.utils.CacheUtil;
import com.zyplayer.doc.wiki.controller.vo.WikiPageContentVo;
import com.zyplayer.doc.wiki.controller.vo.WikiPageVo;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.service.WikiPageUploadService;
import com.zyplayer.doc.wiki.service.common.WikiPageAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
@RequestMapping("/zyplayer-doc-wiki/template")
@RequiredArgsConstructor
public class WikiPageTemplateController {

    private final WikiPageService wikiPageService;
    private final WikiPageContentService wikiPageContentService;
    private final WikiPageUploadService wikipageUploadService;
    private final WikiPageTemplateService wikiPageTemplateService;
    private final WikiPageFileService wikiPageFileService;


    @PostMapping("/add")
    public ResponseJson<Object> addTemplate(WikiPageTemplate wikiPageTemplate) {
        WikiPageTemplate exist = wikiPageTemplateService.getWikiPageTemplateBySpaceAndPage(wikiPageTemplate.getSpaceId(), wikiPageTemplate.getPageId());
        if (null == exist) {
            DocUserDetails currentUser = DocUserUtil.getCurrentUser();
            if (wikiPageTemplate.getTagName().equals("")) {
                wikiPageTemplate.setTagName("无标签");
            }
            wikiPageTemplate.setCreated(new Date());
            wikiPageTemplate.setCreateUser(currentUser.getUsername());
            wikiPageTemplate.setCreateUserId(currentUser.getUserId());
            wikiPageTemplateService.save(wikiPageTemplate);
        } else {
            exist.setTagName(wikiPageTemplate.getTagName());
            exist.setShareStatus(wikiPageTemplate.getShareStatus());
            wikiPageTemplateService.updateById(exist);
        }
        return DocResponseJson.ok();
    }

    @PostMapping("/allTags")
    public ResponseJson<Object> allTags(boolean open) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();

        List<WikiTemplateTagVo> allTags = wikiPageTemplateService.getAllTags(currentUser.getUserId(),open);
        return DocResponseJson.ok(allTags);
    }

    @PostMapping("/filterAll")
    public ResponseJson<Object> filterAll(String name, boolean open, HttpServletRequest request, Long pageNum) {
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        List tagList = new ArrayList();
        Set<Map.Entry<String, String[]>> entries = request.getParameterMap().entrySet();
        entries.forEach(param -> {
            if (param.getKey().contains("].tagName") && !"".equals(param.getValue()[0])) {
                tagList.add(param.getValue()[0]);
            }
        });
        if (tagList.size() < 1) {
            tagList.add("");
        }
        List<WikiPageTemplateInfoVo> wikiPageTemplateInfoVos = wikiPageTemplateService.filterAll(currentUser.getUserId(), name, open, tagList, pageNum);
        Long total = wikiPageTemplateService.total(currentUser.getUserId(), name, open, tagList);
        DocResponseJson<Object> ok = DocResponseJson.ok(wikiPageTemplateInfoVos);
        ok.setTotal(total);
        return ok;
    }

    @PostMapping("/use")
    public ResponseJson<Object> use(Long spaceId, Long parentId, String templateId) {
        WikiPageTemplate template = wikiPageTemplateService.getById(templateId);
        WikiPage wikiTemplatePage = wikiPageService.getById(template.getPageId());
        WikiPage wikiPage = new WikiPage();
        wikiPage.setParentId(parentId);
        wikiPage.setSpaceId(spaceId);
        wikiPage.setEditorType(wikiTemplatePage.getEditorType());
        wikiPage.setName(wikiTemplatePage.getName() + "副本");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("page_id", wikiTemplatePage.getId());
        WikiPageContent pageContent = wikiPageContentService.getOne(queryWrapper);
        UpdateWrapper<WikiPageFile> wrapperFile = new UpdateWrapper<>();
        wrapperFile.eq("page_id", wikiTemplatePage.getId());
        List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
        String content = pageContent.getContent();
        for (WikiPageFile pageFile : pageFiles) {
            pageFile.setId(null);
            String uuid = IdUtil.simpleUUID();
            content.replace(pageFile.getUuid(),uuid);
            pageFile.setUuid(uuid);
        }
        Object info = wikipageUploadService.update(wikiPage, content, content);
        if (null != info) {
            if (info instanceof WikiPage) {
                for (WikiPageFile pageFile : pageFiles) {
                    pageFile.setPageId(((WikiPage)info).getId());
                    wikiPageFileService.save(pageFile);
                }
                return DocResponseJson.ok(info);
            }
            DocResponseJson.warn((String) info);
        }
        return DocResponseJson.ok("状态异常");
    }

}

