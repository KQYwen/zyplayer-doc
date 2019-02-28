package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageComment;
import com.zyplayer.doc.data.service.manage.WikiPageCommentService;
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
@RequestMapping("/zyplayer-doc-wiki/page/comment")
public class WikiPageCommentController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageCommentController.class);
	
	@Resource
	WikiPageCommentService wikiPageCommentService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageComment>> list(WikiPageComment wikiPageComment) {
		UpdateWrapper<WikiPageComment> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", wikiPageComment.getPageId());
		wrapper.eq(wikiPageComment.getParentId() != null, "parent_id", wikiPageComment.getParentId());
		List<WikiPageComment> authList = wikiPageCommentService.list(wrapper);
		return DocResponseJson.ok(authList);
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageComment pageComment) {
		Long id = pageComment.getId();
		if (id != null && id > 0) {
			wikiPageCommentService.updateById(pageComment);
		} else {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			pageComment.setCreateTime(new Date());
			pageComment.setCreateUserId(currentUser.getUserId());
			wikiPageCommentService.save(pageComment);
		}
		return DocResponseJson.ok();
	}
}

