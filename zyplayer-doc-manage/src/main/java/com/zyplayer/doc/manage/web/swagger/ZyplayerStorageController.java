package com.zyplayer.doc.manage.web.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyplayer.doc.manage.repository.manage.mapper.ZyplayerStorageMapper;

@RestController
@RequestMapping("/zyplayer/storage")
public class ZyplayerStorageController {

	@Autowired
	ZyplayerStorageMapper zyplayerStorageMapper;

	@RequestMapping("/mapper")
	public String mapper() {
		Integer selectTop = zyplayerStorageMapper.selectTop();
		return "selectTop:" + selectTop;
	}

}
