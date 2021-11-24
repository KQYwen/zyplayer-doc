package com.zyplayer.doc.db.controller;

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
import com.zyplayer.doc.db.controller.vo.DatabaseExportVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo.TableInfoVo;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.controller.vo.TableStatusVo;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.dto.*;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.utils.PoiUtil;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	
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
		wrapper.select("id", "name", "group_name");
		List<DbDatasource> datasourceList = dbDatasourceService.list(wrapper);
		return DocDbResponseJson.ok(datasourceList);
	}
	
	/**
	 * 获取数据源基本信息
	 *
	 * @param sourceId 数据源ID
	 * @return 基本信息
	 */
	@PostMapping(value = "/getSourceBaseInfo")
	public ResponseJson getSourceBaseInfo(Long sourceId) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		Map<String, Object> dbResultMap = new HashMap<>();
		dbResultMap.put("product", dbBaseService.getDatabaseProduct().name().toLowerCase());
		return DocDbResponseJson.ok(dbResultMap);
	}
	
	@PostMapping(value = "/getTableDdl")
	public ResponseJson getTableDdl(Long sourceId, String dbName, String tableName) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		TableDdlVo tableDdlVo = dbBaseService.getTableDdl(sourceId, dbName, tableName);
		return DocDbResponseJson.ok(tableDdlVo);
	}
	
	@PostMapping(value = "/getDatabaseList")
	public ResponseJson getDatabaseList(Long sourceId) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		List<DatabaseInfoDto> databaseList = dbBaseService.getDatabaseList(sourceId);
		return DocDbResponseJson.ok(databaseList);
	}
	
	@PostMapping(value = "/getTableStatus")
	public ResponseJson getTableStatus(Long sourceId, String dbName, String tableName) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		TableStatusVo tableStatusVo = dbBaseService.getTableStatus(sourceId, dbName, tableName);
		return DocDbResponseJson.ok(tableStatusVo);
	}
	
	@PostMapping(value = "/getTableList")
	public ResponseJson getTableList(Long sourceId, String dbName) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		List<TableInfoDto> tableList = dbBaseService.getTableList(sourceId, dbName);
		return DocDbResponseJson.ok(tableList);
	}
	
	@PostMapping(value = "/getTableColumnList")
	public ResponseJson getTableColumnList(Long sourceId, String dbName, String tableName) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		TableColumnVo tableColumnVo = dbBaseService.getTableColumnList(sourceId, dbName, tableName);
		return DocDbResponseJson.ok(tableColumnVo);
	}
	
	@PostMapping(value = "/getTableAndColumnBySearch")
	public ResponseJson getTableAndColumnBySearch(Long sourceId, String dbName, String searchText) {
		if (StringUtils.isBlank(searchText)) {
			return DocDbResponseJson.ok();
		}
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		List<QueryTableColumnDescDto> columnDescDto = dbBaseService.getTableAndColumnBySearch(sourceId, dbName, searchText);
		return DocDbResponseJson.ok(columnDescDto);
	}
	
	@PostMapping(value = "/getTableDescList")
	public ResponseJson getTableDescList(Long sourceId, String dbName, String tableName) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		List<TableDescDto> tableDescList = dbBaseService.getTableDescList(sourceId, dbName, tableName);
		return DocDbResponseJson.ok(tableDescList);
	}
	
	@PostMapping(value = "/updateTableDesc")
	public ResponseJson updateTableDesc(Long sourceId, String dbName, String tableName, String newDesc) {
		this.judgeAuth(sourceId, DbAuthType.DESC_EDIT.getName(), "没有修改该表注释的权限");
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		dbBaseService.updateTableDesc(sourceId, dbName, tableName, newDesc);
		return DocDbResponseJson.ok();
	}
	
	@PostMapping(value = "/updateTableColumnDesc")
	public ResponseJson updateTableColumnDesc(Long sourceId, String dbName, String tableName, String columnName, String newDesc) {
		this.judgeAuth(sourceId, DbAuthType.DESC_EDIT.getName(), "没有修改该表字段注释的权限");
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		dbBaseService.updateTableColumnDesc(sourceId, dbName, tableName, columnName, newDesc);
		return DocDbResponseJson.ok();
	}
	
	@PostMapping(value = "/exportDatabase")
	public ResponseJson exportDatabase(HttpServletResponse response, Long sourceId, String dbName, String tableNames, Integer exportType, Integer exportFormat) {
		if (StringUtils.isBlank(tableNames)) {
			return DocDbResponseJson.warn("请选择需要导出的表");
		}
		List<String> tableNameList = Stream.of(tableNames.split(",")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
		if (Objects.equals(exportType, 1)) {
			return this.exportForTableDoc(response, sourceId, dbName, tableNameList, exportFormat);
		} else if (Objects.equals(exportType, 2)) {
			return this.exportForTableDdl(response, sourceId, dbName, tableNameList, exportFormat);
		}
		return DocDbResponseJson.ok();
	}
	
	private DocDbResponseJson exportForTableDdl(HttpServletResponse response, Long sourceId, String dbName, List<String> tableNameList, Integer exportFormat) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		Map<String, String> ddlSqlMap = new HashMap<>();
		for (String tableName : tableNameList) {
			TableDdlVo tableDdlVo = dbBaseService.getTableDdl(sourceId, dbName, tableName);
			ddlSqlMap.put(tableName, tableDdlVo.getTableDDLByType());
		}
		try {
			DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
			DatabaseProductEnum databaseProduct = databaseFactoryBean.getDatabaseProduct();
			PoiUtil.exportByDdl(ddlSqlMap, dbName, databaseProduct.name(), response);
		} catch (Exception e) {
			e.printStackTrace();
			return DocDbResponseJson.error("导出失败：" + e.getMessage());
		}
		return DocDbResponseJson.ok();
	}
	
	private DocDbResponseJson exportForTableDoc(HttpServletResponse response, Long sourceId, String dbName, List<String> tableNameList, Integer exportFormat) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		// 数据组装
		List<TableInfoVo> tableList = new LinkedList<>();
		Map<String, List<TableColumnDescDto>> columnList = new HashMap<>();
		for (String tableName : tableNameList) {
			TableColumnVo tableColumnVo = dbBaseService.getTableColumnList(sourceId, dbName, tableName);
			columnList.put(tableName, tableColumnVo.getColumnList());
			tableList.add(tableColumnVo.getTableInfo());
		}
		try {
			DatabaseExportVo exportVo = new DatabaseExportVo(columnList, tableList);
			if (Objects.equals(exportFormat, 1)) {
				PoiUtil.exportByText(exportVo, response);
			} else if (Objects.equals(exportFormat, 2)) {
				PoiUtil.exportByXlsx(exportVo, response);
			} else if (Objects.equals(exportFormat, 3)) {
				PoiUtil.exportByDocx(dbName, exportVo, response);
			}
			return DocDbResponseJson.error("导出失败：请先选择导出类型");
		} catch (Exception e) {
			e.printStackTrace();
			return DocDbResponseJson.error("导出失败：" + e.getMessage());
		}
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
}

