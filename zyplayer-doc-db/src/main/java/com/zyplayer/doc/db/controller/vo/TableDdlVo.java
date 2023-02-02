package com.zyplayer.doc.db.controller.vo;

import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import lombok.Data;

import java.util.Objects;

/**
 * 表ddl信息
 *
 * @author 暮光：城中城
 * @since 2021-04-23
 */
@Data
public class TableDdlVo {
	private String current;
	private String mysql;
	private String sqlserver;
	private String oracle;
	private String dm;
	private String postgresql;
	private String hive;

	// 获取连接类型的ddl
	public String getTableDDLByType() {
		if (Objects.equals(current, DatabaseProductEnum.MYSQL.name().toLowerCase())) {
			return mysql;
		}
		if (Objects.equals(current, DatabaseProductEnum.SQLSERVER.name().toLowerCase())) {
			return sqlserver;
		}
		if (Objects.equals(current, DatabaseProductEnum.ORACLE.name().toLowerCase())) {
			return oracle;
		}
		if (Objects.equals(current, DatabaseProductEnum.DM.name().toLowerCase())) {
			return dm;
		}
		if (Objects.equals(current, DatabaseProductEnum.POSTGRESQL.name().toLowerCase())) {
			return postgresql;
		}
		if (Objects.equals(current, DatabaseProductEnum.HIVE.name().toLowerCase())) {
			return hive;
		}
		return null;
	}
}
