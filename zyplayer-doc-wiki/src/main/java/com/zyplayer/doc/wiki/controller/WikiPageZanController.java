package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageZan;
import com.zyplayer.doc.data.service.manage.WikiPageZanService;
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
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/zan")
public class WikiPageZanController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageZanController.class);
	
	@Resource
	WikiPageZanService wikiPageZanService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageZan>> list(WikiPageZan wikiPageZan) {
		UpdateWrapper<WikiPageZan> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", wikiPageZan.getPageId());
		wrapper.eq(wikiPageZan.getCommentId() != null, "comment_id", wikiPageZan.getCommentId());
		List<WikiPageZan> zanList = wikiPageZanService.list(wrapper);
		return DocResponseJson.ok(zanList);
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageZan wikiPageZan) {
		wikiPageZanService.zanPage(wikiPageZan);
		return DocResponseJson.ok();
	}
}

