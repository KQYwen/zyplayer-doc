package com.zyplayer.doc.db.framework.db.mapper.base;

import java.util.Map;

/**
 * 执行结果回调
 *
 * @author 暮光：城中城
 * @since 2019年8月18日
 */
public interface ResultHandler {
	
	void handleResult(Map<String, Object> resultMap);
	
}
