package com.zyplayer.doc.db.framework.db.mapper.base;

import com.zyplayer.doc.db.framework.db.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库的mapper持有对象接口
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public interface BaseMapper {

	/**
	 * 获取库列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @return 数据库列表
	 */
	List<DatabaseInfoDto> getDatabaseList();

	/**
	 * 获取表列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param dbName 数据库名
	 * @return 数据库表列表
	 */
	List<TableInfoDto> getTableList(@Param("dbName")String dbName);
	
	/**
	 * 获取字段列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param dbName 数据库名
	 * @param tableName 表名
	 * @return 字段列表
	 */
	List<TableColumnDescDto> getTableColumnList(@Param("dbName") String dbName, @Param("tableName") String tableName);

	/**
	 * 获取字段注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName 表名
	 * @return 表字段注释
	 */
	List<TableColumnDescDto> getTableColumnDescList(@Param("tableName") String tableName);
	
	/**
	 * 模糊搜索表和字段
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param dbName 数据库名
	 * @param searchText 搜索内容
	 * @return 表和字段信息
	 */
	List<QueryTableColumnDescDto> getTableAndColumnBySearch(@Param("dbName") String dbName, @Param("searchText") String searchText);
	
	/**
	 * 获取表注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName 可不传，传了只查询指定表的注释
	 * @return 表注释
	 */
	List<TableDescDto> getTableDescList(@Param("tableName") String tableName);

	/**
	 * 增加表注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName 表名
	 * @param newDesc 新的注释
	 */
	void updateTableDesc(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("newDesc") String newDesc);

	/**
	 * 增加字段注释
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param dbName 数据库名
	 * @param tableName 表名
	 * @param columnName 字段名
	 * @param newDesc 新的注释
	 * @param columnInfo 字段信息
	 */
	void updateTableColumnDesc(@Param("dbName") String dbName, @Param("tableName") String tableName,
			@Param("columnName") String columnName, @Param("newDesc") String newDesc,
			@Param("columnInfo") ColumnInfoDto columnInfo);

}