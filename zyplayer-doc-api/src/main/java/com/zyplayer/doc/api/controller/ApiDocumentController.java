package com.zyplayer.doc.api.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyplayer.doc.api.framework.utils.SwaggerDocUtil;
import com.zyplayer.doc.api.service.SwaggerHttpRequestService;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.service.manage.ApiDocService;
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
	private ApiDocService swaggerDocService;
	@Resource
	private SwaggerHttpRequestService swaggerHttpRequestService;
	
	/**
	 * 获取所有的文档地址
	 *
	 * @return 文档内容
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/list")
	public ResponseJson<List<ApiDoc>> list(ApiDoc apiDoc, Integer pageNum, Integer pageSize) {
		IPage<ApiDoc> docList = swaggerDocService.getApiDocList(apiDoc, pageNum, pageSize);
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
		ApiDoc apiDoc = swaggerDocService.getById(id);
		return DocResponseJson.ok(apiDoc);
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
	public ResponseJson<List<ApiDoc>> add(HttpServletRequest request, ApiDoc apiDoc) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		apiDoc.setYn(1);
		apiDoc.setCreateTime(new Date());
		apiDoc.setCreateUserId(currentUser.getUserId());
		apiDoc.setCreateUserName(currentUser.getUsername());
		// url类型
		if (Objects.equals(apiDoc.getDocType(), 1)) {
			// UI地址替换为文档json地址
			String docUrl = SwaggerDocUtil.replaceSwaggerResources(apiDoc.getDocUrl());
			if (SwaggerDocUtil.isSwaggerResources(docUrl)) {
				String swaggerDomain = SwaggerDocUtil.getSwaggerResourceDomain(docUrl);
				List<SwaggerResource> resourceList;
				try {
					String resourcesStr = swaggerHttpRequestService.requestSwaggerUrl(request, docUrl, swaggerDomain);
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
					swaggerDocService.removeById(apiDoc.getId());
				}
				// 存明细地址
				for (SwaggerResource resource : resourceList) {
					apiDoc.setId(null);
					apiDoc.setDocUrl(swaggerDomain + resource.getUrl());
					apiDoc.setName(resource.getName());
					swaggerDocService.save(apiDoc);
				}
			} else {
				swaggerDocService.saveOrUpdate(apiDoc);
			}
		} else if (Objects.equals(apiDoc.getDocType(), 2) || Objects.equals(apiDoc.getDocType(), 4)) {
			swaggerDocService.saveOrUpdate(apiDoc);
		} else {
			return DocResponseJson.warn("暂不支持的文档类型");
		}
		return DocResponseJson.ok();
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
		ApiDoc swaggerDocUp = new ApiDoc();
		swaggerDocUp.setId(apiDoc.getId());
		swaggerDocUp.setDocStatus(apiDoc.getDocStatus());
		swaggerDocUp.setDocUrl(apiDoc.getDocUrl());
		swaggerDocUp.setJsonContent(apiDoc.getJsonContent());
		swaggerDocUp.setName(apiDoc.getName());
		swaggerDocUp.setOpenVisit(apiDoc.getOpenVisit());
		swaggerDocUp.setRewriteDomain(apiDoc.getRewriteDomain());
		swaggerDocUp.setYn(apiDoc.getYn());
		swaggerDocService.updateById(swaggerDocUp);
		return DocResponseJson.ok();
	}
	
	@RequestMapping("/apis")
	public ResponseJson<List<ApiDoc>> resources() {
		QueryWrapper<ApiDoc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq("doc_status", 1);
		queryWrapper.orderByAsc("id");
		queryWrapper.select("id", "name", "doc_type", "doc_url", "rewrite_domain", "open_visit", "doc_status");
		List<ApiDoc> docList = swaggerDocService.list(queryWrapper);
		return DocResponseJson.ok(docList);
	}
	
	@RequestMapping("/apis/detail")
	public ResponseJson<Object> detail(HttpServletRequest request, Long id) {
		ApiDoc apiDoc = swaggerDocService.getById(id);
		if (apiDoc == null) {
			return DocResponseJson.warn("文档不存在");
		}
		if (Objects.equals(apiDoc.getDocType(), 1)) {
			try {
				String docsDomain = SwaggerDocUtil.getV2ApiDocsDomain(apiDoc.getDocUrl());
				String contentStr = swaggerHttpRequestService.requestSwaggerUrl(request, apiDoc.getDocUrl(), docsDomain);
				return DocResponseJson.ok(contentStr);
			} catch (Exception e) {
				e.printStackTrace();
				return DocResponseJson.warn("请求文档失败");
			}
		}
		if (Objects.equals(apiDoc.getDocType(), 2) || Objects.equals(apiDoc.getDocType(), 4)) {
			return DocResponseJson.ok(apiDoc.getJsonContent());
		}
		return DocResponseJson.warn("暂不支持的文档类型");
	}
}
