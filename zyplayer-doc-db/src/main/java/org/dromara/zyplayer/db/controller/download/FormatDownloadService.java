package org.dromara.zyplayer.db.controller.download;

import org.dromara.zyplayer.db.controller.param.DataViewParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载服务
 *
 * @author 暮光：城中城
 * @since 2021-08-14
 */
public interface FormatDownloadService {
	
	void download(HttpServletResponse response, DataViewParam param, String[] tableNameArr) throws Exception;
	
}
