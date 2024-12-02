package org.dromara.zyplayer.manage.framework.upgrade;

import org.dromara.zyplayer.core.util.ZyplayerDocVersion;

/**
 * 升级工具类
 *
 * @author 暮光：城中城
 * @since 2019年4月27日
 */
public class UpgradeUtil {
	public static UpgradeInfo upgradeInfo = new UpgradeInfo();
	
	static {
		UpgradeUtil.upgradeInfo.setNowVersion(ZyplayerDocVersion.version);
	}
}
