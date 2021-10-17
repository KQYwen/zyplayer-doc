package com.zyplayer.doc.swaggerplus.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerDoc;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerGlobalParam;
import com.zyplayer.doc.data.service.manage.SwaggerGlobalParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全局参数控制器
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
@RequestMapping("/doc-swagger/global-param")
public class SwaggerGlobalParamController {
	private static Logger logger = LoggerFactory.getLogger(SwaggerGlobalParamController.class);
	
	@Resource
	private SwaggerGlobalParamService swaggerGlobalParamService;
	
	/**
	 * 获取所有的全局参数
	 *
	 * @return 全局参数列表
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/list")
	public ResponseJson<List<SwaggerGlobalParam>> list() {
		QueryWrapper<SwaggerGlobalParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		List<SwaggerGlobalParam> globalParamList = swaggerGlobalParamService.list(queryWrapper);
		return DocResponseJson.ok(globalParamList);
	}
	
	/**
	 * 修改全局参数
	 *
	 * @return 无
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/update")
	public ResponseJson<List<SwaggerDoc>> update(String globalParam) {
		List<SwaggerGlobalParam> newParamList = JSON.parseArray(globalParam, SwaggerGlobalParam.class);
		QueryWrapper<SwaggerGlobalParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		List<SwaggerGlobalParam> queryParamList = swaggerGlobalParamService.list(queryWrapper);
		List<Long> newIdList = newParamList.stream().map(SwaggerGlobalParam::getId).filter(Objects::nonNull).collect(Collectors.toList());
		List<Long> deletedList = queryParamList.stream().map(SwaggerGlobalParam::getId).filter(id -> !newIdList.contains(id)).collect(Collectors.toList());
		// 删除不存在的
		swaggerGlobalParamService.removeByIds(deletedList);
		// 保存或更新的
		swaggerGlobalParamService.saveOrUpdateBatch(newParamList);
		return DocResponseJson.ok();
	}
}
