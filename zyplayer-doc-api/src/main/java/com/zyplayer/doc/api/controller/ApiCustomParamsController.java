package com.zyplayer.doc.api.controller;


import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.ApiCustomRequest;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * 自建接口文档 前端控制器
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-12-22
 */
@Controller
@RequestMapping("/api-custom-params")
public class ApiCustomParamsController {
	
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	@Resource
	ApiCustomRequestService apiCustomRequestService;
	
	/**
	 * 1. 新增接口
	 * 2. 修改接口名等
	 * 3. 修改父文件夹
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年12月22日
	 */
	@ResponseBody
	@PostMapping(value = "/add")
	public ResponseJson<Object> add(ApiCustomRequest apiCustomRequest) {
		ApiCustomRequest requestSaved = apiCustomRequestService.addRequest(apiCustomRequest);
		return DocResponseJson.ok(requestSaved);
	}
	
	/**
	 * 自定义接口详情
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2022年01月05日
	 */
	@ResponseBody
	@PostMapping(value = "/detail")
	public ResponseJson<Object> detail(Long id) {
		ApiCustomRequest apiCustomRequest = apiCustomRequestService.getById(id);
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomRequest.getDocId());
		return DocResponseJson.ok(apiCustomRequest);
	}
	
	/**
	 * 删除接口
	 *
	 * @author 暮光：城中城
	 * @since 2022年01月05日
	 */
	@ResponseBody
	@PostMapping(value = "/delete")
	public ResponseJson<Object> delete(Long id) {
		ApiCustomRequest apiCustomRequest = apiCustomRequestService.getById(id);
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomRequest.getDocId());
		// 修改为删除状态
		ApiCustomRequest requestUp = new ApiCustomRequest();
		requestUp.setId(id);
		requestUp.setYn(0);
		apiCustomRequestService.updateById(requestUp);
		return DocResponseJson.ok();
	}
}
