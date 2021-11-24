package com.zyplayer.doc.wiki.framework.consts;

/**
 * wiki权限类型
 *
 * @author 暮光：城中城
 * @since 2019-06-01
 */
public enum WikiAuthType {
	@Deprecated
	CREATE_PAGE(1, "WIKI_CREATE_PAGE_"),
	EDIT_PAGE(2, "WIKI_EDIT_PAGE_"),
	@Deprecated
	COMMENT_PAGE(3, "WIKI_COMMENT_PAGE_"),
	DELETE_PAGE(4, "WIKI_DELETE_PAGE_"),
	PAGE_FILE_UPLOAD(5, "WIKI_PAGE_FILE_UPLOAD_"),
	PAGE_FILE_DELETE(6, "WIKI_PAGE_FILE_DELETE_"),
	PAGE_AUTH_MANAGE(7, "WIKI_PAGE_AUTH_MANAGE_"),
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
