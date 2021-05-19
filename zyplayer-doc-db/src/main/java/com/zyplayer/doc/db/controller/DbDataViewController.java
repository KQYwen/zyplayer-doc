package com.zyplayer.doc.db.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.util.StringUtil;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.utils.JSONUtil;
import com.zyplayer.doc.db.service.DbBaseFactory;
import com.zyplayer.doc.db.service.DbBaseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
	SqlExecutor sqlExecutor;
	@Resource
	DbBaseFactory dbBaseFactory;
	
	@PostMapping(value = "/query")
	public ResponseJson execute(DataViewParam dataViewParam) {
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		boolean select = DocUserUtil.haveCustomAuth(DbAuthType.SELECT.getName(), DocAuthConst.DB + dataViewParam.getSourceId());
		boolean update = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocAuthConst.DB + dataViewParam.getSourceId());
		if (!manageAuth && !select && !update) {
			return DocDbResponseJson.warn("没有该数据源的执行权限");
		}
		// 数据查询
		ExecuteType executeType = (manageAuth || update) ? ExecuteType.ALL : ExecuteType.SELECT;
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(dataViewParam.getSourceId());
		String queryPageSql = dbBaseService.getQueryPageSql(dataViewParam);
		ExecuteResult executeResult = this.query(dataViewParam.getSourceId(), dataViewParam.getExecuteId(), executeType, queryPageSql);
		// 数据组装
		List<String> resultList = new LinkedList<>();
		resultList.add(JSON.toJSONString(executeResult, JSONUtil.serializeConfig, SerializerFeature.WriteMapNullValue));
		DocDbResponseJson responseJson = DocDbResponseJson.ok(resultList);
		// 计算总条数，第一页才查询总条数
		if (CollectionUtils.isNotEmpty(executeResult.getResult()) && Objects.equals(dataViewParam.getPageNum(), 1)) {
			responseJson.setTotal((long) executeResult.getResult().size());
			if (executeResult.getResult().size() >= dataViewParam.getPageSize()) {
				String queryCountSql = dbBaseService.getQueryCountSql(dataViewParam);
				ExecuteResult countResult = this.query(dataViewParam.getSourceId(), dataViewParam.getExecuteId(), executeType, queryCountSql);
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
}

