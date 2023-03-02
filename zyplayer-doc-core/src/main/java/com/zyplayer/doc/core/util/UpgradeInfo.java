package com.zyplayer.doc.core.util;

import lombok.Data;

@Data
public class UpgradeInfo {
	private String version;
	private boolean haveUpgradeSql;
	
	public UpgradeInfo() {
	}
	
	public UpgradeInfo(String version, boolean haveUpgradeSql) {
		this.version = version;
		this.haveUpgradeSql = haveUpgradeSql;
	}
}
