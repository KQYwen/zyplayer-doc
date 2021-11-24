package com.zyplayer.doc.db.controller.download;

import com.zyplayer.doc.db.controller.param.DataViewParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载服务
 *
 * @author 暮光：城中城
 * @since 2021-08-14
 */
public interface FormatDownloadService {
	
	public void download(HttpServletResponse response, DataViewParam param, String[] tableNameArr) throws Exception;
	
}
