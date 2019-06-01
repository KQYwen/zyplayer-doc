package com.zyplayer.doc.wiki.framework.consts;

public enum WikiAuthType {
	CREATE_PAGE(1, "CREATE_PAGE_"),
	VIEW_PAGE(1, "VIEW_PAGE_"),
	COMMENT_PAGE(1, "COMMENT_PAGE_"),
	DELETE_PAGE(1, "DELETE_PAGE_"),
	PAGE_FILE_UPLOAD(1, "PAGE_FILE_UPLOAD_"),
	PAGE_FILE_DELETE(1, "PAGE_FILE_DELETE_"),
	PAGE_AUTH_MANAGE(1, "PAGE_AUTH_MANAGE_"),
	;
	private Integer type;
	private String name;
	
	WikiAuthType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
