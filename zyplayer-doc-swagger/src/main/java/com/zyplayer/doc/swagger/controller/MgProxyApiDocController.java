package com.zyplayer.doc.swagger.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.swagger.framework.constant.Consts;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 接口文档代理返回控制器
 *
 * @author 暮光：城中城
 * @since 2019年8月11日
 */
@RestController
@RequestMapping("/swagger-mg-ui/document")
public class MgProxyApiDocController {
	private static Logger logger = LoggerFactory.getLogger(MgProxyApiDocController.class);
	
	@Resource
	private MgStorageService storageService;
	
	/**
	 * 获取增加的json格式文档
	 *
	 * @param id swagger的文档内容的id
	 * @return 结果
	 * @author 暮光：城中城
	 * @since 2019年8月11日
	 */
	@GetMapping(value = Consts.PROXY_API_DOCS)
	public String getApiDocs(Long id) {
		String result = storageService.get(StorageKeys.PROXY_API_DOCS + id);
		if (StringUtils.isBlank(result)) {
			return DocResponseJson.warn("未找到该文档").toJson();
		}
		return result;
	}
}
