package com.zyplayer.doc.core.util;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 字符串操作类
 */
public final class StringUtil {
	
	/**
	 * 获取错误信息
	 *
	 * @param e
	 * @return
	 * @author 暮光：城中城
	 */
	public static String getException(Throwable e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			return sw.toString();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
