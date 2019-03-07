package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiPageComment;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-02-24
 */
public class WikiPageCommentVo extends WikiPageComment {
	private List<WikiPageComment> commentList;
	
	public List<WikiPageComment> getCommentList() {
		return commentList;
	}
	
	public void setCommentList(List<WikiPageComment> commentList) {
		this.commentList = commentList;
	}
}
