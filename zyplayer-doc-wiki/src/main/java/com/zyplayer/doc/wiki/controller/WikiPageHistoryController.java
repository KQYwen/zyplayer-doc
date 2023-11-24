package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageHistory;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.service.manage.WikiPageHistoryService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@Slf4j
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/history")
@RequiredArgsConstructor
public class WikiPageHistoryController {
	
	private final WikiPageHistoryService wikiPageHistoryService;
	private final WikiSpaceService wikiSpaceService;
	private final WikiPageService wikiPageService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageHistory>> list(Long pageId, Integer pageNum) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		// 私人空间
		if (wikiPageSel == null || Objects.equals(wikiPageSel.getDelFlag(), 1)) {
			return DocResponseJson.ok();
		}
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 空间已删除
		if (wikiSpaceSel == null || Objects.equals(wikiSpaceSel.getDelFlag(), 1)) {
			return DocResponseJson.ok();
		}
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
		}
		LambdaQueryWrapper<WikiPageHistory> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WikiPageHistory::getPageId, pageId);
		wrapper.eq(WikiPageHistory::getDelFlag, 0);
		wrapper.orderByDesc(WikiPageHistory::getId);
		wrapper.select(WikiPageHistory::getId, WikiPageHistory::getCreateUserId, WikiPageHistory::getCreateUserName
				, WikiPageHistory::getPageId, WikiPageHistory::getCreateTime);
		IPage<WikiPageHistory> page = new Page<>(pageNum, 30, false);
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
		try {
			byte[] bytes = ZipUtil.unGzip(wikiPageHistory.getContent());
			return DocResponseJson.ok(new String(bytes, StandardCharsets.UTF_8));
		} catch (Exception e) {
			log.error("解析文档内容失败", e);
			return DocResponseJson.warn("解析文档内容失败：" + e.getMessage());
		}
	}
}

