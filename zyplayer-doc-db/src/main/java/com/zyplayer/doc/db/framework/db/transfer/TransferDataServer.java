package com.zyplayer.doc.db.framework.db.transfer;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbTransferTask;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.service.manage.DbTransferTaskService;
import com.zyplayer.doc.data.utils.ThreadPoolUtil;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据互导服务
 *
 * @author 暮光：城中城
 * @since 2019年9月28日
 */
@Service
public class TransferDataServer {
	private static Logger logger = LoggerFactory.getLogger(TransferDataServer.class);
	
	@Resource
	SqlExecutor sqlExecutor;
	@Resource
	DbTransferTaskService dbTransferTaskService;
	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	// 批量插入一批次的条数
	private final Integer batchInsertNum = 100;
	// 多少条时输出条数日志
	private final Integer executeCountLogNum = 5000;
	// 执行记录信息
	private static final Map<Long, String> taskExecuteMap = new ConcurrentHashMap<>();
	
	/**
	 * 取消任务执行
	 * @param taskId
	 * @author 暮光：城中城
	 * @since 2019年9月28日
	 */
	public void cancel(Long taskId) {
		String executeId = taskExecuteMap.get(taskId);
		if (StringUtils.isBlank(executeId)) {
			throw new ConfirmException("该任务不在执行中，取消失败");
		}
		if (sqlExecutor.cancel(executeId)) {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			String executeInfo = String.format("[%s] %s 手动终止了此任务", DateTime.now().toString(), currentUser.getUsername());
			dbTransferTaskService.addExecuteInfo(taskId, TransferTaskStatus.CANCEL.getCode(), executeInfo);
		} else {
			throw new ConfirmException("终止该任务失败");
		}
	}
	
	/**
	 * 查询任务，执行数据查询和数据写入
	 * @param taskId
	 * @author 暮光：城中城
	 * @since 2019年9月28日
	 */
	public void transferData(Long taskId) {
		DbTransferTask transferTask = dbTransferTaskService.getById(taskId);
		if (transferTask == null || transferTask.getDelFlag() == 1) {
			throw new ConfirmException("未找到该任务记录，创建任务失败");
		}
		if (Objects.equals(transferTask.getLastExecuteStatus(), 1)) {
			throw new ConfirmException("任务正在执行中，请勿重复执行");
		}
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		boolean querySelect = DocUserUtil.haveCustomAuth(DbAuthType.SELECT.getName(), DocAuthConst.DB + transferTask.getQueryDatasourceId());
		boolean queryUpdate = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocAuthConst.DB + transferTask.getQueryDatasourceId());
		if (!manageAuth && !querySelect && !queryUpdate) {
			throw new ConfirmException("没有该数据源的查询权限，创建任务失败");
		}
		boolean storageUpdate = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocAuthConst.DB + transferTask.getStorageDatasourceId());
		if (!manageAuth && !storageUpdate) {
			throw new ConfirmException("没有该数据源的写入权限，创建任务失败");
		}
		dbTransferTaskService.resetExecuteInfo(taskId);
		// 提交任务
		ThreadPoolUtil.getThreadPool().submit(() -> {
			String executeInfo = String.format("[%s] 任务开始执行", DateTime.now().toString());
			dbTransferTaskService.addExecuteInfo(taskId, TransferTaskStatus.EXECUTING.getCode(), executeInfo);
			String executeId = RandomUtil.simpleUUID();
			taskExecuteMap.put(taskId, executeId);
			this.transferData(transferTask, executeId);
		});
	}
	
	/**
	 * 执行数据查询和数据写入
	 * @param transferTask
	 * @param executeId
	 * @author 暮光：城中城
	 * @since 2019年9月28日
	 */
	private void transferData(DbTransferTask transferTask, String executeId) {
		Long querySourceId = transferTask.getQueryDatasourceId();
		Long storageSourceId = transferTask.getStorageDatasourceId();
		String querySql = transferTask.getQuerySql();
		String storageSql = transferTask.getStorageSql();
		List<Map<String, Object>> selectResultList = new LinkedList<>();
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getOrCreateFactoryById(querySourceId);
		ExecuteParam executeParam = new ExecuteParam();
		executeParam.setDatasourceId(querySourceId);
		executeParam.setExecuteType(ExecuteType.SELECT);
		executeParam.setExecuteId(executeId);
		try {
			long executeStartTime = System.currentTimeMillis();
			if (Objects.equals(transferTask.getNeedCount(), 1)) {
				String selectCountSql = SqlParseUtil.getSelectCountSql(querySql);
				executeParam.setSql(selectCountSql);
				ExecuteResult countResult = sqlExecutor.execute(executeParam);
				if (CollectionUtils.isEmpty(countResult.getResult())) {
					String executeInfo = String.format("[%s] 获取总条数失败", DateTime.now().toString());
					logger.error(executeInfo);
					dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.ERROR.getCode(), executeInfo);
					return;
				}
				Object transferCount = countResult.getResult().get(0).get("counts");
				String executeInfo = String.format("[%s] 待处理总条数：%s，查询总条数耗时：%sms", DateTime.now().toString(), transferCount, System.currentTimeMillis() - executeStartTime);
				logger.info(executeInfo);
				dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.EXECUTING.getCode(), executeInfo);
			} else {
				String executeInfo = String.format("[%s] 未开启查询总条数，跳过条数查询", DateTime.now().toString());
				dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.EXECUTING.getCode(), executeInfo);
			}
			AtomicLong readCount = new AtomicLong(0L);
			executeParam.setSql(querySql);
//			executeParam.setSql("select sleep(10)");
			ExecuteResult executeResult = sqlExecutor.execute(factoryBean, executeParam, resultMap -> {
				selectResultList.add(resultMap);
				if (readCount.incrementAndGet() % executeCountLogNum == 0) {
					String executeInfo = String.format("[%s] 已处理条数：%s", DateTime.now().toString(), readCount.get());
					dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.EXECUTING.getCode(), executeInfo);
				}
				// 批量输出数据
				if (selectResultList.size() >= batchInsertNum) {
					this.writeData(storageSourceId, storageSql, selectResultList);
				}
			});
			// 不足100的数据
			if (selectResultList.size() > 0) {
				this.writeData(storageSourceId, storageSql, selectResultList);
			}
			if (StringUtils.isNotBlank(executeResult.getErrMsg())) {
				String executeInfo = String.format("[%s] 执行出错：%s", DateTime.now().toString(), executeResult.getErrMsg());
				logger.error(executeInfo);
				dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.ERROR.getCode(), executeInfo);
			} else {
				String executeInfo = String.format("[%s] 任务执行成功，处理总条数：%s，总耗时：%sms", DateTime.now().toString(), readCount.get(), System.currentTimeMillis() - executeStartTime);
				dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.SUCCESS.getCode(), executeInfo);
			}
		} catch (Exception e) {
			logger.error("SQL执行出错：", e);
			String executeInfo = String.format("[%s] 处理出错：%s", DateTime.now().toString(), ExceptionUtils.getFullStackTrace(e));
			dbTransferTaskService.addExecuteInfo(transferTask.getId(), TransferTaskStatus.ERROR.getCode(), executeInfo);
		}
	}
	
	/**
	 * 写数据操作
	 * @param storageSourceId
	 * @param storageSql
	 * @param selectResultList
	 * @author 暮光：城中城
	 * @since 2019年9月28日
	 */
	private void writeData(Long storageSourceId, String storageSql, List<Map<String, Object>> selectResultList) {
		List<ExecuteParam> executeParamList = SqlParseUtil.getExecuteParamList(storageSql, selectResultList);
		for (ExecuteParam executeParam : executeParamList) {
			executeParam.setDatasourceId(storageSourceId);
			executeParam.setExecuteId(RandomUtil.simpleUUID());
			sqlExecutor.execute(executeParam);
		}
		selectResultList.clear();
	}
}
