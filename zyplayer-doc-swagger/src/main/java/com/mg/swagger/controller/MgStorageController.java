package com.mg.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mg.swagger.framework.constant.Toast;
import com.mg.swagger.framework.json.MgUiResponseJson;
import com.mg.swagger.framework.service.MgStorageService;

/**
 * 后台存储服务控制器
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@RestController
@RequestMapping("/swagger-mg-ui/storage")
public class MgStorageController {

	@Autowired(required = false)
	private MgStorageService storageService;

	@PostMapping(value = "/checkConfig")
	public MgUiResponseJson checkConfig() {
		// 本接口能访问当而且实现了MgStorageService才算配置好了
		if (storageService == null) {
			return MgUiResponseJson.error("服务不可用");
		}
		return MgUiResponseJson.ok();
	}
	
	@PostMapping(value = "/data")
	public MgUiResponseJson setData(String key, String value) {
		if (storageService == null) {
			return MgUiResponseJson.warn(Toast.AUTOWIRED_ERROR);
		}
		if (key == null || value == null) {
			return MgUiResponseJson.warn("参数名或值不能为空");
		}
		storageService.put(key, value);
		return MgUiResponseJson.ok();
	}

	@GetMapping(value = "/data")
	public MgUiResponseJson getData(String key) {
		if (storageService == null) {
			return MgUiResponseJson.warn(Toast.AUTOWIRED_ERROR);
		}
		if (key == null) {
			return MgUiResponseJson.warn("参数名不能为空");
		}
		String value = storageService.get(key);
		value = (value == null) ? "" : value;
		return MgUiResponseJson.ok(value);
	}
}