package com.zyplayer.doc.wiki.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@RestController
@RequestMapping("/zyplayer-doc-wiki/doc-wiki")
public class WikiController {
	private static Logger logger = LoggerFactory.getLogger(WikiController.class);
	
	/**
	 *
	 * @author 暮光：城中城
	 * @since 2019年2月17日
	 **/
	@PostMapping(value = "/list")
	public DocResponseJson list() {
		return DocResponseJson.ok();
	}
}

