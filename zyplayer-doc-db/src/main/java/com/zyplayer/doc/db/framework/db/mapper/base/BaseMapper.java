package com.zyplayer.doc.db.framework.db.mapper.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyplayer.doc.db.framework.db.dto.ColumnInfoDto;
import com.zyplayer.doc.db.framework.db.dto.DatabaseInfoDto;
import com.zyplayer.doc.db.framework.db.dto.QueryTableColumnDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableInfoDto;

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
	 * @return
	 */
	List<DatabaseInfoDto> getDatabaseList();

	/**
	 * 获取表列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param dbName
	 * @return
	 */
	List<TableInfoDto> getTableList(@Param("dbName")String dbName);
	
	/**
	 * 获取字段列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param dbName
	 * @param tableName
	 * @return
	 */
	List<TableColumnDescDto> getTableColumnList(@Param("dbName") String dbName, @Param("tableName") String tableName);

	/**
	 * 获取字段注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName
	 * @return
	 */
	List<TableColumnDescDto> getTableColumnDescList(@Param("tableName") String tableName);
	
	/**
	 * 模糊搜索表和字段
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param searchText
	 * @return
	 */
	List<QueryTableColumnDescDto> getTableAndColumnBySearch(@Param("dbName") String dbName, @Param("searchText") String searchText);
	
	/**
	 * 获取表注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName 可不传，传了只查询指定表的注释
	 * @return
	 */
	List<TableDescDto> getTableDescList(@Param("tableName") String tableName);

	/**
	 * 增加表注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName
	 * @param newDesc
	 */
	void updateTableDesc(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("newDesc") String newDesc);

	/**
	 * 增加字段注释
	 * 
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 * @param tableName
	 * @param columnName
	 * @param newDesc
	 */
	void updateTableColumnDesc(@Param("dbName") String dbName, @Param("tableName") String tableName,
			@Param("columnName") String columnName, @Param("newDesc") String newDesc,
			@Param("columnInfo") ColumnInfoDto columnInfo);

}