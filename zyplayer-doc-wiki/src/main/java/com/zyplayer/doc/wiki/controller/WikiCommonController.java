package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageFileMapper;
import com.zyplayer.doc.data.service.manage.UserInfoService;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.framework.consts.Const;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
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
import java.util.*;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/common")
public class WikiCommonController {
	private static Logger logger = LoggerFactory.getLogger(WikiCommonController.class);
	
	@Value("${zyplayer.doc.wiki.upload-path:}")
	private String uploadPath;
	
	@Resource
	WikiPageFileService wikiPageFileService;
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	WikiPageFileMapper wikiPageFileMapper;
	
	@PostMapping("/user/base")
	public ResponseJson<Object> userBaseInfo(String search) {
		if (StringUtils.isBlank(search)) {
			return DocResponseJson.ok();
		}
		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("user_name", search).or().like("user_no", search)
				.or().like("email", search);
		queryWrapper.select("id", "user_name");
		List<UserInfo> userInfoList = userInfoService.list(queryWrapper);
		return DocResponseJson.ok(userInfoList);
	}
	
	@PostMapping("/wangEditor/upload")
	public Map<String, Object> wangEditorUpload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		this.upload(wikiPageFile, file);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("errno", "0");
		resultMap.put("data", new String[]{wikiPageFile.getFileUrl()});
		return resultMap;
	}
	
	@PostMapping("/upload")
	public ResponseJson<Object> upload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long pageId = wikiPageFile.getPageId();
		if (pageId != null && pageId > 0) {
			WikiPage wikiPageSel = wikiPageService.getById(pageId);
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
			// 私人空间
			if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有该空间的文件上传权限！");
			}
			// 空间不是自己的，也没有权限
			if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				boolean pageAuth = DocUserUtil.havePageAuth(WikiAuthType.PAGE_FILE_UPLOAD.getName(), pageId);
				if (!pageAuth) {
					return DocResponseJson.warn("您没有修改该文章附件的权限！");
				}
			}
		}
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
		// 未登录访问文件，需要判断是否是开放空间的文件
		Long pageId = Optional.ofNullable(pageFile.getPageId()).orElse(0L);
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (pageId > 0 && currentUser == null) {
			WikiPage wikiPage = wikiPageService.getById(pageId);
			WikiSpace wikiSpace = wikiSpaceService.getById(wikiPage.getSpaceId());
			if (wikiSpace.getOpenDoc() == 0) {
				return DocResponseJson.warn("登陆后才可访问此文件");
			}
		}
		// 增加下载次数
		wikiPageFileMapper.addDownloadNum(pageFile.getId());
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

