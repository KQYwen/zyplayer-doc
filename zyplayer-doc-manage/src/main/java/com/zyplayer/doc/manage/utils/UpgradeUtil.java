package com.zyplayer.doc.manage.utils;

import com.zyplayer.doc.core.util.ZyplayerDocVersion;
import com.zyplayer.doc.manage.utils.bean.UpgradeInfo;

public class UpgradeUtil {
	public static UpgradeInfo upgradeInfo = new UpgradeInfo();
	
	static {
		UpgradeUtil.upgradeInfo.setNowVersion(ZyplayerDocVersion.version);
	}
}
