package com.zyplayer.doc.db.framework.db.mapper.postgresql;

import org.apache.ibatis.annotations.Param;

import com.zyplayer.doc.db.framework.db.dto.ColumnInfoDto;

/**
 * postgresql数据库的mapper持有对象
 *
 * @author 辽宁-天平
 * @since 2021年1月5日
 */
public interface PostgresqlMapper {
	
	ColumnInfoDto getColumnInfo(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("columnName") String columnName);
	
}
