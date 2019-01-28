package com.zyplayer.doc.swagger.controller.vo;

import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.swagger.framework.constant.StorageKeys;
import springfox.documentation.swagger.web.SwaggerResource;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SwaggerResourcesInfoVo implements Serializable {
	private Integer id;
	private String url;
	private String storageKey;
	private String rewriteDomainUrl;
	private List<SwaggerResource> resourceList;
	private Date creationTime;
	private Date lastSync;
	
	public SwaggerResourcesInfoVo() {
	
	}
	
	public SwaggerResourcesInfoVo(String url) {
		this.url = url;
		this.storageKey = StorageKeys.SWAGGER_OFFLINE_DOC_START + RandomUtil.simpleUUID();
		this.creationTime = new Date();
	}
	
	public SwaggerResourcesInfoVo(String url, List<SwaggerResource> resourceList) {
		this.url = url;
		this.resourceList = resourceList;
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
	
	public List<SwaggerResource> getResourceList() {
		return resourceList;
	}
	
	public void setResourceList(List<SwaggerResource> resourceList) {
		this.resourceList = resourceList;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRewriteDomainUrl() {
		return rewriteDomainUrl;
	}
	
	public void setRewriteDomainUrl(String rewriteDomainUrl) {
		this.rewriteDomainUrl = rewriteDomainUrl;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return Objects.equals(this.url, ((SwaggerResourcesInfoVo) obj).getUrl());
	}
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
}
