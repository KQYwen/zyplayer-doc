package com.zyplayer.doc.db.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.db.controller.vo.DatabaseExportVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo.TableInfoVo;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean.DatabaseProduct;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.dto.*;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.db.mapper.mysql.MysqlMapper;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
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

	@Autowired
	DatabaseRegistrationBean databaseRegistrationBean;

	@PostMapping(value = "/getDataSourceList")
	public ResponseJson getDataSourceList() {
		List<DatabaseFactoryBean> factoryBeanList = databaseRegistrationBean.getDatabaseFactoryBeanList();
		Set<String> dataSourceList = factoryBeanList.stream().collect(Collectors.mapping(DatabaseFactoryBean::getHost, Collectors.toSet()));
		return DocDbResponseJson.ok(dataSourceList);
	}

	@PostMapping(value = "/getDatabaseList")
	public ResponseJson getDatabaseList(String host) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapperByHost(host);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		List<DatabaseInfoDto> dbNameDtoList = baseMapper.getDatabaseList();
		return DocDbResponseJson.ok(dbNameDtoList);
	}

	@PostMapping(value = "/getTableList")
	public ResponseJson getTableList(String host, String dbName) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapper(host, dbName);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		List<TableInfoDto> dbTableList = baseMapper.getTableList(dbName);
		return DocDbResponseJson.ok(dbTableList);
	}

	@PostMapping(value = "/getTableColumnList")
	public ResponseJson getTableColumnList(String host, String dbName, String tableName) {
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getDatabaseFactoryBean(host, dbName);
		if (databaseFactoryBean == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		TableColumnVo tableColumnVo = this.getTableColumnVo(databaseFactoryBean, dbName, tableName);
		return DocDbResponseJson.ok(tableColumnVo);
	}

	@PostMapping(value = "/getTableColumnDescList")
	public ResponseJson getTableColumnDescList(String host, String dbName, String tableName) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapper(host, dbName);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		List<TableColumnDescDto> columnDescDto = baseMapper.getTableColumnDescList(tableName);
		return DocDbResponseJson.ok(columnDescDto);
	}

	@PostMapping(value = "/getTableAndColumnBySearch")
	public ResponseJson getTableAndColumnBySearch(String host, String dbName, String tableName, String searchText) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapper(host, dbName);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		if (StringUtils.isNotBlank(searchText)) {
			searchText = "%" + searchText + "%";
		}
		List<QueryTableColumnDescDto> columnDescDto = baseMapper.getTableAndColumnBySearch(dbName, searchText);
		return DocDbResponseJson.ok(columnDescDto);
	}

	@PostMapping(value = "/getTableDescList")
	public ResponseJson getTableDescList(String host, String dbName, String tableName) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapper(host, dbName);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		List<TableDescDto> columnDescDto = baseMapper.getTableDescList(tableName);
		return DocDbResponseJson.ok(columnDescDto);
	}

	@PostMapping(value = "/updateTableDesc")
	public ResponseJson updateTableDesc(String host, String dbName, String tableName, String newDesc) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapper(host, dbName);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		baseMapper.updateTableDesc(dbName, tableName, newDesc);
		return DocDbResponseJson.ok();
	}

	@PostMapping(value = "/updateTableColumnDesc")
	public ResponseJson updateTableColumnDesc(String host, String dbName, String tableName, String columnName, String newDesc) {
		BaseMapper baseMapper = databaseRegistrationBean.getBaseMapper(host, dbName);
		if (baseMapper == null) {
			return DocDbResponseJson.warn("未找到对应的数据库连接");
		}
		ColumnInfoDto columnInfo = null;
		// mysql要同时修改类型默认值等，所以先查出来
		MysqlMapper mysqlMapper = databaseRegistrationBean.getBaseMapper(host, dbName, MysqlMapper.class);
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
	public ResponseJson exportDatabase(HttpServletResponse response, String host, String dbName, String tableNames) {
		if (StringUtils.isBlank(tableNames)) {
			return DocDbResponseJson.warn("请选择需要导出的表");
		}
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getDatabaseFactoryBean(host, dbName);
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
		content = "var database = " + content;
		
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=database.js");
		response.setCharacterEncoding("utf-8");
		// 将文件输入流写入response的输出流中
		try {
			IoUtil.write(response.getOutputStream(), "utf-8", true, content);
		} catch (Exception e) {
			e.printStackTrace();
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
				if(tempDesc != null) {
					tempDesc.setDescription(descDto.getDescription());
				}
			}
		}
		TableColumnVo tableColumnVo = new TableColumnVo();
		tableColumnVo.setColumnList(columnDescDto);
		// 表注释
		TableInfoVo tableInfoVo = new TableInfoVo();
		List<TableDescDto> tableDescList = baseMapper.getTableDescList(tableName);
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
	
	public static void main(String[] args) {
		//File zipFile = ZipUtil.zip("d:/aaa");
		File zipFile = new File("d:/111.zip");
		ZipUtil.zip(zipFile, true, new File("d:/111.txt"),
				new File("d:/222.txt"), new File("d:/aaa"));
	}
}

