package com.zyplayer.doc.db.framework.utils;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.sql.Timestamp;
import java.util.Date;

/**
 * json工具类
 *
 * @author 暮光：城中城
 * @since 2021-05-20
 */
public class JSONUtil {
	
	public static SerializeConfig serializeConfig = new SerializeConfig();
	
	static {
		serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
}
