package com.zyplayer.doc.api.controller;


import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomFolder;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomFolderService;
import org.apache.commons.lang3.StringUtils;
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
@AuthMan
@Controller
@RequestMapping("/api-custom-folder")
public class ApiCustomFolderController {
	
	@Resource
	ApiCustomFolderService apiCustomFolderService;
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	
	/**
	 * 1. 新增文件夹
	 * 2. 修改文件夹名称说明等
	 * 3. 修改父文件夹
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年12月22日
	 */
	@ResponseBody
	@PostMapping(value = "/add")
	public ResponseJson<Object> add(ApiCustomFolder apiCustomFolder) {
		apiCustomFolderService.addFolder(apiCustomFolder);
		return DocResponseJson.ok();
	}
	
	/**
	 * 修改文件夹
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年12月22日
	 */
	@ResponseBody
	@PostMapping(value = "/update")
	public ResponseJson<Object> update(ApiCustomFolder apiCustomFolder) {
		// 参数未传不处理
		if (apiCustomFolder.getId() == null || StringUtils.isBlank(apiCustomFolder.getFolderName())) {
			return DocResponseJson.ok();
		}
		ApiCustomFolder apiCustomFolderSel = apiCustomFolderService.getById(apiCustomFolder.getId());
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolderSel.getDocId());
		// 执行修改
		ApiCustomFolder folderUp = new ApiCustomFolder();
		folderUp.setId(apiCustomFolder.getId());
		folderUp.setFolderName(apiCustomFolder.getFolderName());
		apiCustomFolderService.updateById(folderUp);
		return DocResponseJson.ok();
	}
	
	/**
	 * 删除文件夹
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年12月22日
	 */
	@ResponseBody
	@PostMapping(value = "/delete")
	public ResponseJson<Object> delete(Long id) {
		ApiCustomFolder apiCustomFolderSel = apiCustomFolderService.getById(id);
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolderSel.getDocId());
		apiCustomFolderService.deleteFolder(id);
		return DocResponseJson.ok();
	}
}
