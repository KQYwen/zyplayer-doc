package com.zyplayer.doc.swagger.controller.vo;

import java.net.URLDecoder;
import java.util.Objects;

public class LocationListVo {
	private Integer id;
	private String name;
	private String location;
	private String resources;
	private String rewriteDomainUrl;
	
	public LocationListVo(){
	
	}
	
	public LocationListVo(String location, String resources) {
		this.location = location;
		this.resources = resources;
		this.name = location;
		// 修正名字
		int indexGroup = location.indexOf("group=");
		int indexV2 = location.indexOf("/v2");
		if (indexGroup >= 0 && location.length() > indexGroup) {
			try {
				this.name = URLDecoder.decode(location.substring(indexGroup + 6), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (indexV2 >= 0) {
			this.name = location.substring(indexV2 + 3);
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
}
