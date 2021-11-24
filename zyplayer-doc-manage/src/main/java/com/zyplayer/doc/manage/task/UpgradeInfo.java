package com.zyplayer.doc.manage.task;

/**
 * 升级信息对象
 *
 * @author 暮光：城中城
 * @since 2019年4月27日
 */
public class UpgradeInfo {
	private String nowVersion;
	private String lastVersion;
	private String upgradeContent;
	private String upgradeUrl;
	private String nextStep;
	
	public String getLastVersion() {
		return lastVersion;
	}
	
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}
	
	public String getUpgradeContent() {
		return upgradeContent;
	}
	
	public void setUpgradeContent(String upgradeContent) {
		this.upgradeContent = upgradeContent;
	}
	
	public String getUpgradeUrl() {
		return upgradeUrl;
	}
	
	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}
	
	public String getNextStep() {
		return nextStep;
	}
	
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
	
	public String getNowVersion() {
		return nowVersion;
	}
	
	public void setNowVersion(String nowVersion) {
		this.nowVersion = nowVersion;
	}
}
