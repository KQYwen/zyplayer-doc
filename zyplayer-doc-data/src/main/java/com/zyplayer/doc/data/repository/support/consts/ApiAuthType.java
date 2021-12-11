package com.zyplayer.doc.data.repository.support.consts;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * api授权前缀
 *
 * @author 暮光：城中城
 * @since 2021-12-12
 */
public enum ApiAuthType {
	MANAGE(1, "API_DOC_MANAGE"),
	DEVELOPER(2, "API_DOC_DEVELOPER"),
	;
	private final Integer type;
	private final String code;
	
	ApiAuthType(Integer type, String code) {
		this.type = type;
		this.code = code;
	}
	
	public Integer getType() {
		return type;
	}
	
	public String getCode() {
		return code;
	}
	
	private static final Map<Integer, ApiAuthType> API_AUTH_TYPE_MAP = Stream.of(values()).collect(Collectors.toMap(ApiAuthType::getType, (deriveType) -> deriveType));
	private static final Map<String, ApiAuthType> API_AUTH_CODE_MAP = Stream.of(values()).collect(Collectors.toMap(ApiAuthType::getCode, (deriveType) -> deriveType));
	
	public static ApiAuthType typeOf(Integer type) {
		return API_AUTH_TYPE_MAP.get(type);
	}
	
	public static ApiAuthType typeOf(String code) {
		return API_AUTH_CODE_MAP.get(code);
	}
}
