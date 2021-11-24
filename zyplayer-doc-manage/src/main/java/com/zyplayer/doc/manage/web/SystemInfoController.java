package com.zyplayer.doc.manage.web;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.manage.task.UpgradeUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息控制器
 *
 * @author 暮光：城中城
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/system/info")
public class SystemInfoController {
	
	@PostMapping("/upgrade")
	public ResponseJson<Object> upgradeInfo() {
		return DocResponseJson.ok(UpgradeUtil.upgradeInfo);
	}
	
}
