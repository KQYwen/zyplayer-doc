package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.support.consts.UserMsgSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.UserMessageService;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.service.WikiPageAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/file")
public class WikiPageFileController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageFileController.class);
	
	@Value("${zyplayer.doc.wiki.upload-path:}")
	private String uploadPath;
	
	@Resource
	WikiPageFileService wikiPageFileService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiPageAuthService wikiPageAuthService;
	@Resource
	UserMessageService userMessageService;
	
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
	
	@PostMapping("/delete")
	public ResponseJson<Object> delete(WikiPageFile wikiPageFile) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long id = wikiPageFile.getId();
		if (id == null || id <= 0) {
			return DocResponseJson.warn("需指定删除的附件！");
		}
		WikiPageFile pageFileSel = wikiPageFileService.getById(wikiPageFile.getId());
		WikiPage wikiPageSel = wikiPageService.getById(pageFileSel.getPageId());
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 权限判断
		String canUploadFile = wikiPageAuthService.canUploadFile(wikiSpaceSel, pageFileSel.getPageId(), currentUser.getUserId());
		if (canUploadFile != null) {
			return DocResponseJson.warn(canUploadFile);
		}
		wikiPageFile.setDelFlag(1);
		wikiPageFile.setUpdateUserId(currentUser.getUserId());
		wikiPageFile.setUpdateUserName(currentUser.getUsername());
		wikiPageFile.setUpdateTime(new Date());
		wikiPageFileService.updateById(wikiPageFile);
		// 给相关人发送消息
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, wikiPageSel.getId(), wikiPageSel.getName(), UserMsgSysType.WIKI, UserMsgType.WIKI_PAGE_FILE_DEL);
		userMessage.setAffectUserId(wikiPageSel.getCreateUserId());
		userMessage.setAffectUserName(wikiPageSel.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/wangEditor/upload")
	public Map<String, Object> wangEditorUpload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		Map<String, Object> resultMap = new HashMap<>();
		DocResponseJson docResponseJson = this.uploadFile(wikiPageFile, file);
		if (!docResponseJson.isOk()) {
			resultMap.put("errno", "1");
			resultMap.put("err", docResponseJson.getErrMsg());
		} else {
			resultMap.put("errno", "0");
			resultMap.put("data", new String[]{wikiPageFile.getFileUrl()});
		}
		return resultMap;
	}
	
	@PostMapping("/upload")
	public ResponseJson upload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long pageId = wikiPageFile.getPageId();
		if (pageId == null || pageId <= 0) {
			return DocResponseJson.warn("未指定附件关联的文档");
		}
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 权限判断
		String canUploadFile = wikiPageAuthService.canUploadFile(wikiSpaceSel, wikiPageSel.getId(), currentUser.getUserId());
		if (canUploadFile != null) {
			return DocResponseJson.warn(canUploadFile);
		}
		DocResponseJson docResponseJson = this.uploadFile(wikiPageFile, file);
		if (!docResponseJson.isOk()) {
			return docResponseJson;
		}
		// 给相关人发送消息
		UserMessage userMessage = userMessageService.createUserMessage(currentUser, pageId, wikiPageSel.getName(), UserMsgSysType.WIKI, UserMsgType.WIKI_PAGE_UPLOAD);
		userMessage.setAffectUserId(wikiPageSel.getCreateUserId());
		userMessage.setAffectUserName(wikiPageSel.getCreateUserName());
		userMessageService.addWikiMessage(userMessage);
		return DocResponseJson.ok(wikiPageFile);
	}
	
	/**
	 * 单纯的文件上传方法
	 * @param wikiPageFile
	 * @param file
	 * @return
	 */
	private DocResponseJson uploadFile(WikiPageFile wikiPageFile, MultipartFile file) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		String fileName = file.getOriginalFilename();
		String fileSuffix = "";
		if (fileName != null && fileName.lastIndexOf(".") >= 0) {
			fileSuffix = fileName.substring(fileName.lastIndexOf("."));
		}
		String path = uploadPath + "/" + DateTime.now().toString("yyyy/MM/dd") + "/";
		File newFile = new File(path);
		if (!newFile.exists() && !newFile.mkdirs()) {
			return DocResponseJson.warn("创建文件夹失败");
		}
		String simpleUUID = RandomUtil.simpleUUID();
		path += simpleUUID + fileSuffix;
		newFile = new File(path);
		try {
			file.transferTo(newFile);
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("保存文件失败");
		}
		wikiPageFile.setUuid(simpleUUID);
		wikiPageFile.setFileUrl(path);
		wikiPageFile.setFileName(fileName);
		wikiPageFile.setCreateTime(new Date());
		wikiPageFile.setCreateUserId(currentUser.getUserId());
		wikiPageFile.setCreateUserName(currentUser.getUsername());
		wikiPageFile.setDelFlag(0);
		wikiPageFileService.save(wikiPageFile);
		wikiPageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + wikiPageFile.getUuid());
		return DocResponseJson.ok();
	}
}

