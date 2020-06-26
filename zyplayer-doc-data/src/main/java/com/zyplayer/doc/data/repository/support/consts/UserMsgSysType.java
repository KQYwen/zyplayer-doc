package com.zyplayer.doc.data.repository.support.consts;

public enum UserMsgSysType {
	// 系统类型 1=manage 2=wiki 3=db
	MANAGE(1), WIKI(2), DB(2),
	;
	
	UserMsgSysType(int type) {
		this.type = type;
	}
	
	private int type;
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}
