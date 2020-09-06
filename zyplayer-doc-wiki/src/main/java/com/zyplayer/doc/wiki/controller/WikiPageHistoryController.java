package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.repository.support.consts.UserMsgSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.*;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.service.git.GitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/history")
public class WikiPageHistoryController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageHistoryController.class);
	
	@Resource
	WikiPageHistoryService wikiPageHistoryService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageService wikiPageService;
	@Resource
	GitService gitService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageHistory>> list(Long pageId, Integer pageNum) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
		}
		UpdateWrapper<WikiPageHistory> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", pageId);
		wrapper.eq("del_flag", 0);
		IPage<WikiPageHistory> page = new Page<>(pageNum, 20, false);
		wikiPageHistoryService.page(page, wrapper);
		return DocResponseJson.ok(page);
	}
	
	@PostMapping("/detail")
	public ResponseJson<Object> detail(Long id) {
		WikiPageHistory wikiPageHistory = wikiPageHistoryService.getById(id);
		if (wikiPageHistory == null) {
			return DocResponseJson.warn("未找到相关记录");
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(wikiPageHistory.getPageId());
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
		}
		String pageHistory = gitService.getPageHistory(wikiPageHistory.getGitCommitId(), wikiPageHistory.getPageId());
		return DocResponseJson.ok(pageHistory);
	}
}

