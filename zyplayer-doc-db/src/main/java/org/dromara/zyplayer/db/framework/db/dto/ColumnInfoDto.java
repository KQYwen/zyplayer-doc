package org.dromara.zyplayer.db.framework.db.dto;

import lombok.Data;

/**
 * 字段信息
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Data
public class ColumnInfoDto {
	private String isNullable;
	private String columnType;
	private String columnDefault;
	private String extra;
}
