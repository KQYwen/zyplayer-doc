package com.zyplayer.doc.db.controller.download;

/**
 * 下载类型枚举
 * @author 暮光：城中城
 * @since 2021-08-14
 */
public enum FormatDownloadEnum {
	INSERT(FormatDownloadConst.INSERT),
	UPDATE(FormatDownloadConst.UPDATE),
	JSON(FormatDownloadConst.JSON),
	EXCEL(FormatDownloadConst.EXCEL),
	CVS(FormatDownloadConst.CVS),
	;
	
	private String type;
	
	FormatDownloadEnum(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
