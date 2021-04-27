package com.zyplayer.doc.db.framework.consts;

public enum DbAuthType {
	NO_AUTH(0, "DB_NO_AUTH_"),
	VIEW(1, "DB_VIEW_"),
	SELECT(2, "DB_SELECT_"),
	UPDATE(3, "DB_UPDATE_"),
	DESC_EDIT(4, "DB_DESC_EDIT_"),
	PROC_EDIT(5, "DB_PROC_EDIT_"),
	;
	private Integer type;
	private String name;
	
	DbAuthType(Integer type, String name) {
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
