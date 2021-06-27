package com.zyplayer.doc.db.framework.db.parser;

import cn.hutool.core.date.DateTime;

public interface FillParamParser {
	
	/**
	 * 执行时间处理
	 *
	 * @param dateTime 时间
	 * @param paramOne 第一个参数
	 * @param paramThree 第三个参数
	 * @return 时间格式化
	 */
	String parser(DateTime dateTime, String paramOne, String paramTwo, String paramThree);
	
}
