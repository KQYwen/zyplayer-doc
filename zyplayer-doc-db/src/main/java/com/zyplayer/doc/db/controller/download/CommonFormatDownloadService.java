package com.zyplayer.doc.db.controller.download;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.util.ZyplayerDocVersion;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.service.common.ExecuteAuthService;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import com.zyplayer.doc.db.service.download.BaseDownloadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 公共的格式化数据下载服务，适用于insert、update、json
 *
 * @author 暮光：城中城
 * @since 2021-08-14
 */
@Service(FormatDownloadConst.COMMON)
public class CommonFormatDownloadService implements FormatDownloadService {
	
	@Resource
	ExecuteAuthService executeAuthService;
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	@Resource
	BaseDownloadService baseDownloadService;
	
	@Override
	public void download(HttpServletResponse response, DataViewParam param, String[] tableNameArr) throws Exception {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
		boolean multipleFile = Objects.equals(param.getDownloadFileType(), 2);
		boolean isJson = Objects.equals(param.getDownloadType(), FormatDownloadConst.JSON);
		boolean isUpdate = Objects.equals(param.getDownloadType(), FormatDownloadConst.UPDATE);
		// 此服务支持的下载格式判断
		if (!FormatDownloadConst.INSERT.equals(param.getDownloadType())
				&& !FormatDownloadConst.UPDATE.equals(param.getDownloadType())
				&& !FormatDownloadConst.JSON.equals(param.getDownloadType())) {
			throw new ConfirmException("不支持的数据下载格式");
		}
		JSONObject conditionMap = StringUtils.isBlank(param.getConditionJson()) ? new JSONObject() : JSON.parseObject(param.getConditionJson());
		JSONObject conditionColumnMap = StringUtils.isBlank(param.getConditionColumnJson()) ? new JSONObject() : JSON.parseObject(param.getConditionColumnJson());
		JSONObject getRetainColumnMap = StringUtils.isBlank(param.getRetainColumnJson()) ? new JSONObject() : JSON.parseObject(param.getRetainColumnJson());
		// 结果
		StringBuilder resultSb = new StringBuilder("/*\n" +
				" 数据库       : " + param.getDbName() + "\n" +
				" 数据库类型   : " + dbBaseService.getDatabaseProduct().name() + "\n" +
				" 导出时间     : " + DateTime.now().toString() + "\n" +
				" 导出软件     : zyplayer-doc\n" +
				" 软件版本     : " + ZyplayerDocVersion.version + "\n" +
				"*/\n\n");
		String tempDir = System.getProperty("java.io.tmpdir");
		String tempDirName = tempDir + "zyplayer-doc-" + IdUtil.fastSimpleUUID();
		try {
			// 创建临时文件夹
			FileUtil.mkdir(tempDirName);
			// 再分表查数据
			String suffix = isJson ? ".json" : ".sql";
			for (String tableName : tableNameArr) {
				param.setTableName(tableName);
				param.setCondition(conditionMap.getString(tableName));
				param.setConditionColumn(conditionColumnMap.getString(tableName));
				param.setRetainColumn(getRetainColumnMap.getString(tableName));
				ExecuteType executeType = executeAuthService.getExecuteType(param.getSourceId());
				// 获取列信息等
				TableColumnVo tableColumnVo = dbBaseService.getTableColumnList(param.getSourceId(), param.getDbName(), tableName);
				List<TableColumnDescDto> columnList = tableColumnVo.getColumnList();
				// 默认找主键作为更新条件
				Set<String> primaryKeyColumnSet = columnList.stream().filter(item -> Objects.equals(item.getPrimaryKey(), "1")).map(TableColumnDescDto::getName).collect(Collectors.toSet());
				// 更新条件列
				Set<String> conditionColumnSet = new HashSet<>();
				if (isUpdate) {
					conditionColumnSet.addAll(StringUtils.isBlank(param.getConditionColumn()) ? primaryKeyColumnSet : Stream.of(param.getConditionColumn().split(",")).collect(Collectors.toSet()));
				}
				// 保留的列
				Set<String> retainColumnSet = StringUtils.isBlank(param.getRetainColumn()) ? Collections.emptySet() : Stream.of(param.getRetainColumn().split(",")).collect(Collectors.toSet());
				List<TableColumnDescDto> columnListRetain = columnList.stream().filter(item -> retainColumnSet.isEmpty() || retainColumnSet.contains(item.getName()) || conditionColumnSet.contains(item.getName())).collect(Collectors.toList());
				// 数据查询
				String queryAllSql = dbBaseService.getQueryAllSql(param);
				ExecuteParam executeParam = new ExecuteParam();
				executeParam.setDatasourceId(param.getSourceId());
				executeParam.setExecuteId(param.getExecuteId());
				executeParam.setExecuteType(executeType);
				executeParam.setSql(queryAllSql);
				String downloadTableData = dbBaseService.getDownloadTableData(param, executeParam, columnListRetain, conditionColumnSet);
				// 写入临时文件
				if (multipleFile) {
					File tempFile = FileUtil.file(tempDirName + "/" + tableName + suffix);
					String tableDataSb = isJson ? downloadTableData : resultSb + String.format("-- 导出数据表：`%s`.`%s` --\n", param.getDbName(), tableName) + downloadTableData;
					FileUtil.writeUtf8String(tableDataSb, tempFile);
				} else {
					resultSb.append(String.format("-- 导出数据表：`%s`.`%s` --\n", param.getDbName(), tableName));
					resultSb.append(downloadTableData).append("\n");
				}
			}
			if (multipleFile) {
				baseDownloadService.sendResponse(response, param.getDbName(), tempDirName);
			} else {
				baseDownloadService.sendResponse(response, param.getDbName(), suffix, resultSb.toString());
			}
		} finally {
			FileUtil.del(tempDirName);
		}
	}
}
