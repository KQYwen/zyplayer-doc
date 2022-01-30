package com.zyplayer.doc.api.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyplayer.doc.api.framework.utils.SwaggerDocUtil;
import com.zyplayer.doc.api.service.SwaggerHttpRequestService;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomDocVo;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomVo;
import com.zyplayer.doc.data.repository.manage.vo.ApiDocVo;
import com.zyplayer.doc.data.repository.support.consts.ApiAuthType;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomNodeService;
import com.zyplayer.doc.data.service.manage.ApiCustomRequestService;
import com.zyplayer.doc.data.service.manage.ApiDocService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2021年10月16日
 */
@AuthMan
@RestController
@RequestMapping("/doc-api/doc")
public class ApiDocumentController {
	private static Logger logger = LoggerFactory.getLogger(ApiDocumentController.class);
	
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	@Resource
	private ApiDocService apiDocService;
	@Resource
	private SwaggerHttpRequestService swaggerHttpRequestService;
	@Resource
	ApiCustomNodeService apiCustomNodeService;
	
	/**
	 * 获取所有的文档地址
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/list")
	public ResponseJson<List<ApiDocVo>> list(ApiDoc apiDoc, Integer pageNum, Integer pageSize) {
		IPage<ApiDocVo> docList = apiDocService.getApiDocList(apiDoc, pageNum, pageSize);
		return DocResponseJson.ok(docList);
	}
	
	/**
	 * 获取文档内容
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/detail")
	public ResponseJson<List<ApiDoc>> detail(Long id) {
		ApiDoc apiDoc = apiDocService.getById(id);
		if (!apiDocAuthJudgeService.haveDevelopAuth(apiDoc)) {
			return DocResponseJson.warn("没有此文档的查看权限");
		}
		ApiDocVo apiDocVo = new ApiDocVo();
		BeanUtil.copyProperties(apiDoc, apiDocVo);
		Integer authType = apiDocAuthJudgeService.haveManageAuth(apiDoc) ? ApiAuthType.MANAGE.getType() : ApiAuthType.DEVELOPER.getType();
		apiDocVo.setAuthType(authType);
		return DocResponseJson.ok(apiDocVo);
	}
	
	/**
	 * 添加文档
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/add")
	public ResponseJson<Object> add(HttpServletRequest request, ApiDoc apiDoc) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		apiDoc.setYn(1);
		apiDoc.setCreateTime(new Date());
		apiDoc.setCreateUserId(currentUser.getUserId());
		apiDoc.setCreateUserName(currentUser.getUsername());
		if (apiDoc.getId() == null) {
			apiDoc.setShareUuid(IdUtil.simpleUUID());
		} else {
			ApiDoc apiDocSel = apiDocService.getById(apiDoc.getId());
			if (apiDocSel == null) {
				return DocResponseJson.warn("未找到指定的文档记录信息");
			}
			if (!apiDocAuthJudgeService.haveManageAuth(apiDocSel)) {
				return DocResponseJson.warn("没有此文档的操作权限");
			}
			if (StringUtils.isBlank(apiDocSel.getShareUuid())) {
				apiDoc.setShareUuid(IdUtil.simpleUUID());
			}
		}
		// url类型
		if (Objects.equals(apiDoc.getDocType(), 1)) {
			// UI地址替换为文档json地址
			String docUrl = SwaggerDocUtil.replaceSwaggerResources(apiDoc.getDocUrl());
			if (SwaggerDocUtil.isSwaggerResources(docUrl)) {
				String swaggerDomain = SwaggerDocUtil.getSwaggerResourceDomain(docUrl);
				List<SwaggerResource> resourceList;
				try {
					String resourcesStr = swaggerHttpRequestService.requestSwaggerUrl(request, 0L, docUrl, swaggerDomain);
					resourceList = JSON.parseArray(resourcesStr, SwaggerResource.class);
				} catch (Exception e) {
					e.printStackTrace();
					return DocResponseJson.warn("解析文档地址失败：" + e.getMessage());
				}
				if (resourceList == null || resourceList.isEmpty()) {
					return DocResponseJson.warn("该地址未找到文档");
				}
				// 删除原有文档
				if (apiDoc.getId() != null) {
					apiDocService.removeById(apiDoc.getId());
				}
				// 存明细地址
				for (SwaggerResource resource : resourceList) {
					apiDoc.setId(null);
					apiDoc.setDocUrl(swaggerDomain + resource.getUrl());
					apiDoc.setName(resource.getName());
					apiDoc.setShareUuid(IdUtil.simpleUUID());
					apiDocService.save(apiDoc);
				}
			} else {
				apiDocService.saveOrUpdate(apiDoc);
			}
		} else if (Objects.equals(apiDoc.getDocType(), 2)
				|| Objects.equals(apiDoc.getDocType(), 3)
				|| Objects.equals(apiDoc.getDocType(), 4)
				|| Objects.equals(apiDoc.getDocType(), 5)) {
			apiDocService.saveOrUpdate(apiDoc);
		} else {
			return DocResponseJson.warn("暂不支持的文档类型");
		}
		return DocResponseJson.ok(apiDoc);
	}
	
	/**
	 * 修改文档基本信息
	 *
	 * @return 无
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/update")
	public ResponseJson<List<ApiDoc>> update(ApiDoc apiDoc) {
		if (apiDoc.getId() == null) {
			return DocResponseJson.warn("请指定修改的记录ID");
		}
		// 基本信息可以改，删除需要管理员权限
		if (Objects.equals(apiDoc.getYn(), 0)) {
			if (!apiDocAuthJudgeService.haveManageAuth(apiDoc.getId())) {
				return DocResponseJson.warn("没有此文档的删除权限");
			}
		} else {
			if (!apiDocAuthJudgeService.haveDevelopAuth(apiDoc.getId())) {
				return DocResponseJson.warn("没有此文档的编辑权限");
			}
		}
		ApiDoc swaggerDocUp = new ApiDoc();
		swaggerDocUp.setId(apiDoc.getId());
		swaggerDocUp.setDocStatus(apiDoc.getDocStatus());
		swaggerDocUp.setShareInstruction(apiDoc.getShareInstruction());
		swaggerDocUp.setYn(apiDoc.getYn());
		apiDocService.updateById(swaggerDocUp);
		return DocResponseJson.ok();
	}
	
	@RequestMapping("/apis")
	public ResponseJson<List<ApiDoc>> resources() {
		List<ApiDoc> docList = apiDocService.getApiDocList();
		return DocResponseJson.ok(docList);
	}
	
	@RequestMapping("/apis/detail")
	public ResponseJson<Object> detail(HttpServletRequest request, Long id) {
		ApiDoc apiDoc = apiDocService.getById(id);
		if (apiDoc == null) {
			return DocResponseJson.warn("文档不存在");
		}
		if (!apiDocAuthJudgeService.haveDevelopAuth(apiDoc)) {
			return DocResponseJson.warn("没有此文档的查看权限");
		}
		if (Objects.equals(apiDoc.getDocType(), 1) || Objects.equals(apiDoc.getDocType(), 3)) {
			try {
				String docsDomain = SwaggerDocUtil.getV2ApiDocsDomain(apiDoc.getDocUrl());
				String contentStr = swaggerHttpRequestService.requestSwaggerUrl(request, apiDoc.getId(), apiDoc.getDocUrl(), docsDomain);
				return DocResponseJson.ok(contentStr);
			} catch (Exception e) {
				e.printStackTrace();
				return DocResponseJson.warn("请求文档失败");
			}
		}
		if (Objects.equals(apiDoc.getDocType(), 2) || Objects.equals(apiDoc.getDocType(), 4)) {
			return DocResponseJson.ok(apiDoc.getJsonContent());
		}
		if (Objects.equals(apiDoc.getDocType(), 5)) {
			List<ApiCustomDocVo> customVoList = apiCustomNodeService.buildCustomApiList(apiDoc);
			return DocResponseJson.ok(customVoList);
		}
		return DocResponseJson.warn("暂不支持的文档类型");
	}
}
