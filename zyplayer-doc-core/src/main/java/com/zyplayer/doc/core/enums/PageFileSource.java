package com.zyplayer.doc.core.enums;

import lombok.Getter;

/**
 * 文件存储路径
 *
 * @author 暮光：城中城
 * @since 2023-10-06
 */
public enum PageFileSource {
	UPLOAD_FILES(1, "手动上传的附件"),
	PASTE_FILES(2, "页面粘贴的图片或文件"),
	;
	@Getter
	private final Integer source;
	@Getter
	private final String desc;
	
	PageFileSource(Integer source, String desc) {
		this.source = source;
		this.desc = desc;
	}
}
