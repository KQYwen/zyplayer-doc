package com.zyplayer.doc.wiki.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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
//
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPageFile wikiPageFile) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long id = wikiPageFile.getId();
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

