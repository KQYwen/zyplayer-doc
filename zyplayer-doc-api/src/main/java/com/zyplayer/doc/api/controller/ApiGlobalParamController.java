package com.zyplayer.doc.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.repository.manage.entity.ApiGlobalParam;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiGlobalParamService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 全局参数控制器
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
@RequestMapping("/doc-api/global-param")
public class ApiGlobalParamController {
	private static Logger logger = LoggerFactory.getLogger(ApiGlobalParamController.class);
	
	@Resource
	private ApiGlobalParamService apiGlobalParamService;
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	
	/**
	 * 获取所有的全局参数
	 *
	 * @return 全局参数列表
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/list")
	public ResponseJson<List<ApiGlobalParam>> list(Long docId) {
		Long docIdNew = Optional.ofNullable(docId).orElse(0L);
		if (docIdNew > 0 && !apiDocAuthJudgeService.haveDevelopAuth(docIdNew)) {
			return DocResponseJson.warn("没有此文档的查看权限");
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		QueryWrapper<ApiGlobalParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("doc_id", docIdNew);
		// 全局参数才按创建人来控制，文档的全局参数大家共用
		queryWrapper.eq(docIdNew == 0, "create_user_id", currentUser.getUserId());
		queryWrapper.orderByDesc("id");
		List<ApiGlobalParam> globalParamList = apiGlobalParamService.list(queryWrapper);
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
	public ResponseJson<List<ApiDoc>> update(ApiGlobalParam globalParam) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		globalParam.setDocId(Optional.ofNullable(globalParam.getDocId()).orElse(0L));
		// 新的文档ID是否有权限
		if (globalParam.getDocId() > 0 && !apiDocAuthJudgeService.haveDevelopAuth(globalParam.getDocId())) {
			return DocResponseJson.warn("没有此文档的查看权限");
		}
		if (globalParam.getId() == null) {
			globalParam.setYn(1);
			globalParam.setCreateTime(new Date());
			globalParam.setCreateUserId(currentUser.getUserId());
			globalParam.setCreateUserName(currentUser.getUsername());
		} else {
			ApiGlobalParam param = apiGlobalParamService.getById(globalParam.getId());
			if (param.getDocId() > 0) {
				// 已有的文档ID是否有权限
				if (!apiDocAuthJudgeService.haveDevelopAuth(param.getDocId())) {
					return DocResponseJson.warn("没有此文档的查看权限");
				}
			} else {
				if (!Objects.equals(param.getCreateUserId(), currentUser.getUserId())) {
					return DocResponseJson.warn("目标全局参数不存在");
				}
			}
		}
		QueryWrapper<ApiGlobalParam> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		wrapper.eq("param_key", globalParam.getParamKey());
		wrapper.eq("doc_id", globalParam.getDocId());
		// 全局参数才按创建人来控制，文档的全局参数大家共用
		wrapper.eq(globalParam.getDocId() == 0, "create_user_id", currentUser.getUserId());
		List<ApiGlobalParam> paramList = apiGlobalParamService.list(wrapper);
		if (CollectionUtils.isNotEmpty(paramList)) {
			if (paramList.size() > 1 || !Objects.equals(paramList.get(0).getId(), globalParam.getId())) {
				return DocResponseJson.warn("全局参数名称不能重复");
			}
		}
		apiGlobalParamService.saveOrUpdate(globalParam);
		return DocResponseJson.ok();
	}
}
