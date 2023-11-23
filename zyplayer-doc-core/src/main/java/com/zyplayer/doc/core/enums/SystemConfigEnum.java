package com.zyplayer.doc.core.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统配置枚举
 *
 * @author 暮光：城中城
 * @since 2022-10-20
 */
public enum SystemConfigEnum {
	// 系统相关
	DOC_SYSTEM_VERSION("doc_system_version", "系统当前的版本号"),
	;
	
	private final String key;
	private final String desc;
	
	SystemConfigEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	private static final Map<String, SystemConfigEnum> VALUE_MAP = Stream.of(values()).collect(Collectors.toMap(SystemConfigEnum::getKey, (treeType) -> treeType));
	
	public static SystemConfigEnum get(String code) {
		return VALUE_MAP.get(code);
	}
	
	public String getKey() {
		return key;
	}
	
	public String getDesc() {
		return desc;
	}
}
