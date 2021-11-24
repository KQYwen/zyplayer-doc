package com.zyplayer.doc.swagger.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.swagger.framework.service.MgStorage;
import com.zyplayer.doc.swagger.framework.service.MgStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台存储服务控制器
 *
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@AuthMan
@RestController
@RequestMapping("/swagger-mg-ui/storage")
public class MgStorageController {

	@Resource
	private MgStorageService storageService;

	@PostMapping(value = "/checkConfig")
	public DocResponseJson<Object> checkConfig() {
		return DocResponseJson.ok();
	}

	@PostMapping(value = "/data")
	public DocResponseJson<Object> setData(String key, String value) {
		if (key == null || value == null) {
			return DocResponseJson.warn("参数名或值不能为空");
		}
		storageService.put(key, value);
		return DocResponseJson.ok();
	}

	@GetMapping(value = "/data")
	public DocResponseJson<String> getData(String key) {
		if (key == null) {
			return DocResponseJson.warn("参数名不能为空");
		}
		String value = storageService.get(key);
		value = (value == null) ? "" : value;
		return DocResponseJson.ok(value);
	}

	@PostMapping(value = "/delete")
	public DocResponseJson<Object> delete(String key) {
		if (key == null) {
			return DocResponseJson.warn("参数名不能为空");
		}
		storageService.remove(key);
		return DocResponseJson.ok();
	}

	@PostMapping(value = "/like")
	public DocResponseJson<List<MgStorage>> like(String key, String value) {
		List<MgStorage> likeList = storageService.like(key, value);
		return DocResponseJson.ok(likeList);
	}
}
