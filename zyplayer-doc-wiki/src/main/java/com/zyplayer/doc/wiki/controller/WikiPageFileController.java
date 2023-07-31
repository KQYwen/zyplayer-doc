package com.zyplayer.doc.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.core.annotation.AuthMan;
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


/*	@PostMapping("/list")
	public ResponseJson<List<WikiPageFile>> list(WikiPageFile wikiPageFile) {
		// TODO 检查space是否开放访问
        return DocResponseJson.ok(wikiPageFileServiceEx.list(wikiPageFile));
	}*/

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
        Object result = wikiPageFileServiceEx.basicUpload(wikiPageFile, file);
        DocResponseJson docResponseJson = DocResponseJson.warn("处理失败");
        if (result instanceof WikiPageFile) {
            docResponseJson = DocResponseJson.ok(result);
        } else if (result != null) {
            docResponseJson = DocResponseJson.error((String) result);
        }
        if (!docResponseJson.isOk()) {
            resultMap.put("errno", 1);
            resultMap.put("message", docResponseJson.getErrMsg());
        } else {
            resultMap.put("errno", 0);
            resultMap.put("data", new JSONObject().fluentPut("url", wikiPageFile.getFileUrl()));
        }
        return resultMap;
    }

    @PostMapping("/upload")
    public ResponseJson upload(WikiPageFile wikiPageFile, @RequestParam("files") MultipartFile file, boolean importFlag) {
        if (importFlag) {
            String info = batchDocImportManger.importBatchDoc(wikiPageFile, file);
            if (null == info) {
                return DocResponseJson.ok();
            }
            return DocResponseJson.warn(info);
        }
        Object result = wikiPageFileServiceEx.basicUpload(wikiPageFile, file);
        if (result instanceof WikiPageFile) {
            return DocResponseJson.ok(result);
        } else if (result != null) {
            return DocResponseJson.warn((String) result);
        }
        return DocResponseJson.warn("未知异常");
    }


}

