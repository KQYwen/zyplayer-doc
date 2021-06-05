package com.zyplayer.doc.db.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.util.StringUtil;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.utils.JSONUtil;
import com.zyplayer.doc.db.service.DbBaseFactory;
import com.zyplayer.doc.db.service.DbBaseService;
import com.zyplayer.doc.db.service.download.BaseDownloadService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表数据查看控制器
 *
 * @author 暮光：城中城
 * @since 2021年5月16日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/data-view")
public class DbDataViewController {
	private static Logger logger = LoggerFactory.getLogger(DbDataViewController.class);
	
	@Resource
	SqlExecutor sqlExecutor;
	@Resource
	DbBaseFactory dbBaseFactory;
	@Resource
	BaseDownloadService baseDownloadService;
	
	@PostMapping(value = "/query")
	public ResponseJson query(DataViewParam param) {
		// 数据查询
		ExecuteType executeType = this.getExecuteType(param.getSourceId());
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(param.getSourceId());
		String queryPageSql = dbBaseService.getQueryPageSql(param);
		ExecuteResult executeResult = this.query(param.getSourceId(), param.getExecuteId(), executeType, queryPageSql);
		// 数据组装
		List<String> resultList = new LinkedList<>();
		resultList.add(JSON.toJSONString(executeResult, JSONUtil.serializeConfig, SerializerFeature.WriteMapNullValue));
		DocDbResponseJson responseJson = DocDbResponseJson.ok(resultList);
		// 计算总条数，第一页才查询总条数
		if (CollectionUtils.isNotEmpty(executeResult.getResult()) && Objects.equals(param.getPageNum(), 1)) {
			responseJson.setTotal((long) executeResult.getResult().size());
			if (executeResult.getResult().size() >= param.getPageSize()) {
				String queryCountSql = dbBaseService.getQueryCountSql(param);
				ExecuteResult countResult = this.query(param.getSourceId(), param.getExecuteId(), executeType, queryCountSql);
				if (CollectionUtils.isNotEmpty(countResult.getResult()) && MapUtils.isNotEmpty(countResult.getResult().get(0))) {
					Map<String, Object> countMap = countResult.getResult().get(0);
					Optional<Object> countAny = countMap.values().stream().findAny();
					countAny.ifPresent(count -> responseJson.setTotal(TypeUtils.castToLong(count)));
				}
			}
		}
		return responseJson;
	}
	
	/**
	 * 单表数据下载
	 *
	 * @param response
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/download")
	public ResponseJson download(HttpServletResponse response, DataViewParam param) {
		param.setExecuteId(RandomUtil.simpleUUID());
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(param.getSourceId());
		// 获取列信息等
		TableColumnVo tableColumnVo = dbBaseService.getTableColumnList(param.getSourceId(), param.getDbName(), param.getTableName());
		Set<String> conditionSet = StringUtils.isBlank(param.getConditionColumn()) ? Collections.emptySet() : Stream.of(param.getConditionColumn().split(",")).collect(Collectors.toSet());
		Set<String> retainColumnSet = StringUtils.isBlank(param.getRetainColumn()) ? Collections.emptySet() : Stream.of(param.getRetainColumn().split(",")).collect(Collectors.toSet());
		List<TableColumnDescDto> columnList = tableColumnVo.getColumnList().stream().filter(item -> retainColumnSet.isEmpty() || retainColumnSet.contains(item.getName())).collect(Collectors.toList());
		// 数据查询
		String queryAllSql = dbBaseService.getQueryAllSql(param);
		ExecuteParam executeParam = new ExecuteParam();
		executeParam.setDatasourceId(param.getSourceId());
		executeParam.setExecuteId(param.getExecuteId());
		executeParam.setExecuteType(this.getExecuteType(param.getSourceId()));
		executeParam.setSql(queryAllSql);
		// 最大支持10w行数据，总得有个上限
		executeParam.setMaxRows(100000);
		try {
			dbBaseService.downloadSingleTableData(response, param, executeParam, columnList, conditionSet);
		} catch (Exception e) {
			return DocDbResponseJson.error("导出失败：" + e.getMessage());
		}
		return DocDbResponseJson.ok();
	}
	
	/**
	 * 多表下载
	 *
	 * @param response
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/downloadMultiple")
	public ResponseJson downloadMultiple(HttpServletResponse response, DataViewParam param) {
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(param.getSourceId());
		if (StringUtils.isBlank(param.getTableNames())) {
			return DocDbResponseJson.warn("请选择导出的表");
		}
		param.setExecuteId(RandomUtil.simpleUUID());
		StringBuilder resultSb = new StringBuilder("/*\n" +
				" zyplayer-doc 表数据导出\n" +
				"\n" +
				" 数据库       : " + param.getDbName() + "\n" +
				" 数据库类型   : " + dbBaseService.getDatabaseProduct().name() + "\n" +
				" 导出时间     : " + DateTime.now().toString() + "\n" +
				"*/\n\n");
		String[] tableNameArr = param.getTableNames().split(",");
		try {
			for (String tabName : tableNameArr) {
				param.setTableName(tabName);
				// 获取列信息等
				TableColumnVo tableColumnVo = dbBaseService.getTableColumnList(param.getSourceId(), param.getDbName(), tabName);
				List<TableColumnDescDto> columnList = tableColumnVo.getColumnList();
				// 找主键作为更新条件
				Set<String> conditionSet = columnList.stream().filter(item -> Objects.equals(item.getIspramary(), "1")).map(TableColumnDescDto::getName).collect(Collectors.toSet());
				// 数据查询
				String queryAllSql = dbBaseService.getQueryAllSql(param);
				ExecuteParam executeParam = new ExecuteParam();
				executeParam.setDatasourceId(param.getSourceId());
				executeParam.setExecuteId(param.getExecuteId());
				executeParam.setExecuteType(this.getExecuteType(param.getSourceId()));
				executeParam.setSql(queryAllSql);
				// 最大支持10w行数据，总得有个上限
				executeParam.setMaxRows(100000);
				String downloadTableData = dbBaseService.getDownloadTableData(param, executeParam, columnList, conditionSet);
				resultSb.append(String.format("-- 导出数据表：`%s`.`%s` --\n", param.getDbName(), tabName));
				resultSb.append(downloadTableData).append("\n");
			}
			String prefix = Objects.equals(param.getDownloadType(), "json") ? ".json" : ".sql";
			baseDownloadService.sendResponse(response, param.getDbName(), prefix, resultSb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return DocDbResponseJson.error("导出失败：" + e.getMessage());
		}
		return DocDbResponseJson.ok();
	}
	
	/**
	 * 执行数据查询
	 *
	 * @param sourceId
	 * @param executeId
	 * @param executeType
	 * @param executeSql
	 * @return
	 */
	private ExecuteResult query(Long sourceId, String executeId, ExecuteType executeType, String executeSql) {
		try {
			ExecuteParam executeParam = new ExecuteParam();
			executeParam.setDatasourceId(sourceId);
			executeParam.setExecuteId(executeId);
			executeParam.setExecuteType(executeType);
			executeParam.setSql(executeSql);
			executeParam.setMaxRows(1000);
			return sqlExecutor.execute(executeParam);
		} catch (Exception e) {
			logger.error("执行出错", e);
			return ExecuteResult.error(StringUtil.getException(e), executeSql);
		}
	}
	
	private ExecuteType getExecuteType(Long sourceId) {
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		boolean select = DocUserUtil.haveCustomAuth(DbAuthType.SELECT.getName(), DocAuthConst.DB + sourceId);
		boolean update = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocAuthConst.DB + sourceId);
		if (!manageAuth && !select && !update) {
			throw new ConfirmException("没有该数据源的执行权限");
		}
		return (manageAuth || update) ? ExecuteType.ALL : ExecuteType.SELECT;
	}
}

