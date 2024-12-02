package org.dromara.zyplayer.manage.web;

import org.dromara.zyplayer.core.json.DocResponseJson;
import org.dromara.zyplayer.core.json.ResponseJson;
import org.dromara.zyplayer.manage.framework.config.ZyplayerModuleKeeper;
import org.dromara.zyplayer.manage.framework.upgrade.UpgradeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统信息控制器
 *
 * @author 暮光：城中城
 * @author Sh1yu 2023年6月15日
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/system/info")
public class SystemInfoController {

    @Resource
    ZyplayerModuleKeeper moduleKeeper;

    @PostMapping("/upgrade")
    public ResponseJson<Object> upgradeInfo() {
        return DocResponseJson.ok(UpgradeUtil.upgradeInfo);
    }

    @GetMapping("/module")
    public ResponseJson<Object> moduleInfo() {
        return DocResponseJson.ok(moduleKeeper.getmoduleInfo());
    }

}
