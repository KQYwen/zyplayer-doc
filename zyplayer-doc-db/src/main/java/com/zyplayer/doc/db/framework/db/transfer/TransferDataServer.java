package com.zyplayer.doc.db.framework.db.transfer;

import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class TransferDataServer {
	
	@Resource
	SqlExecutor sqlExecutor;
	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	// 批量插入一批次的条数
	private final Integer batchInsertNum = 100;
	
	public void writeData(Long querySourceId, Long storageSourceId, String querySql, String storageSql) {
		List<Map<String, Object>> selectResultList = new LinkedList<>();
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getFactoryById(querySourceId);
		ExecuteParam executeParam = new ExecuteParam();
		executeParam.setSql(querySql);
		executeParam.setExecuteType(ExecuteType.SELECT);
		executeParam.setExecuteId(RandomUtil.simpleUUID());
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
	}
	
	public void writeData(Long storageSourceId, String storageSql, List<Map<String, Object>> selectResultList) {
		List<ExecuteParam> executeParamList = SqlParseUtil.getExecuteParamList(storageSql, selectResultList);
		for (ExecuteParam executeParam : executeParamList) {
			executeParam.setDatasourceId(storageSourceId);
			sqlExecutor.execute(executeParam);
		}
		selectResultList.clear();
	}
}
