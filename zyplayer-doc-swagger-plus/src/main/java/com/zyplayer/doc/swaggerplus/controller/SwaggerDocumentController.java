package com.zyplayer.doc.swaggerplus.controller;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerDoc;
import com.zyplayer.doc.data.service.manage.SwaggerDocService;
import com.zyplayer.doc.swaggerplus.framework.utils.SwaggerDocUtil;
import com.zyplayer.doc.swaggerplus.service.SwaggerHttpRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
@RequestMapping("/doc-swagger/doc")
public class SwaggerDocumentController {
	private static Logger logger = LoggerFactory.getLogger(SwaggerDocumentController.class);
	
	@Resource
	private SwaggerDocService swaggerDocService;
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
	public ResponseJson<List<SwaggerDoc>> list(SwaggerDoc swaggerDoc) {
		List<SwaggerDoc> docList = swaggerDocService.getSwaggerDocList(swaggerDoc);
		return DocResponseJson.ok(docList);
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
	public ResponseJson<List<SwaggerDoc>> add(HttpServletRequest request, SwaggerDoc swaggerDoc) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		swaggerDoc.setYn(1);
		swaggerDoc.setCreateTime(new Date());
		swaggerDoc.setCreateUserId(currentUser.getUserId());
		swaggerDoc.setCreateUserName(currentUser.getUsername());
		// url类型
		if (Objects.equals(swaggerDoc.getDocType(), 1)) {
			// UI地址替换为文档json地址
			String docUrl = SwaggerDocUtil.replaceSwaggerResources(swaggerDoc.getDocUrl());
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
				if (swaggerDoc.getId() != null) {
					swaggerDocService.removeById(swaggerDoc.getId());
				}
				// 存明细地址
				for (SwaggerResource resource : resourceList) {
					swaggerDoc.setId(null);
					swaggerDoc.setDocUrl(swaggerDomain + resource.getUrl());
					swaggerDoc.setName(resource.getName());
					swaggerDocService.save(swaggerDoc);
				}
			} else {
				swaggerDocService.saveOrUpdate(swaggerDoc);
			}
		} else {
			swaggerDocService.saveOrUpdate(swaggerDoc);
		}
		return DocResponseJson.ok();
	}
	
	/**
	 * 修改文档信息
	 *
	 * @return 无
	 * @author 暮光：城中城
	 * @since 2021年10月16日
	 */
	@ResponseBody
	@PostMapping(value = "/update")
	public ResponseJson<List<SwaggerDoc>> update(SwaggerDoc swaggerDoc) {
		if (swaggerDoc.getId() == null) {
			return DocResponseJson.warn("请指定修改的记录ID");
		}
		SwaggerDoc swaggerDocUp = new SwaggerDoc();
		swaggerDocUp.setId(swaggerDoc.getId());
		swaggerDocUp.setDocStatus(swaggerDoc.getDocStatus());
		swaggerDocUp.setDocUrl(swaggerDoc.getDocUrl());
		swaggerDocUp.setJsonContent(swaggerDoc.getJsonContent());
		swaggerDocUp.setName(swaggerDoc.getName());
		swaggerDocUp.setOpenVisit(swaggerDoc.getOpenVisit());
		swaggerDocUp.setRewriteDomain(swaggerDoc.getRewriteDomain());
		swaggerDocUp.setYn(swaggerDoc.getYn());
		swaggerDocService.updateById(swaggerDocUp);
		return DocResponseJson.ok();
	}
}
