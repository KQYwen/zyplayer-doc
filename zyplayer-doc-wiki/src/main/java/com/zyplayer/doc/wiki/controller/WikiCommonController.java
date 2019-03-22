package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.wiki.framework.consts.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Optional;

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
		String fileName = file.getOriginalFilename();
		String fileSuffix = "";
		if (fileName != null && fileName.lastIndexOf(".") >= 0) {
			fileSuffix = fileName.substring(fileName.lastIndexOf("."));
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
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
		return DocResponseJson.ok(wikiPageFile);
	}
	
	@GetMapping("/file")
	public ResponseJson<Object> file(HttpServletResponse response, String uuid) {
		if (StringUtils.isBlank(uuid)) {
			return DocResponseJson.warn("请指定文件ID");
		}
		UpdateWrapper<WikiPageFile> wrapperFile = new UpdateWrapper<>();
		wrapperFile.eq("uuid", uuid);
		WikiPageFile pageFile = wikiPageFileService.getOne(wrapperFile);
		if (pageFile == null) {
			return DocResponseJson.warn("未找到指定文件");
		}
		try {
			String fileName = Optional.ofNullable(pageFile.getFileName()).orElse("");
			File file = new File(pageFile.getFileUrl());
			String fileSuffix = "";
			if (fileName.lastIndexOf(".") >= 0) {
				fileSuffix = fileName.substring(fileName.lastIndexOf("."));
			}
			String mimeStr = Optional.ofNullable(Const.mimeMap.get(fileSuffix)).orElse("text/plain");
			response.setContentType(mimeStr);
			response.setHeader("Content-disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//			response.setHeader("Content-disposition", "inline;filename=" + fileName);
//			response.setHeader("Content-Disposition", "inline; fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
			inputStream.close();
			return null;
		} catch (Exception e) {
			logger.info("失败：{}", e.getMessage());
		}
		return DocResponseJson.warn("获取文件失败");
	}
}

