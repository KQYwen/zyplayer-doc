package com.zyplayer.doc.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.enums.PageFileSource;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.wiki.batch.BatchDocImportManager;
import com.zyplayer.doc.wiki.service.WikiPageFileServiceEx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @author Sh1yu
 * @since 2019年2月17日
 */
@Slf4j
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page/file")
@RequiredArgsConstructor
public class WikiPageFileController {
	
	private final WikiPageFileServiceEx wikiPageFileServiceEx;
	private final BatchDocImportManager batchDocImportManger;
	
	@PostMapping("/delete")
	public ResponseJson<Object> delete(WikiPageFile wikiPageFile) {
		String info = wikiPageFileServiceEx.delete(wikiPageFile);
		if (null != info) {
			return DocResponseJson.warn(info);
		}
		return DocResponseJson.ok();
	}
	
	@PostMapping("/wangEditor/upload")
	public Map<String, Object> wangEditorUpload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		Map<String, Object> resultMap = new HashMap<>();
		wikiPageFile.setFileSource(PageFileSource.PASTE_FILES.getSource());
		DocResponseJson<Object> docResponseJson = wikiPageFileServiceEx.basicUpload(wikiPageFile, file);
		if (!docResponseJson.isOk()) {
			resultMap.put("errno", 1);
			resultMap.put("message", docResponseJson.getErrMsg());
		} else {
			resultMap.put("errno", 0);
			resultMap.put("data", new JSONObject().fluentPut("url", wikiPageFile.getFileUrl()));
		}
		return resultMap;
	}
	
	@PostMapping("/import/upload")
	public ResponseJson importUpload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		return batchDocImportManger.importBatchDoc(wikiPageFile, file);
	}
	
	@PostMapping("/upload")
	public ResponseJson upload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file) {
		wikiPageFile.setFileSource(PageFileSource.UPLOAD_FILES.getSource());
		return wikiPageFileServiceEx.basicUpload(wikiPageFile, file);
	}
}

