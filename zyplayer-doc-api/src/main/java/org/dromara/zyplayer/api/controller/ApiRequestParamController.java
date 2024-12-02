package org.dromara.zyplayer.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.zyplayer.core.annotation.AuthMan;
import org.dromara.zyplayer.core.json.DocResponseJson;
import org.dromara.zyplayer.core.json.ResponseJson;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.ApiDoc;
import org.dromara.zyplayer.data.repository.manage.entity.ApiRequestParam;
import org.dromara.zyplayer.data.service.manage.ApiRequestParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 请求参数控制器
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
@RequestMapping("/doc-api/request-param")
public class ApiRequestParamController {
	private static final Logger logger = LoggerFactory.getLogger(ApiRequestParamController.class);
	
	@Resource
	private ApiRequestParamService apiRequestParamService;
	
	/**
	 * 获取所有的请求参数
	 *
	 * @return 请求参数
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/query")
	public ResponseJson<ApiRequestParam> query(String docUrl) {
		QueryWrapper<ApiRequestParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("doc_url", docUrl);
		ApiRequestParam requestParam = apiRequestParamService.getOne(queryWrapper);
		return DocResponseJson.ok(requestParam);
	}
	
	/**
	 * 修改请求参数
	 *
	 * @return 无
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/update")
	public ResponseJson<List<ApiDoc>> update(ApiRequestParam apiRequestParam) {
		QueryWrapper<ApiRequestParam> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("doc_url", apiRequestParam.getDocUrl());
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		apiRequestParam.setYn(1);
		apiRequestParam.setCreateTime(new Date());
		apiRequestParam.setCreateUserId(currentUser.getUserId());
		apiRequestParam.setCreateUserName(currentUser.getUsername());
		apiRequestParamService.update(apiRequestParam, updateWrapper);
		return DocResponseJson.ok();
	}
}
