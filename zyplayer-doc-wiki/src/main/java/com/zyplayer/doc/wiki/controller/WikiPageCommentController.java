package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageComment;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.service.manage.WikiPageCommentService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.controller.vo.WikiPageCommentVo;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageService wikiPageService;
	@Resource
	Mapper mapper;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageCommentVo>> list(WikiPageComment pageComment) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageComment.getPageId());
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (Objects.equals(wikiSpaceSel.getType(), 3) && !currentUser.getUserId().equals(wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有查看该空间的评论权！");
		}
		UpdateWrapper<WikiPageComment> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", pageComment.getPageId());
		wrapper.eq(pageComment.getParentId() != null, "parent_id", pageComment.getParentId());
		List<WikiPageComment> authList = wikiPageCommentService.list(wrapper);
		Map<Long, List<WikiPageComment>> listMap = authList.stream().filter(val -> val.getParentId() != null)
				.collect(Collectors.groupingBy(WikiPageComment::getParentId));
		List<WikiPageCommentVo> commentList = authList.stream().filter(val -> val.getParentId() == null)
				.map(val -> mapper.map(val, WikiPageCommentVo.class)).collect(Collectors.toList());
		for (WikiPageCommentVo commentVo : commentList) {
			commentVo.setCommentList(listMap.get(commentVo.getId()));
		}
		return DocResponseJson.ok(commentList);
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageComment pageComment) {
		Long id = pageComment.getId();
		Long pageId;
		if (id != null && id > 0) {
			WikiPageComment pageCommentSel = wikiPageCommentService.getById(id);
			pageId = pageCommentSel.getPageId();
		} else if (pageComment.getPageId() != null) {
			pageId = pageComment.getPageId();
		} else {
			return DocResponseJson.warn("需指定所属页面！");
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (Objects.equals(wikiSpaceSel.getType(), 3) && !currentUser.getUserId().equals(wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有该空间的评论权！");
		}
		if (id != null && id > 0) {
			wikiPageCommentService.updateById(pageComment);
		} else {
			pageComment.setCreateTime(new Date());
			pageComment.setCreateUserId(currentUser.getUserId());
			pageComment.setCreateUserName(currentUser.getUsername());
			wikiPageCommentService.save(pageComment);
		}
		return DocResponseJson.ok(pageComment);
	}
}

