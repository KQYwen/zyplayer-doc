package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageContent;
import com.zyplayer.doc.data.service.manage.WikiPageContentService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@RestController
@RequestMapping("/zyplayer-doc-wiki/page")
public class WikiPageController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageController.class);
	
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiPageContentService wikiPageContentService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPage>> list(WikiPage wikiPage) {
		// TODO 检查space是否开放访问
		UpdateWrapper<WikiPage> wrapper = new UpdateWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("space_id", wikiPage.getSpaceId());
		wrapper.isNull(wikiPage.getParentId() == null, "parent_id");
		wrapper.eq(wikiPage.getParentId() != null, "parent_id", wikiPage.getParentId());
		List<WikiPage> authList = wikiPageService.list(wrapper);
		return DocResponseJson.ok(authList);
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPage wikiPage, String content) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPageContent pageContent = new WikiPageContent();
		pageContent.setContent(content);
		
		Long id = wikiPage.getId();
		if (id != null && id > 0) {
			wikiPage.setUpdateTime(new Date());
			wikiPage.setUpdateUid(currentUser.getUserId());
			wikiPageService.updateById(wikiPage);
			// 详情
			pageContent.setUpdateTime(new Date());
			pageContent.setUpdateUid(currentUser.getUserId());
			UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
			wrapper.eq("page_id", id);
			wikiPageContentService.update(pageContent, wrapper);
		} else {
			wikiPage.setCreateTime(new Date());
			wikiPage.setCreateUid(currentUser.getUserId());
			wikiPageService.save(wikiPage);
			// 详情
			pageContent.setCreateTime(new Date());
			pageContent.setCreateUid(currentUser.getUserId());
			wikiPageContentService.save(pageContent);
		}
		return DocResponseJson.ok();
	}
}

