package com.zyplayer.doc.db.framework.db.transfer;

import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	DatabaseRegistrationBean databaseRegistrationBean;
	// 批量插入一批次的条数
	private final Integer batchInsertNum = 100;
	
	public void transferData(Long querySourceId, Long storageSourceId, String querySql, String storageSql) {
		List<Map<String, Object>> selectResultList = new LinkedList<>();
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getFactoryById(querySourceId);
		ExecuteParam executeParam = new ExecuteParam();
		executeParam.setDatasourceId(querySourceId);
		executeParam.setExecuteType(ExecuteType.SELECT);
		executeParam.setExecuteId(RandomUtil.simpleUUID());
		try {
			String selectCountSql = SqlParseUtil.getSelectCountSql(querySql);
			executeParam.setSql(selectCountSql);
			ExecuteResult countResult = sqlExecutor.execute(executeParam);
			if (CollectionUtils.isEmpty(countResult.getResult())) {
				logger.error("获取总条数失败");
				return;
			}
			logger.info("总条数：{}", countResult.getResult().get(0).get("counts"));
			
			executeParam.setSql(querySql);
			ExecuteResult executeResult = sqlExecutor.execute(factoryBean, executeParam, resultMap -> {
				selectResultList.add(resultMap);
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
				logger.error("执行出错：{}", executeResult.getErrMsg());
			}
		} catch (Exception e) {
			logger.error("SQL执行出错：", e);
		}
	}
	
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
