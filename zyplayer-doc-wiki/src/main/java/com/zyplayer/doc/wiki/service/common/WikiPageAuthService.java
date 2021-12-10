package com.zyplayer.doc.wiki.service.common;

import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.manage.mapper.UserGroupAuthMapper;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * wiki页面权限服务
 *
 * @author 暮光：城中城
 * @since 2020-06-16
 */
@Service
public class WikiPageAuthService {
	
	@Resource
	UserGroupAuthMapper userGroupAuthMapper;
	
	/**
	 * 是否具有编辑权限
	 *
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
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.EDIT_PAGE.getCode(), DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId);
			if (!pageAuth) {
				// 在空间上直接授权了分组的权限，在这个分组里就具有权限
				Long authId = userGroupAuthMapper.haveAuth(wikiSpaceSel.getId(), DocSysType.WIKI.getType(), WikiAuthType.EDIT_PAGE.getType(), currentUserId);
				if (authId == null) {
					return "您没有修改该文章的权限！";
				}
			}
		}
		return null;
	}
	
	/**
	 * 是否具有权限编辑权限
	 *
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
			if (!DocUserUtil.haveCustomAuth(WikiAuthType.PAGE_AUTH_MANAGE.getCode(), DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId)) {
				// 在空间上直接授权了分组的权限，在这个分组里就具有权限
				Long authId = userGroupAuthMapper.haveAuth(wikiSpaceSel.getId(), DocSysType.WIKI.getType(), WikiAuthType.PAGE_AUTH_MANAGE.getType(), currentUserId);
				if (authId == null) {
					return "您不是创建人或没有权限";
				}
			}
		}
		return null;
	}
	
	/**
	 * 是否具有附件上传权限
	 *
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
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.PAGE_FILE_UPLOAD.getCode(), DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId);
			if (!pageAuth) {
				// 在空间上直接授权了分组的权限，在这个分组里就具有权限
				Long authId = userGroupAuthMapper.haveAuth(wikiSpaceSel.getId(), DocSysType.WIKI.getType(), WikiAuthType.PAGE_FILE_UPLOAD.getType(), currentUserId);
				if (authId == null) {
					return "您没有上传该文章附件的权限！";
				}
			}
		}
		return null;
	}
	
	/**
	 * 是否具有附件删除权限
	 *
	 * @param wikiSpaceSel
	 * @param pageId
	 * @param currentUserId
	 * @return
	 */
	public String canDeleteFile(WikiSpace wikiSpaceSel, Long pageId, Long currentUserId) {
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			return "您没有该空间的文件上传权限！";
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUserId, wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.PAGE_FILE_DELETE.getCode(), DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId);
			if (!pageAuth) {
				// 在空间上直接授权了分组的权限，在这个分组里就具有权限
				Long authId = userGroupAuthMapper.haveAuth(wikiSpaceSel.getId(), DocSysType.WIKI.getType(), WikiAuthType.PAGE_FILE_DELETE.getType(), currentUserId);
				if (authId == null) {
					return "您没有删除该文章附件的权限！";
				}
			}
		}
		return null;
	}
	
	/**
	 * 是否具有删除权限
	 *
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
			boolean pageAuth = DocUserUtil.haveCustomAuth(WikiAuthType.DELETE_PAGE.getCode(), DocSysType.WIKI.getType(), DocSysModuleType.Wiki.PAGE.getType(), pageId);
			if (!pageAuth) {
				// 在空间上直接授权了分组的权限，在这个分组里就具有权限
				Long authId = userGroupAuthMapper.haveAuth(wikiSpaceSel.getId(), DocSysType.WIKI.getType(), WikiAuthType.DELETE_PAGE.getType(), currentUserId);
				if (authId == null) {
					return "您没有删除该文章的权限！";
				}
			}
		}
		return null;
	}
}
