package org.dromara.zyplayer.data.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlUtils {
	private static final Logger logger = LoggerFactory.getLogger(HtmlUtils.class);
	
	/**
	 * 从Content-Disposition头部提取文件名
	 *
	 * @param contentDisposition Content-Disposition头部字符串
	 * @return 文件名，如果未找到则返回null
	 */
	public static String extractFilename(String contentDisposition, String defaultName) {
		if (StringUtils.isBlank(contentDisposition)) {
			return defaultName;
		}
		for (String name : contentDisposition.split(";")) {
			String[] nameArr = name.trim().split("=");
			if (nameArr.length >= 2 && StringUtils.startsWith(nameArr[0], "filename")) {
				return StringUtils.removeEnd(StringUtils.removeStart(nameArr[1].trim(), "\""), "\"");
			}
		}
		return defaultName;
	}
}
