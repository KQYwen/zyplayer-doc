package com.zyplayer.doc.manage.web.manage;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.manage.utils.UpgradeUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/info")
public class SystemInfoController {

	@PostMapping("/upgrade")
	public ResponseJson<Object> upgradeInfo() {
		return DocResponseJson.ok(UpgradeUtil.upgradeInfo);
	}

}
