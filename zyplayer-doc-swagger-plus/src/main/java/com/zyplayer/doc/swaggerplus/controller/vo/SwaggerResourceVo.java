
package com.zyplayer.doc.swaggerplus.controller.vo;

import com.google.common.collect.ComparisonChain;

public class SwaggerResourceVo implements Comparable<SwaggerResourceVo> {
	private String name;
	private String url;
	private String swaggerVersion;
	private String rewriteDomain;
	
	public SwaggerResourceVo() {
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getLocation() {
		return this.url;
	}
	
	public void setLocation(String location) {
		this.url = location;
	}
	
	public String getSwaggerVersion() {
		return this.swaggerVersion;
	}
	
	public void setSwaggerVersion(String swaggerVersion) {
		this.swaggerVersion = swaggerVersion;
	}
	
	public int compareTo(SwaggerResourceVo other) {
		return ComparisonChain.start().compare(this.swaggerVersion, other.swaggerVersion).compare(this.name, other.name).result();
	}
	
	public String getRewriteDomain() {
		return rewriteDomain;
	}
	
	public void setRewriteDomain(String rewriteDomain) {
		this.rewriteDomain = rewriteDomain;
	}
}
