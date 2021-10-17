package com.zyplayer.doc.swaggerplus.framework.utils;

public class SwaggerDocUtil {
	
	public static String replaceSwaggerResources(String docUrl) {
		int htmlIndex = docUrl.indexOf("/swagger-ui.html");
		if (htmlIndex > 0) {
			docUrl = docUrl.substring(0, htmlIndex) + "/swagger-resources";
		}
		return docUrl;
	}
	
	public static boolean isSwaggerResources(String docUrl) {
		return docUrl.contains("/swagger-resources");
	}
	
	public static boolean isSwaggerLocation(String docUrl) {
		return docUrl.contains("/v2/api-docs");
	}
	
}
