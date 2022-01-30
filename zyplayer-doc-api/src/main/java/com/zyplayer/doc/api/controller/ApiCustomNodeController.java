package com.zyplayer.doc.api.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.api.controller.vo.ApiCustomParamsVo;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomNodeService;
import com.zyplayer.doc.data.service.manage.ApiCustomParamsService;
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
@RequestMapping("/api-custom-node")
public class ApiCustomNodeController {
	
	@Resource
	ApiCustomNodeService apiCustomNodeService;
	@Resource
	ApiCustomParamsService apiCustomParamsService;
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
	public ResponseJson<Object> add(ApiCustomNode apiCustomNode, ApiCustomParams apiCustomParams) {
		apiCustomNodeService.addNode(apiCustomNode, apiCustomParams);
		return DocResponseJson.ok(apiCustomNode);
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
	public ResponseJson<Object> update(ApiCustomNode apiCustomNode) {
		// 参数未传不处理
		if (apiCustomNode.getId() == null) {
			return DocResponseJson.ok();
		}
		if (StringUtils.isBlank(apiCustomNode.getNodeName()) && StringUtils.isBlank(apiCustomNode.getNodeDesc())) {
			return DocResponseJson.ok();
		}
		ApiCustomNode apiCustomFolderSel = apiCustomNodeService.getById(apiCustomNode.getId());
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolderSel.getDocId());
		// 执行修改
		ApiCustomNode nodeUp = new ApiCustomNode();
		nodeUp.setId(apiCustomNode.getId());
		nodeUp.setNodeName(apiCustomNode.getNodeName());
		nodeUp.setNodeDesc(apiCustomNode.getNodeDesc());
		apiCustomNodeService.updateById(nodeUp);
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
		ApiCustomNode apiCustomFolderSel = apiCustomNodeService.getById(id);
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolderSel.getDocId());
		apiCustomNodeService.deleteNode(id);
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
	@PostMapping(value = "/changeParent")
	public ResponseJson<Object> changeParent(Long id, Long parentId, Integer targetType, Integer targetSeq) {
		ApiCustomNode apiCustomFolderSel = apiCustomNodeService.getById(id);
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolderSel.getDocId());
		apiCustomNodeService.changeParent(id, parentId, targetType, targetSeq);
		return DocResponseJson.ok();
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
		ApiCustomNode apiCustomNode = apiCustomNodeService.getById(id);
		if (apiCustomNode == null) {
			return DocResponseJson.warn("接口不存在");
		}
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomNode.getDocId());
		QueryWrapper<ApiCustomParams> paramsWrapper = new QueryWrapper<>();
		paramsWrapper.eq("yn", 1);
		paramsWrapper.eq("node_id", id);
		ApiCustomParams apiCustomParams = apiCustomParamsService.getOne(paramsWrapper);
		// 组装结果对象
		ApiCustomParamsVo customParamsVo = new ApiCustomParamsVo();
		customParamsVo.setId(apiCustomNode.getId());
		customParamsVo.setDocId(apiCustomNode.getDocId());
		customParamsVo.setParentId(apiCustomNode.getParentId());
		customParamsVo.setNodeType(apiCustomNode.getNodeType());
		customParamsVo.setNodeName(apiCustomNode.getNodeName());
		customParamsVo.setNodeDesc(apiCustomNode.getNodeDesc());
		customParamsVo.setSeqNo(apiCustomNode.getSeqNo());
		customParamsVo.setNodeId(apiCustomNode.getId());
		if (apiCustomParams != null) {
			customParamsVo.setMethod(apiCustomParams.getMethod());
			customParamsVo.setApiUrl(apiCustomParams.getApiUrl());
			customParamsVo.setFormData(apiCustomParams.getFormData());
			customParamsVo.setBodyData(apiCustomParams.getBodyData());
			customParamsVo.setHeaderData(apiCustomParams.getHeaderData());
			customParamsVo.setCookieData(apiCustomParams.getCookieData());
		}
		return DocResponseJson.ok(customParamsVo);
	}
}
