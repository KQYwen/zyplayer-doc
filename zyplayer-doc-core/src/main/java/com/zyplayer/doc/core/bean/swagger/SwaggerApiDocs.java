package com.zyplayer.doc.core.bean.swagger;

import java.util.List;

public class SwaggerApiDocs {

	private String swagger;
	private Info info;
	private String host;
	private String basePath;
	private List<String> tags;
	private Paths paths;

	public void setSwagger(String swagger) {
		this.swagger = swagger;
	}

	public String getSwagger() {
		return swagger;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public Info getInfo() {
		return info;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setPaths(Paths paths) {
		this.paths = paths;
	}

	public Paths getPaths() {
		return paths;
	}

}