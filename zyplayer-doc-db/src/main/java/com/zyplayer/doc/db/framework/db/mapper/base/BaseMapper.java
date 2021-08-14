package com.zyplayer.doc.db.framework.db.mapper.base;

import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.db.controller.param.ProcedureListParam;
import com.zyplayer.doc.db.controller.vo.TableStatusVo;
import com.zyplayer.doc.db.framework.db.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据库的mapper持有对象接口
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public interface BaseMapper {
	
	/**
	 * 获取库列表
	 *
	 * @return 数据库列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	List<Map<String, String>> getTableDdl(@Param("dbName") String dbName, @Param("tableName") String tableName);
	
	/**
	 * 获取库列表
	 *
	 * @return 数据库列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	List<DatabaseInfoDto> getDatabaseList();
	
	/**
	 * 获取表列表
	 *
	 * @param dbName 数据库名
	 * @return 数据库表列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	List<TableInfoDto> getTableList(@Param("dbName") String dbName);
	
	/**
	 * 获取字段列表
	 *
	 * @param dbName    数据库名
	 * @param tableName 表名
	 * @return 字段列表
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	List<TableColumnDescDto> getTableColumnList(@Param("dbName") String dbName, @Param("tableName") String tableName);
	
	/**
	 * 模糊搜索表和字段
	 *
	 * @param dbName     数据库名
	 * @param searchText 搜索内容
	 * @return 表和字段信息
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	List<QueryTableColumnDescDto> getTableAndColumnBySearch(@Param("dbName") String dbName, @Param("searchText") String searchText);
	
	/**
	 * 获取表注释
	 *
	 * @param tableName 可不传，传了只查询指定表的注释
	 * @return 表注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	List<TableDescDto> getTableDescList(@Param("dbName") String dbName, @Param("tableName") String tableName);
	
	/**
	 * 增加表注释
	 *
	 * @param tableName 表名
	 * @param newDesc   新的注释
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	void updateTableDesc(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("newDesc") String newDesc);
	
	/**
	 * 增加字段注释
	 *
	 * @param dbName     数据库名
	 * @param tableName  表名
	 * @param columnName 字段名
	 * @param newDesc    新的注释
	 * @param columnInfo 字段信息
	 * @author 暮光：城中城
	 * @since 2018年8月8日
	 */
	void updateTableColumnDesc(@Param("dbName") String dbName, @Param("tableName") String tableName,
	                           @Param("columnName") String columnName, @Param("newDesc") String newDesc,
	                           @Param("columnInfo") ColumnInfoDto columnInfo);
	
	/**
	 * 获取表基本信息
	 *
	 * @param dbName    数据库名
	 * @param tableName 表名
	 * @author 暮光：城中城
	 * @since 2019年9月1日
	 */
	TableStatusVo getTableStatus(@Param("dbName") String dbName, @Param("tableName") String tableName);
	
	/**
	 * 获取存储过程总条数
	 *
	 * @param procedureParam 参数
	 * @author 暮光：城中城
	 * @since 2020年4月24日
	 */
	Long getProcedureCount(@Param("param") ProcedureListParam procedureParam);
	
	/**
	 * 获取存储过程列表
	 *
	 * @param procedureParam 参数
	 * @author 暮光：城中城
	 * @since 2020年4月24日
	 */
	List<ProcedureDto> getProcedureList(@Param("param") ProcedureListParam procedureParam);
	
	/**
	 * 获取存储过程详情
	 *
	 * @param dbName   数据库名
	 * @param typeName 类型名称 PROCEDURE、FUNCTION
	 * @param procName 过程名称
	 * @author 暮光：城中城
	 * @since 2020年4月24日
	 */
	ProcedureDto getProcedureDetail(@Param("dbName") String dbName, @Param("typeName") String typeName, @Param("procName") String procName);
	
	/**
	 * 删除存储过程
	 *
	 * @param dbName   数据库名
	 * @param typeName 类型名称 PROCEDURE、FUNCTION
	 * @param procName 过程名称
	 * @author 暮光：城中城
	 * @since 2020年4月24日
	 */
	void deleteProcedure(@Param("dbName") String dbName, @Param("typeName") String typeName, @Param("procName") String procName);
	
	/**
	 * 删除行数
	 * @author 暮光：城中城
	 * @since 2021-08-14
	 */
	void deleteTableLineData(@Param("dbName") String dbName, @Param("tableName") String tableName, @Param("lineParam") JSONObject lineParam);
}
