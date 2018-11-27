package com.zyplayer.doc.manage.web.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyplayer.doc.manage.repository.manage.mapper.ZyplayerStorageMapper;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@RestController
@RequestMapping("/zyplayer-storage")
public class ZyplayerStorageController {

	@Autowired
	ZyplayerStorageMapper zyplayerStorageMapper;

	@RequestMapping("/mapper")
	public String mapper() {
		Integer selectTop = zyplayerStorageMapper.selectTop();
		return "selectTop:" + selectTop;
	}

}
