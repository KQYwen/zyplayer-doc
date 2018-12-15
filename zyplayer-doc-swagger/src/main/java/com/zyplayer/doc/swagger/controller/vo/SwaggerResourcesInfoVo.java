package com.zyplayer.doc.swagger.controller.vo;

import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;

import java.util.Date;

public class SwaggerResourcesInfoVo {
	private String url;
	private String storageKey;
	private Date creationTime;
	private Date lastSync;

	public SwaggerResourcesInfoVo(String url){
		this.url = url;
		this.storageKey = StorageKeys.SWAGGER_OFFLINE_DOC_START + RandomUtil.simpleUUID();
		this.creationTime = new Date();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastSync() {
		return lastSync;
	}

	public void setLastSync(Date lastSync) {
		this.lastSync = lastSync;
	}
}
