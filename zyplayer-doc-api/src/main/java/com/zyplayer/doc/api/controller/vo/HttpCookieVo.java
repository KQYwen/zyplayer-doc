package com.zyplayer.doc.api.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cookie返回值对象
 *
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpCookieVo {
	private String name;
	private String value;
}
