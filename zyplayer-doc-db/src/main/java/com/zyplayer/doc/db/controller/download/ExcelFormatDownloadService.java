package com.zyplayer.doc.db.controller.download;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.service.common.ExecuteAuthService;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import com.zyplayer.doc.db.service.download.BaseDownloadService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * excel格式化数据下载服务
 *
 * @author 暮光：城中城
 * @since 2021-08-14
 */
@Service(FormatDownloadConst.EXCEL)
public class ExcelFormatDownloadService implements FormatDownloadService {
	private static Logger logger = LoggerFactory.getLogger(ExcelFormatDownloadService.class);
	
	@Resource
	ExecuteAuthService executeAuthService;
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	@Resource
	BaseDownloadService baseDownloadService;
	@Resource
	SqlExecutor sqlExecutor;
	
	@Override
	public void download(HttpServletResponse response, DataViewParam param, String[] tableNameArr) throws Exception {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
		boolean multipleFile = Objects.equals(param.getDownloadFileType(), 2);
		JSONObject conditionMap = StringUtils.isBlank(param.getConditionJson()) ? new JSONObject() : JSON.parseObject(param.getConditionJson());
		JSONObject conditionColumnMap = StringUtils.isBlank(param.getConditionColumnJson()) ? new JSONObject() : JSON.parseObject(param.getConditionColumnJson());
		JSONObject getRetainColumnMap = StringUtils.isBlank(param.getRetainColumnJson()) ? new JSONObject() : JSON.parseObject(param.getRetainColumnJson());
		// 结果
		String tempDir = System.getProperty("java.io.tmpdir");
		String tempDirName = tempDir + "zyplayer-doc-" + IdUtil.fastSimpleUUID();
		ExcelWriter excelWriter = null;
		boolean excelWriterIsFinish = false;
		try {
			String suffix = ".xlsx";
			if (!multipleFile) {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding("utf-8");
				String fileNameOrigin = "数据库表数据导出." + DateTime.now().toString("yyyyMMddHHmmss");
				String fileName = URLEncoder.encode(fileNameOrigin, "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName + suffix);
				excelWriter = EasyExcel.write(response.getOutputStream()).build();
			}
			// 创建临时文件夹
			FileUtil.mkdir(tempDirName);
			// 再分表查数据
			int tableIndex = 0;
			for (String tableName : tableNameArr) {
				param.setTableName(tableName);
				param.setCondition(conditionMap.getString(tableName));
				param.setConditionColumn(conditionColumnMap.getString(tableName));
				param.setRetainColumn(getRetainColumnMap.getString(tableName));
				ExecuteType executeType = executeAuthService.getExecuteType(param.getSourceId());
				// 获取列信息等
				TableColumnVo tableColumnVo = dbBaseService.getTableColumnList(param.getSourceId(), param.getDbName(), tableName);
				List<TableColumnDescDto> columnList = tableColumnVo.getColumnList();
				// 保留的列
				Set<String> retainColumnSet = StringUtils.isBlank(param.getRetainColumn()) ? Collections.emptySet() : Stream.of(param.getRetainColumn().split(",")).collect(Collectors.toSet());
				List<TableColumnDescDto> columnListRetain = columnList.stream().filter(item -> retainColumnSet.isEmpty() || retainColumnSet.contains(item.getName())).collect(Collectors.toList());
				// 数据查询
				String queryAllSql = dbBaseService.getQueryAllSql(param);
				ExecuteParam executeParam = new ExecuteParam();
				executeParam.setDatasourceId(param.getSourceId());
				executeParam.setExecuteId(param.getExecuteId());
				executeParam.setExecuteType(executeType);
				executeParam.setSql(queryAllSql);
				ExecuteResult executeResult = sqlExecutor.execute(executeParam);
				List<Map<String, Object>> executeResultData = executeResult.getResult();
				if (CollectionUtils.isEmpty(executeResultData)) {
					executeResultData = Collections.emptyList();
					if (StringUtils.isNotBlank(executeResult.getErrMsg())) {
						logger.error("执行sql失败：{}， {}", executeResult.getSql(), executeResult.getErrMsg());
					}
				}
				// 处理成表格下载所需格式
				List<List<Object>> downloadDataList = new LinkedList<>();
				for (Map<String, Object> dataMap : executeResultData) {
					downloadDataList.add(new LinkedList<Object>() {{
						for (TableColumnDescDto columnDto : columnListRetain) {
							Object data = dataMap.get(columnDto.getName());
							// 数据格式处理，不处理有些格式会造成乱码，打不开文件
							if (!(data == null || data instanceof Number || data instanceof CharSequence)) {
								if (data instanceof Timestamp) {
									data = DateTime.of(((Timestamp) data).getTime()).toJdkDate();
								} else {
									data = String.valueOf(data);
								}
							}
							add(data);
						}
					}});
				}
				List<List<String>> sheetHeadList = this.getSheetHeadList(columnListRetain);
				if (multipleFile) {
					// 写入临时文件
					File tempFile = FileUtil.file(tempDirName + "/" + tableName + suffix);
					EasyExcel.write(tempFile).head(sheetHeadList).sheet(tableName).doWrite(downloadDataList);
				} else {
					WriteSheet writeSheet = EasyExcel.writerSheet(tableIndex++, tableName).head(sheetHeadList).build();
					excelWriter.write(downloadDataList, writeSheet);
				}
			}
			if (multipleFile) {
				baseDownloadService.sendResponse(response, param.getDbName(), tempDirName);
			} else {
				excelWriter.finish();
				excelWriterIsFinish = true;
			}
		} finally {
			FileUtil.del(tempDirName);
			if (excelWriterIsFinish && excelWriter != null) {
				excelWriter.finish();
			}
		}
	}
	
	private List<List<String>> getSheetHeadList(List<TableColumnDescDto> columnListRetain) {
		List<List<String>> sheetHeadList = new ArrayList<>();
		for (TableColumnDescDto dataCol : columnListRetain) {
			sheetHeadList.add(new ArrayList<String>() {{
				add(dataCol.getName());
			}});
		}
		return sheetHeadList;
	}
}
