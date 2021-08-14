package com.zyplayer.doc.db.controller.download;

import com.zyplayer.doc.db.controller.param.DataViewParam;

import javax.servlet.http.HttpServletResponse;

public interface FormatDownloadService {
	
	public void download(HttpServletResponse response, DataViewParam param, String[] tableNameArr) throws Exception;
	
}
