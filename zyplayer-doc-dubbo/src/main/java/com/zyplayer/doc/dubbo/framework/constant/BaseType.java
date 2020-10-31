package com.zyplayer.doc.dubbo.framework.constant;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class BaseType {
	
	private static final Set<String> baseType = new HashSet<String>() {{
		add("java.lang.Boolean");
		add("java.lang.Character");
		add("java.lang.Byte");
		add("java.lang.Short");
		add("java.lang.Integer");
		add("java.lang.Long");
		add("java.lang.Float");
		add("java.lang.Double");
		add("java.lang.String");
		add("java.lang.Void");
		add("boolean");
		add("char");
		add("byte");
		add("short");
		add("int");
		add("long");
		add("float");
		add("double");
		add("void");
	}};
	
	public static boolean isBaseType(String type) {
		if (type == null || StringUtils.startsWith(type, "java.lang")) {
			return true;
		}
		return baseType.contains(type);
	}
	
}
