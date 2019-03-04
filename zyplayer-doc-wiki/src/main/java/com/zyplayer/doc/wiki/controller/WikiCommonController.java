package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
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

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@RestController
@RequestMapping("/zyplayer-doc-wiki/common")
public class WikiCommonController {
	private static Logger logger = LoggerFactory.getLogger(WikiCommonController.class);
	
	@Value("${zyplayer.doc.wiki.upload-path:}")
	private String uploadPath;
	
	@Resource
	WikiPageFileService wikiPageFileService;
	
	@PostMapping("/upload")
	public ResponseJson<Object> upload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		//通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		try {
			String fileName = file.getOriginalFilename();
			String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			String path = uploadPath + "/" + DateTime.now().toString("yyyy/MM/dd") + "/";
			File newFile = new File(path);
			if (!newFile.exists()) {
				newFile.mkdir();
			}
			path += RandomUtil.simpleUUID() + fileSuffix;
			newFile = new File(path);
			file.transferTo(newFile);
			wikiPageFile.setFileUrl(path);
			wikiPageFile.setFileName(fileName);
			wikiPageFile.setCreateTime(new Date());
			wikiPageFile.setCreateUserId(currentUser.getUserId());
			wikiPageFile.setCreateUserName(currentUser.getUsername());
			wikiPageFile.setDelFlag(0);
		} catch (Exception e) {
			e.printStackTrace();
			return DocResponseJson.warn("失败");
		}
		wikiPageFileService.save(wikiPageFile);
		return DocResponseJson.ok(wikiPageFile);
	}
}

