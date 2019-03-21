package com.zyplayer.doc.wiki.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/file")
public class WikiPageFileController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageFileController.class);
	
	@Resource
	WikiPageFileService wikiPageFileService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageService wikiPageService;
	
//	@PostMapping("/list")
//	public ResponseJson<List<WikiPageFile>> list(WikiPageFile wikiPageFile) {
//		// TODO 检查space是否开放访问
//		UpdateWrapper<WikiPageFile> wrapper = new UpdateWrapper<>();
//		wrapper.eq("del_flag", 0);
//		wrapper.eq("page_id", wikiPageFile.getPageId());
//		List<WikiPageFile> fileList = wikiPageFileService.list(wrapper);
//		for (WikiPageFile pageFile : fileList) {
//			pageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + pageFile.getUuid());
//		}
//		return DocResponseJson.ok(fileList);
//	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageFile wikiPageFile) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long id = wikiPageFile.getId();
		Long pageId;
		if (id != null && id > 0) {
			WikiPageFile pageFileSel = wikiPageFileService.getById(wikiPageFile.getId());
			pageId = pageFileSel.getPageId();
		} else if (wikiPageFile.getPageId() != null) {
			pageId = wikiPageFile.getPageId();
		} else {
			return DocResponseJson.warn("需指定修改文件的所属页面！");
		}
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (Objects.equals(wikiSpaceSel.getType(), 3) && !currentUser.getUserId().equals(wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有该空间的文件上传权限！");
		}
		if (id != null && id > 0) {
			wikiPageFile.setUpdateUserId(currentUser.getUserId());
			wikiPageFile.setUpdateUserName(currentUser.getUsername());
			wikiPageFile.setUpdateTime(new Date());
			wikiPageFileService.updateById(wikiPageFile);
		} else {
			wikiPageFile.setCreateTime(new Date());
			wikiPageFile.setCreateUserId(currentUser.getUserId());
			wikiPageFile.setCreateUserName(currentUser.getUsername());
			wikiPageFileService.save(wikiPageFile);
		}
		return DocResponseJson.ok();
	}
}

