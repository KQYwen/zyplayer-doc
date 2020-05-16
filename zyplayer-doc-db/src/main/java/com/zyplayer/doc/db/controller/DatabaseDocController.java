package com.zyplayer.doc.db.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.service.manage.DbDatasourceService;
import com.zyplayer.doc.data.service.manage.UserAuthService;
import com.zyplayer.doc.data.utils.CachePrefix;
import com.zyplayer.doc.data.utils.CacheUtil;
import com.zyplayer.doc.db.controller.vo.DatabaseExportVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo.TableInfoVo;
import com.zyplayer.doc.db.controller.vo.TableStatusVo;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean.DatabaseProduct;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.dto.*;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.db.mapper.mysql.MysqlMapper;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/doc-db")
public class DatabaseDocController {
	
	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	@Resource
	DbDatasourceService dbDatasourceService;
	@Resource
	UserAuthService userAuthService;
	
	@PostMapping(value = "/getDataSourceList")
	public ResponseJson getDataSourceList() {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		QueryWrapper<DbDatasource> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		// 没管理权限只返回有权限的数据源
		if (!DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE)) {
			QueryWrapper<UserAuth> updateWrapper = new QueryWrapper<>();
			updateWrapper.likeRight("auth_custom_suffix", DocAuthConst.DB);
			updateWrapper.eq("del_flag", 0);
			updateWrapper.eq("user_id", currentUser.getUserId());
			List<UserAuth> userAuthList = userAuthService.list(updateWrapper);
			if (userAuthList == null || userAuthList.isEmpty()) {
				return DocDbResponseJson.ok();
			}
			List<Long> userAuthDbIds = userAuthList.stream().map(val -> NumberUtils.toLong(val.getAuthCustomSuffix().replace(DocAuthConst.DB, ""))).collect(Collectors.toList());
			wrapper.in("id", userAuthDbIds);
		}
		List<DbDatasource> datasourceList = dbDatasourceService.list(wrapper);
		List<DatabaseFactoryBean> dataSourceList = datasourceList.stream().map(val -> {
			DatabaseFactoryBean bean = new DatabaseFactoryBean();
			bean.setCnName(val.getName());
			bean.setId(val.getId());
			return bean;
		}).collect(Collectors.toList());
		return DocDbResponseJson.ok(dataSourceList);
	}
	
	/**
	 * 获取编辑器所需的所有信息，用于自动补全
	 * 此接口会返回所有库表结构，介意的话请自己手动屏蔽调此接口
	 *
	 * @param sourceId
	 * @return
	 */
	@PostMapping(value = "/getEditorData")
	public ResponseJson getEditorData(Long sourceId) {
		// 没权限，返回空
		if (!DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE)
				&& !DocUserUtil.haveCustomAuth(DbAuthType.VIEW.getName(), DocAuthConst.DB + sourceId)) {
			return DocDbResponseJson.ok();
		}
		String cacheKey = CachePrefix.DB_EDITOR_DATA_CACHE + sourceId;
		Object resultObj = CacheUtil.get(cacheKey);
		if (resultObj != null) {
			return DocDbResponseJson.ok(resultObj);
		}
		BaseMapper baseMapper = this.getBaseMapper(sourceId);
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		List<DatabaseInfoDto> dbNameDtoList = baseMapper.getDatabaseList();
		Map<String, List<TableInfoDto>> dbTableMap = new HashMap<>();
		Map<String, List<TableColumnDescDto>> tableColumnsMap = new HashMap<>();
		
		Map<String, List<TableInfoDto>> tableMapList = new HashMap<>();
		// MYSQL可以一次性查询所有库表
		if (databaseFactoryBean.getDatabaseProduct() == DatabaseProduct.MYSQL) {
			List<TableInfoDto> dbTableList = baseMapper.getTableList(null);
			tableMapList = dbTableList.stream().collect(Collectors.groupingBy(TableInfoDto::getDbName));
		}
		for (DatabaseInfoDto infoDto : dbNameDtoList) {
			List<TableInfoDto> tableInfoDtoList = tableMapList.get(infoDto.getDbName());
			// SQLSERVER必须要库才能查
			if (databaseFactoryBean.getDatabaseProduct() == DatabaseProduct.SQLSERVER) {
				tableInfoDtoList = baseMapper.getTableList(infoDto.getDbName());
			}
			if (CollectionUtils.isEmpty(tableInfoDtoList)) {
				continue;
			}
			dbTableMap.put(infoDto.getDbName(), tableInfoDtoList);
			// 小于10个库，查所有库，否则只查询当前链接的库，防止库表太多，数据量太大
			// 如果觉得没必要就自己改吧！
			Map<String, List<TableColumnDescDto>> columnDescDtoMap = new HashMap<>();
			if (dbNameDtoList.size() <= 10 || Objects.equals(databaseFactoryBean.getDbName(), infoDto.getDbName())) {
				List<TableColumnDescDto> columnDescDto = baseMapper.getTableColumnList(infoDto.getDbName(), null);
				columnDescDtoMap = columnDescDto.stream().collect(Collectors.groupingBy(TableColumnDescDto::getTableName));
			}
			for (TableInfoDto tableInfoDto : tableInfoDtoList) {
				List<TableColumnDescDto> descDtoList = columnDescDtoMap.get(tableInfoDto.getTableName());
				if (CollectionUtils.isNotEmpty(descDtoList)) {
					tableColumnsMap.put(tableInfoDto.getTableName(), descDtoList);
				}
			}
		}
		Map<String, Object> dbResultMap = new HashMap<>();
		dbResultMap.put("db", dbNameDtoList);
		dbResultMap.put("table", dbTableMap);
		dbResultMap.put("column", tableColumnsMap);
		// 缓存10分钟，如果10分钟内库里面增删改了表或字段，则提示不出来
		CacheUtil.put(cacheKey, dbResultMap, 6000);
		return DocDbResponseJson.ok(dbResultMap);
	}
	
	@PostMapping(value = "/getTableDdl")
	public ResponseJson getTableDdl(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		Map<String, String> dataMap = baseMapper.getTableDdl(dbName, tableName);
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		// 不同数据源类型获取方式不一致
		if (Objects.equals(DatabaseProduct.MYSQL, databaseFactoryBean.getDatabaseProduct())) {
			return DocDbResponseJson.ok(dataMap.get("Create Table"));
		}
		return DocDbResponseJson.ok("暂未支持的数据库类型");
	}
	
	@PostMapping(value = "/getDatabaseList")
	public ResponseJson getDatabaseList(Long sourceId) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<DatabaseInfoDto> dbNameDtoList = baseMapper.getDatabaseList();
		return DocDbResponseJson.ok(dbNameDtoList);
	}
	
	@PostMapping(value = "/getTableStatus")
	public ResponseJson getTableStatus(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		TableStatusVo tableStatusVo = baseMapper.getTableStatus(dbName, tableName);
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		tableStatusVo.setDbType(factoryBean.getDatabaseProduct().name().toLowerCase());
		return DocDbResponseJson.ok(tableStatusVo);
	}
	
	@PostMapping(value = "/getTableList")
	public ResponseJson getTableList(Long sourceId, String dbName) {
		BaseMapper baseMapper = this.getBaseMapper(sourceId);
		List<TableInfoDto> dbTableList = baseMapper.getTableList(dbName);
		return DocDbResponseJson.ok(dbTableList);
	}
	
	@PostMapping(value = "/getTableColumnList")
	public ResponseJson getTableColumnList(Long sourceId, String dbName, String tableName) {
		this.judgeAuth(sourceId, DbAuthType.VIEW.getName(), "没有查看该库表信息的权限");
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		if (databaseFactoryBean == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		TableColumnVo tableColumnVo = this.getTableColumnVo(databaseFactoryBean, dbName, tableName);
		return DocDbResponseJson.ok(tableColumnVo);
	}
	
	@PostMapping(value = "/getTableColumnDescList")
	public ResponseJson getTableColumnDescList(Long sourceId, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<TableColumnDescDto> columnDescDto = baseMapper.getTableColumnDescList(tableName);
		return DocDbResponseJson.ok(columnDescDto);
	}
	
	@PostMapping(value = "/getTableAndColumnBySearch")
	public ResponseJson getTableAndColumnBySearch(Long sourceId, String dbName, String searchText) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		if (StringUtils.isBlank(searchText)) {
			return DocDbResponseJson.ok();
		}
		searchText = "%" + searchText + "%";
		List<QueryTableColumnDescDto> columnDescDto = baseMapper.getTableAndColumnBySearch(dbName, searchText);
		return DocDbResponseJson.ok(columnDescDto);
	}
	
	@PostMapping(value = "/getTableDescList")
	public ResponseJson getTableDescList(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<TableDescDto> columnDescDto = baseMapper.getTableDescList(dbName, tableName);
		return DocDbResponseJson.ok(columnDescDto);
	}
	
	@PostMapping(value = "/updateTableDesc")
	public ResponseJson updateTableDesc(Long sourceId, String dbName, String tableName, String newDesc) {
		this.judgeAuth(sourceId, DbAuthType.DESC_EDIT.getName(), "没有修改该表注释的权限");
		BaseMapper baseMapper = this.getBaseMapper(sourceId);
		baseMapper.updateTableDesc(dbName, tableName, newDesc);
		return DocDbResponseJson.ok();
	}
	
	@PostMapping(value = "/updateTableColumnDesc")
	public ResponseJson updateTableColumnDesc(Long sourceId, String dbName, String tableName, String columnName, String newDesc) {
		this.judgeAuth(sourceId, DbAuthType.DESC_EDIT.getName(), "没有修改该表字段注释的权限");
		BaseMapper baseMapper = this.getBaseMapper(sourceId);
		ColumnInfoDto columnInfo = null;
		// mysql要同时修改类型默认值等，所以先查出来
		MysqlMapper mysqlMapper = databaseRegistrationBean.getBaseMapper(sourceId, MysqlMapper.class);
		if (mysqlMapper != null) {
			columnInfo = mysqlMapper.getColumnInfo(dbName, tableName, columnName);
			String isNullable = Optional.ofNullable(columnInfo.getIsNullable()).orElse("");
			columnInfo.setIsNullable("yes".equalsIgnoreCase(isNullable) ? "null" : "not null");
			String columnDefault = columnInfo.getColumnDefault();
			if (StringUtils.isNotBlank(columnDefault)) {
				columnInfo.setColumnDefault("DEFAULT " + columnDefault);
			} else {
				columnInfo.setColumnDefault("");
			}
			String extra = columnInfo.getExtra();
			columnInfo.setExtra(StringUtils.isBlank(extra) ? "" : extra);
		}
		baseMapper.updateTableColumnDesc(dbName, tableName, columnName, newDesc, columnInfo);
		return DocDbResponseJson.ok();
	}
	
	@GetMapping(value = "/exportDatabase")
	public ResponseJson exportDatabase(HttpServletResponse response, Long sourceId, String dbName, String tableNames, Integer exportType) {
		this.judgeAuth(sourceId, DbAuthType.VIEW.getName(), "没有查看该库表信息的权限");
		if (StringUtils.isBlank(tableNames)) {
			return DocDbResponseJson.warn("请选择需要导出的表");
		}
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		if (databaseFactoryBean == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		List<TableInfoVo> tableList = new LinkedList<>();
		Map<String, List<TableColumnDescDto>> columnList = new HashMap<>();
		String[] tableNameArr = tableNames.split(",");
		for (String tableName : tableNameArr) {
			if (StringUtils.isBlank(tableName)) {
				continue;
			}
			TableColumnVo tableColumnVo = this.getTableColumnVo(databaseFactoryBean, dbName, tableName);
			columnList.put(tableName, tableColumnVo.getColumnList());
			tableList.add(tableColumnVo.getTableInfo());
		}
		DatabaseExportVo exportVo = new DatabaseExportVo();
		exportVo.setColumnList(columnList);
		exportVo.setTableList(tableList);
		String content = JSON.toJSONString(exportVo);
		content = "var docDbDatabase = " + content;
		if (Objects.equals(exportType, 1)) {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=database.js");
			response.setCharacterEncoding("utf-8");
			// 将文件输入流写入response的输出流中
			try {
				IoUtil.write(response.getOutputStream(), "utf-8", true, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding("utf-8");
				String fileName = URLEncoder.encode("数据库表导出", "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
				ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
				WriteSheet writeSheet = EasyExcel.writerSheet(0, "表信息").head(TableInfoVo.class).build();
				excelWriter.write(tableList, writeSheet);
				int index = 1;
				for (Map.Entry<String, List<TableColumnDescDto>> entry : columnList.entrySet()) {
					writeSheet = EasyExcel.writerSheet(index++, entry.getKey()).head(TableColumnDescDto.class).build();
					excelWriter.write(entry.getValue(), writeSheet);
				}
				excelWriter.finish();
			} catch (IOException e) {
				e.printStackTrace();
				return DocDbResponseJson.error("导出失败：" + e.getMessage());
			}
		}
		return DocDbResponseJson.ok();
	}
	
	private TableColumnVo getTableColumnVo(DatabaseFactoryBean databaseFactoryBean, String dbName, String tableName) {
		SqlSessionTemplate sessionTemplate = databaseFactoryBean.getSqlSessionTemplate();
		BaseMapper baseMapper = sessionTemplate.getMapper(BaseMapper.class);
		List<TableColumnDescDto> columnDescDto = baseMapper.getTableColumnList(dbName, tableName);
		// SQLSERVER要单独查字段注释
		if (databaseFactoryBean.getDatabaseProduct() == DatabaseProduct.SQLSERVER) {
			List<TableColumnDescDto> columnDescList = baseMapper.getTableColumnDescList(tableName);
			Map<String, TableColumnDescDto> columnMap = columnDescDto.stream().collect(Collectors.toMap(TableColumnDescDto::getName, val -> val));
			// 字段注释
			for (TableColumnDescDto descDto : columnDescList) {
				TableColumnDescDto tempDesc = columnMap.get(descDto.getName());
				if (tempDesc != null) {
					tempDesc.setDescription(descDto.getDescription());
				}
			}
		}
		TableColumnVo tableColumnVo = new TableColumnVo();
		tableColumnVo.setColumnList(columnDescDto);
		// 表注释
		TableInfoVo tableInfoVo = new TableInfoVo();
		List<TableDescDto> tableDescList = baseMapper.getTableDescList(dbName, tableName);
		String description = null;
		if (tableDescList.size() > 0) {
			TableDescDto descDto = tableDescList.get(0);
			description = descDto.getDescription();
		}
		description = Optional.ofNullable(description).orElse("");
		tableInfoVo.setDescription(description);
		tableInfoVo.setTableName(tableName);
		tableColumnVo.setTableInfo(tableInfoVo);
		return tableColumnVo;
	}
	
	/**
	 * 权限判断
	 *
	 * @author 暮光：城中城
	 */
	private void judgeAuth(Long sourceId, String authName, String noAuthInfo) {
		if (!DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE)
				&& !DocUserUtil.haveCustomAuth(authName, DocAuthConst.DB + sourceId)) {
			throw new ConfirmException(noAuthInfo);
		}
	}
	
	/**
	 * 获取BaseMapper
	 *
	 * @author 暮光：城中城
	 */
	private BaseMapper getBaseMapper(Long sourceId) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapperById(sourceId);
		if (baseMapper == null) {
			throw new ConfirmException("未找到对应的数据库连接");
		}
		return baseMapper;
	}
	
	/**
	 * 判断查看权和获取BaseMapper
	 *
	 * @author 暮光：城中城
	 */
	private BaseMapper getViewAuthBaseMapper(Long sourceId) {
		this.judgeAuth(sourceId, DbAuthType.VIEW.getName(), "没有查看该库表信息的权限");
		return this.getBaseMapper(sourceId);
	}
	
	public static void main(String[] args) {
		//File zipFile = ZipUtil.zip("d:/aaa");
		File zipFile = new File("d:/111.zip");
		ZipUtil.zip(zipFile, true, new File("d:/111.txt"),
				new File("d:/222.txt"), new File("d:/aaa"));
	}
}

