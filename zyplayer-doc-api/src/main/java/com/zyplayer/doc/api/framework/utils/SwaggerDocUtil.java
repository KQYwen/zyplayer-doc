package com.zyplayer.doc.api.framework.utils;

/**
 * swagger文档工具类
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
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
	
	public static String getV2ApiDocsDomain(String docUrl) {
		int index = docUrl.indexOf("/v2/api-docs");
		if (index >= 0) {
			return docUrl.substring(0, index);
		}
		return "";
	}
	
	public static String getDomainHost(String domain) {
		domain = domain.replace("http://", "");
		domain = domain.replace("https://", "");
		int index = domain.indexOf("/");
		if (index >= 0) {
			return domain.substring(0, index);
		}
		return domain;
	}
	
	public static boolean isSwaggerLocation(String docUrl) {
		return docUrl.contains("/v2/api-docs");
	}
	
}
