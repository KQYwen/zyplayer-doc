package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.aspect.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.service.manage.*;
import com.zyplayer.doc.wiki.controller.vo.WikiPageContentVo;
import com.zyplayer.doc.wiki.controller.vo.WikiPageVo;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page")
public class WikiPageController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageController.class);
	
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiPageContentService wikiPageContentService;
	@Resource
	WikiPageFileService wikiPageFileService;
	@Resource
	WikiPageZanService wikiPageZanService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	Mapper mapper;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageVo>> list(WikiPage wikiPage) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章列表！");
		}
		UpdateWrapper<WikiPage> wrapper = new UpdateWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("space_id", wikiPage.getSpaceId());
		wrapper.eq(wikiPage.getParentId() != null, "parent_id", wikiPage.getParentId());
		List<WikiPage> wikiPageList = wikiPageService.list(wrapper);
		Map<Long, List<WikiPageVo>> listMap = wikiPageList.stream().map(val -> mapper.map(val, WikiPageVo.class)).collect(Collectors.groupingBy(WikiPageVo::getParentId));
		List<WikiPageVo> nodePageList;
		if (wikiPage.getParentId() == null) {
			nodePageList = listMap.get(0L);
			this.setChildren(listMap, nodePageList, "");
		} else {
			nodePageList = listMap.get(wikiPage.getParentId());
		}
		return DocResponseJson.ok(nodePageList);
	}
	
	@PostMapping("/detail")
	public ResponseJson<WikiPageContentVo> detail(WikiPage wikiPage) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
		}
		UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", wikiPage.getId());
		WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
		
		UpdateWrapper<WikiPageFile> wrapperFile = new UpdateWrapper<>();
		wrapperFile.eq("page_id", wikiPage.getId());
		wrapperFile.eq("del_flag", 0);
		List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
		for (WikiPageFile pageFile : pageFiles) {
			pageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + pageFile.getUuid());
		}
		UpdateWrapper<WikiPageZan> wrapperZan = new UpdateWrapper<>();
		wrapperZan.eq("page_id", wikiPage.getId());
		wrapperZan.eq("create_user_id", currentUser.getUserId());
		wrapperZan.eq("yn", 1);
		WikiPageZan pageZan = wikiPageZanService.getOne(wrapperZan);
		WikiPageContentVo vo = new WikiPageContentVo();
		vo.setWikiPage(wikiPageSel);
		vo.setPageContent(pageContent);
		vo.setFileList(pageFiles);
		vo.setSelfZan((pageZan != null) ? 1 : 0);
		// 高并发下会有覆盖问题，但不重要~
		Integer viewNum =  Optional.ofNullable(wikiPageSel.getViewNum()).orElse(0);
		WikiPage wikiPageUp = new WikiPage();
		wikiPageUp.setId(wikiPageSel.getId());
		wikiPageUp.setViewNum(viewNum + 1);
		wikiPageService.updateById(wikiPageUp);
		// 修改返回值里的查看数+1
		wikiPageSel.setViewNum(viewNum + 1);
		return DocResponseJson.ok(vo);
	}
	
	@PostMapping("/changeParent")
	public ResponseJson<Object> changeParent(WikiPage wikiPage) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long id = wikiPage.getId();
		WikiPage wikiPageSel = wikiPageService.getById(id);
		if (wikiPageSel == null || Objects.equals(wikiPageSel.getEditType(), 1)) {
			return DocResponseJson.warn("当前页面不允许编辑！");
		}
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间不允许调用接口获取文章
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限修改该空间的文章！");
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.havePageAuth(WikiAuthType.EDIT_PAGE.getName(), id);
			if (!pageAuth) {
				return DocResponseJson.warn("您没有修改该文章的权限！");
			}
		}
		WikiPage wikiPageUp = new WikiPage();
		wikiPageUp.setId(wikiPage.getId());
		wikiPageUp.setParentId(wikiPage.getParentId());
		wikiPageUp.setUpdateTime(new Date());
		wikiPageUp.setUpdateUserId(currentUser.getUserId());
		wikiPageUp.setUpdateUserName(currentUser.getUsername());
		wikiPageService.updateById(wikiPage);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPage wikiPage, String content) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPageContent pageContent = new WikiPageContent();
		pageContent.setContent(content);
		Integer delFlag = Optional.ofNullable(wikiPage.getDelFlag()).orElse(0);
		if (delFlag == 0 && StringUtils.isBlank(wikiPage.getName())) {
			return DocResponseJson.warn("标题不能为空！");
		}
		Long pageId = wikiPage.getId();
		if (pageId != null && pageId > 0) {
			WikiPage wikiPageSel = wikiPageService.getById(pageId);
			if (wikiPageSel == null || Objects.equals(wikiPageSel.getEditType(), 1)) {
				return DocResponseJson.warn("当前页面不允许编辑！");
			}
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
			// 私人空间不允许调用接口获取文章
			if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有权限修改该空间的文章！");
			}
			// 空间不是自己的，也没有权限
			if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				boolean pageAuth = DocUserUtil.havePageAuth(WikiAuthType.EDIT_PAGE.getName(), pageId);
				if (!pageAuth) {
					return DocResponseJson.warn("您没有修改该文章的权限！");
				}
			}
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
		} else {
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
			// 空间不是自己的
			if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有权限新增该空间的文章！");
			}
			wikiPage.setCreateTime(new Date());
			wikiPage.setCreateUserId(currentUser.getUserId());
			wikiPage.setCreateUserName(currentUser.getUsername());
			wikiPageService.save(wikiPage);
			// 详情
			pageContent.setPageId(wikiPage.getId());
			pageContent.setCreateTime(new Date());
			pageContent.setCreateUserId(currentUser.getUserId());
			pageContent.setCreateUserName(currentUser.getUsername());
			wikiPageContentService.save(pageContent);
		}
		return DocResponseJson.ok(wikiPage);
	}
	
	private void setChildren(Map<Long, List<WikiPageVo>> listMap, List<WikiPageVo> nodePageList, String path) {
		if (nodePageList == null || listMap == null) {
			return;
		}
		for (WikiPageVo page : nodePageList) {
			String nowPath = path + "/" + page.getName();
			page.setPath(nowPath);
			List<WikiPageVo> wikiPageVos = listMap.get(page.getId());
			if (wikiPageVos != null && wikiPageVos.size() > 0) {
				page.setChildren(wikiPageVos);
				this.setChildren(listMap, wikiPageVos, nowPath);
			}
		}
	}
}

