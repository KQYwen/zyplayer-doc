package com.zyplayer.doc.data.repository.support.consts;

/**
 * 系统类型枚举
 *
 * @author 暮光：城中城
 * @since 2020-06-26
 */
public enum DocSysType {
	// 系统类型 1=manage 2=wiki 3=db
	MANAGE(1), WIKI(2), DB(3), API(4),
	;
	
	DocSysType(int type) {
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
