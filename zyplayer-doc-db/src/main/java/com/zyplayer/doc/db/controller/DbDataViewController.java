package com.zyplayer.doc.db.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.util.StringUtil;
import com.zyplayer.doc.db.controller.download.FormatDownloadConst;
import com.zyplayer.doc.db.controller.download.FormatDownloadService;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.utils.JSONUtil;
import com.zyplayer.doc.db.service.common.ExecuteAuthService;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
	ExecuteAuthService executeAuthService;
	@Resource
	SqlExecutor sqlExecutor;
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	@Resource
	Map<String, FormatDownloadService> formatDownloadServiceMap;
	// 最大允许导出的行数，设置的过大有可能会导致内存溢出，默认10W条
	@Value("${zyplayer.doc.db.download-max-row:100000}")
	Integer downloadMaxRow;
	
	/**
	 * 数据查询接口
	 *
	 * @author 暮光：城中城
	 * @since 2021-08-14
	 */
	@PostMapping(value = "/query")
	public ResponseJson query(DataViewParam param) {
		// 数据查询
		ExecuteType executeType = executeAuthService.getExecuteType(param.getSourceId());
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
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
				responseJson.setTotal(this.getDataCount(param, executeType));
			}
		}
		return responseJson;
	}
	
	/**
	 * 删除表数据
	 *
	 * @author 暮光：城中城
	 */
	@PostMapping(value = "/deleteTableLineData")
	public ResponseJson deleteTableLineData(Long sourceId, String dbName, String tableName, String lineJson) {
		JSONArray lineJsonArr = JSON.parseArray(lineJson);
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		dbBaseService.deleteTableLineData(sourceId, dbName, tableName, lineJsonArr);
		return DocDbResponseJson.ok();
	}
	
	/**
	 * 多表下载
	 *
	 * @param response
	 * @param param
	 * @return
	 * @author 暮光：城中城
	 */
	@PostMapping(value = "/downloadMultiple")
	public ResponseJson downloadMultiple(HttpServletResponse response, DataViewParam param) {
		if (StringUtils.isBlank(param.getTableNames())) {
			return DocDbResponseJson.warn("请选择导出的表");
		}
		param.setExecuteId(IdUtil.simpleUUID());
		String[] tableNameArr = param.getTableNames().split(",");
		try {
			// 先校验总条数是否超过限制
			for (String tableName : tableNameArr) {
				param.setTableName(tableName);
				ExecuteType executeType = executeAuthService.getExecuteType(param.getSourceId());
				if (this.getDataCount(param, executeType) > downloadMaxRow) {
					return DocDbResponseJson.error(String.format("导出失败，表：%s 数据行数超过最大导出配置 %s，请联系管理员修改", tableName, downloadMaxRow));
				}
			}
			FormatDownloadService formatDownloadService = formatDownloadServiceMap.getOrDefault(param.getDownloadType(), formatDownloadServiceMap.get(FormatDownloadConst.COMMON));
			formatDownloadService.download(response, param, tableNameArr);
		} catch (Exception e) {
			e.printStackTrace();
			return DocDbResponseJson.error("导出失败：" + e.getMessage());
		}
		return DocDbResponseJson.ok();
	}
	
	/**
	 * 获取数据总条数
	 *
	 * @author 暮光：城中城
	 * @since 2021-08-14
	 */
	private Long getDataCount(DataViewParam param, ExecuteType executeType) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
		String queryCountSql = dbBaseService.getQueryCountSql(param);
		ExecuteResult countResult = this.query(param.getSourceId(), param.getExecuteId(), executeType, queryCountSql);
		if (CollectionUtils.isNotEmpty(countResult.getResult()) && MapUtils.isNotEmpty(countResult.getResult().get(0))) {
			Map<String, Object> countMap = countResult.getResult().get(0);
			Optional<Object> countAny = countMap.values().stream().findAny();
			return TypeUtils.castToLong(countAny.orElse(0));
		}
		return 0L;
	}
	
	/**
	 * 执行数据查询
	 *
	 * @param sourceId
	 * @param executeId
	 * @param executeType
	 * @param executeSql
	 * @return
	 * @author 暮光：城中城
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
}

