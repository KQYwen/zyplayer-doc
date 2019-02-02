package com.zyplayer.doc.swagger.controller.vo;

import cn.hutool.core.util.RandomUtil;

import java.net.URLDecoder;
import java.util.Objects;

public class LocationListVo {
	private Integer id;
	private String name;
	private String uuid;
	private String location;
	private String resources;
	private Integer openVisit;
	private String rewriteDomainUrl;
	
	public LocationListVo(){
	
	}
	
	public LocationListVo(String location, String resources) {
		this.location = location;
		this.resources = resources;
		this.name = location;
		this.uuid = RandomUtil.simpleUUID();
		// 修正名字
		int indexGroup = location.indexOf("group=");
		if (indexGroup >= 0 && location.length() > indexGroup) {
			try {
				this.name = URLDecoder.decode(location.substring(indexGroup + 6), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getResources() {
		return resources;
	}
	
	public void setResources(String resources) {
		this.resources = resources;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return Objects.equals(this.location, ((LocationListVo) obj).getLocation());
	}
	
	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRewriteDomainUrl() {
		return rewriteDomainUrl;
	}
	
	public void setRewriteDomainUrl(String rewriteDomainUrl) {
		this.rewriteDomainUrl = rewriteDomainUrl;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getOpenVisit() {
		return openVisit;
	}
	
	public void setOpenVisit(Integer openVisit) {
		this.openVisit = openVisit;
	}
}
