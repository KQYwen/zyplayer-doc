package com.zyplayer.doc.db.framework.db.mapper.hive;

import com.zyplayer.doc.db.framework.db.dto.ColumnInfoDto;
import org.apache.ibatis.annotations.Param;

/**
 * mysql数据库的mapper持有对象
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public interface HiveMapper {
	
	ColumnInfoDto getColumnInfo(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("columnName") String columnName);
}
