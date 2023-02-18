package com.zyplayer.doc.core.util;

import java.util.LinkedList;
import java.util.List;

/**
 * zyplayer-doc版本号
 *
 * @author 暮光：城中城
 * @since 2021-06-06
 */
public class ZyplayerDocVersion {
	public static final String version = "1.1.3";
	
	/**
	 * 每次升级必须添加一条记录，用于执行它的升级SQL
	 */
	public static final List<String> versionUpgrade = new LinkedList<String>() {{
		add("1.1.3");
		add("1.1.2");
	}};
}
