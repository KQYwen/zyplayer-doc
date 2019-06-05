package com.zyplayer.doc.wiki.framework.consts;

public enum WikiAuthType {
	CREATE_PAGE(1, "WIKI_CREATE_PAGE_"),
	EDIT_PAGE(1, "WIKI_EDIT_PAGE_"),
	COMMENT_PAGE(1, "WIKI_COMMENT_PAGE_"),
	DELETE_PAGE(1, "WIKI_DELETE_PAGE_"),
	PAGE_FILE_UPLOAD(1, "WIKI_PAGE_FILE_UPLOAD_"),
	PAGE_FILE_DELETE(1, "WIKI_PAGE_FILE_DELETE_"),
	PAGE_AUTH_MANAGE(1, "WIKI_PAGE_AUTH_MANAGE_"),
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
