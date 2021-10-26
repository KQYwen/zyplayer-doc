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
	
	public static String getSwaggerResourceDomain(String docUrl) {
		int index = docUrl.indexOf("/swagger-resources");
		if (index >= 0) {
			return docUrl.substring(0, index);
		}
		return "";
	}
	
	public static boolean isSwaggerLocation(String docUrl) {
		return docUrl.contains("/v2/api-docs");
	}
	
}
