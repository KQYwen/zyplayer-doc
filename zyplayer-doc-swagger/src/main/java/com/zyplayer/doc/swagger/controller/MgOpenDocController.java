package com.zyplayer.doc.swagger.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.swagger.controller.vo.SwaggerResourcesInfoVo;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 开放接口文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年1月27日
 */
@RestController
@RequestMapping("/swagger-mg-ui/open-doc")
public class MgOpenDocController {
	private static Logger logger = LoggerFactory.getLogger(MgOpenDocController.class);
	
	@Resource
	private MgStorageService storageService;
	
	/**
	 * @author 暮光：城中城
	 * @since 2019年1月27日
	 */
	@ResponseBody
	@PostMapping(value = "/{source}")
	public ResponseJson<List<SwaggerResourcesInfoVo>> resourcesList(@PathVariable String source) {
		
		return DocResponseJson.ok();
	}
}
