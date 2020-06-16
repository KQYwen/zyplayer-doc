package com.zyplayer.doc.wiki.service;

import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WikiPageAuthService {
	
	/**
	 * 是否具有编辑权限
	 * @param wikiSpaceSel
	 * @param editType
	 * @param pageId
	 * @param currentUserId
	 * @return
	 */
	public String canEdit(WikiSpace wikiSpaceSel, Integer editType, Long pageId, Long currentUserId) {
		if (wikiSpaceSel == null || Objects.equals(editType, 1)) {
			return "当前页面不允许编辑！";
		}
		// 私人空间不允许调用接口获取文章
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			return "您没有权限修改该空间的文章！";
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.EDIT_PAGE.getName(), DocAuthConst.WIKI + pageId);
			if (!pageAuth) {
				return "您没有修改该文章的权限！";
			}
		}
		return null;
	}
	
	/**
	 * 是否具有权限编辑权限
	 * @param wikiSpaceSel
	 * @param pageId
	 * @param currentUserId
	 * @return
	 */
	public String canConfigAuth(WikiSpace wikiSpaceSel, Long pageId, Long currentUserId) {
		if (!SpaceType.isPersonal(wikiSpaceSel.getType())) {
			return "只有个人空间才可以编辑权限";
		}
		if (!Objects.equals(currentUserId, wikiSpaceSel.getCreateUserId())) {
			if (!DocUserUtil.haveCustomAuth(WikiAuthType.PAGE_AUTH_MANAGE.getName(), DocAuthConst.WIKI + pageId)) {
				return "您不是创建人或没有权限修改";
			}
		}
		return null;
	}
	
	/**
	 * 是否具有附件上传权限
	 * @param wikiSpaceSel
	 * @param pageId
	 * @param currentUserId
	 * @return
	 */
	public String canUploadFile(WikiSpace wikiSpaceSel, Long pageId, Long currentUserId) {
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			return "您没有该空间的文件上传权限！";
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.PAGE_FILE_UPLOAD.getName(), DocAuthConst.WIKI + pageId);
			if (!pageAuth) {
				return "您没有修改该文章附件的权限！";
			}
		}
		return null;
	}
	
	/**
	 * 是否具有删除权限
	 * @param wikiSpaceSel
	 * @param editType
	 * @param pageId
	 * @param currentUserId
	 * @return
	 */
	public String canDelete(WikiSpace wikiSpaceSel, Integer editType, Long pageId, Long currentUserId) {
		if (wikiSpaceSel == null || Objects.equals(editType, 1)) {
			return "当前页面不允许编辑！";
		}
		// 私人空间不允许调用接口获取文章
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			return "您没有权限修改该空间的文章！";
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.DELETE_PAGE.getName(), DocAuthConst.WIKI + pageId);
			if (!pageAuth) {
				return "您没有删除该文章的权限！";
			}
		}
		return null;
	}
}
