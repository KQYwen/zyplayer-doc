package com.zyplayer.doc.api.controller;


import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.service.manage.ApiCustomFolderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * 自建接口文档分组 前端控制器
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-12-22
 */
@Controller
@RequestMapping("/api-custom-folder")
public class ApiCustomFolderController {
	
	@Resource
	ApiCustomFolderService apiCustomFolderService;
	
	/**
	 * 获取所有的文档地址
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/add")
	public ResponseJson<Object> add(Long docId, Long parentFolderId) {
		return DocResponseJson.ok();
	}
}
