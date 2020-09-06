package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageComment;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.support.consts.UserMsgSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.UserMessageService;
import com.zyplayer.doc.data.service.manage.WikiPageCommentService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.controller.vo.WikiPageCommentVo;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
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
@AuthMan
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
	UserMessageService userMessageService;
	@Resource
	Mapper mapper;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageCommentVo>> list(WikiPageComment pageComment) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageComment.getPageId());
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有查看该空间的评论权！");
		}
		UpdateWrapper<WikiPageComment> wrapper = new UpdateWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("page_id", pageComment.getPageId());
		wrapper.eq(pageComment.getParentId() != null, "parent_id", pageComment.getParentId());
		List<WikiPageComment> pageCommentList = wikiPageCommentService.list(wrapper);
		// 取消二级评论，全展示在一级
//		Map<Long, List<WikiPageComment>> listMap = pageCommentList.stream().filter(val -> val.getParentId() != null)
//				.collect(Collectors.groupingBy(WikiPageComment::getParentId));
//		List<WikiPageCommentVo> commentList = pageCommentList.stream().filter(val -> val.getParentId() == null)
//				.map(val -> mapper.map(val, WikiPageCommentVo.class)).collect(Collectors.toList());
//		for (WikiPageCommentVo commentVo : commentList) {
//			commentVo.setCommentList(listMap.get(commentVo.getId()));
//		}
		return DocResponseJson.ok(pageCommentList);
	}
	
	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long id) {
		WikiPageComment pageCommentSel = wikiPageCommentService.getById(id);
		WikiPage wikiPageSel = wikiPageService.getById(pageCommentSel.getPageId());
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (!Objects.equals(pageCommentSel.getCreateUserId(), currentUser.getUserId())) {
			if (!Objects.equals(currentUser.getUserId(), wikiPageSel.getCreateUserId())) {
				return DocResponseJson.warn("只有评论人或页面创建人才有权限删除此评论！");
			}
		}
		WikiPageComment pageComment = new WikiPageComment();
		pageComment.setId(id);
		pageComment.setDelFlag(1);
		wikiPageCommentService.updateById(pageComment);
		// 给相关人发送消息
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), UserMsgSysType.WIKI, UserMsgType.WIKI_PAGE_COMMENT_DEL);
		userMessage.setAffectUserId(wikiPageSel.getCreateUserId());
		userMessage.setAffectUserName(wikiPageSel.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageComment pageComment) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long id = pageComment.getId();
		Long pageId;
		if (id != null && id > 0) {
			WikiPageComment pageCommentSel = wikiPageCommentService.getById(id);
			if (!Objects.equals(pageCommentSel.getCreateUserId(), currentUser.getUserId())) {
				return DocResponseJson.warn("只能修改自己的评论！");
			}
			pageId = pageCommentSel.getPageId();
		} else if (pageComment.getPageId() != null) {
			pageId = pageComment.getPageId();
		} else {
			return DocResponseJson.warn("需指定所属页面！");
		}
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有该空间的评论权！");
		}
		// 空间不是自己的，也没有权限，感觉评论没必要加权限，先去掉
//		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
//			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.COMMENT_PAGE.getName(), pageId);
//			if (!pageAuth) {
//				return DocResponseJson.warn("您没有评论该文章的权限！");
//			}
//		}
		if (id != null && id > 0) {
			pageComment.setDelFlag(0);
			wikiPageCommentService.updateById(pageComment);
		} else {
			pageComment.setCreateTime(new Date());
			pageComment.setCreateUserId(currentUser.getUserId());
			pageComment.setCreateUserName(currentUser.getUsername());
			wikiPageCommentService.save(pageComment);
		}
		// 给相关人发送消息
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), UserMsgSysType.WIKI, UserMsgType.WIKI_PAGE_COMMENT);
		userMessage.setAffectUserId(wikiPageSel.getCreateUserId());
		userMessage.setAffectUserName(wikiPageSel.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
		return DocResponseJson.ok(pageComment);
	}
}

