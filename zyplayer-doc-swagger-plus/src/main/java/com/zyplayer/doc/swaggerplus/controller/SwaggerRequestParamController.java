package com.zyplayer.doc.swaggerplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerDoc;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerRequestParam;
import com.zyplayer.doc.data.service.manage.SwaggerRequestParamService;
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
@RequestMapping("/doc-swagger/request-param")
public class SwaggerRequestParamController {
	private static Logger logger = LoggerFactory.getLogger(SwaggerRequestParamController.class);
	
	@Resource
	private SwaggerRequestParamService swaggerRequestParamService;
	
	/**
	 * 获取所有的请求参数
	 *
	 * @return 请求参数
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/query")
	public ResponseJson<SwaggerRequestParam> query(String docUrl) {
		QueryWrapper<SwaggerRequestParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("doc_url", docUrl);
		SwaggerRequestParam requestParam = swaggerRequestParamService.getOne(queryWrapper);
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
	public ResponseJson<List<SwaggerDoc>> update(SwaggerRequestParam swaggerRequestParam) {
		QueryWrapper<SwaggerRequestParam> updateWrapper = new QueryWrapper<>();
		updateWrapper.eq("doc_url", swaggerRequestParam.getDocUrl());
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		swaggerRequestParam.setYn(1);
		swaggerRequestParam.setCreateTime(new Date());
		swaggerRequestParam.setCreateUserId(currentUser.getUserId());
		swaggerRequestParam.setCreateUserName(currentUser.getUsername());
		swaggerRequestParamService.update(swaggerRequestParam, updateWrapper);
		return DocResponseJson.ok();
	}
}
