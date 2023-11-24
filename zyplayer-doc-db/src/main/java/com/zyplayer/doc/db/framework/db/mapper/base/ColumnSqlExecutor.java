package com.zyplayer.doc.db.framework.db.mapper.base;

import cn.hutool.core.io.IoUtil;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.sse.service.DbSseEmitterService;
import com.zyplayer.doc.db.framework.sse.util.DbSseCacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.ParameterMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 新版的SQL执行类，返回结果有比较大的差异，为了兼容之前的代码就不在原有基础上修改
 *
 * @author 暮光：城中城
 * @since 2023-02-12
 */
@Repository
public class ColumnSqlExecutor {
	private static final Logger logger = LoggerFactory.getLogger(SqlExecutor.class);

	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;

	@Resource
	DbSseEmitterService dbSseEmitterService;

	// 执行中的PreparedStatement信息，用于强制取消执行
	private static final Map<String, PreparedStatement> statementMap = new ConcurrentHashMap<>();

	/**
	 * 取消执行
	 *
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public boolean cancel(String executeId) {
		PreparedStatement preparedStatement = statementMap.remove(executeId);
		try {
			if (preparedStatement != null) {
				preparedStatement.cancel();
				return true;
			}
			logger.error("未找到指定任务，取消执行失败：{}", executeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 执行sql，返回结果
	 *
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ColumnExecuteResult execute(ExecuteParam param) {
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getOrCreateFactoryById(param.getDatasourceId());
		return this.execute(factoryBean, param, null);
	}

	/**
	 * 执行sql，返回结果
	 *
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ColumnExecuteResult execute(ExecuteParam param, ResultHandler handler) {
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getOrCreateFactoryById(param.getDatasourceId());
		return this.execute(factoryBean, param, handler);
	}

	/**
	 * 执行sql，可通过handler回调每一行的结果
	 *
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ColumnExecuteResult execute(DatabaseFactoryBean factoryBean, ExecuteParam executeParam, ResultHandler handler) {
		// 有参数的时候不输出日志，暂时只有导数据才有参数
		if (CollectionUtils.isEmpty(executeParam.getParamList())) {
			if (StringUtils.isNotBlank(executeParam.getPrefixSql())) {
				logger.info("prefix sql ==> {}", executeParam.getPrefixSql());
			}
			logger.info("sql ==> {}", executeParam.getSql());
		}
		if (factoryBean == null) {
			return ColumnExecuteResult.error(executeParam.getSql(), "未找到数据库连接", null);
		}
		long startExecuteTime = System.currentTimeMillis();
		ColumnExecuteResult executeResult = ColumnExecuteResult.ok(executeParam.getSql());
		PreparedStatement preparedStatement = null;
		PreparedStatement prefixStatement = null;
		DruidPooledConnection connection = null;
		ResultSet resultSet = null;
		// 执行查询
		try {
			connection = factoryBean.getDataSource().getConnection();
			if (StringUtils.isNotBlank(executeParam.getPrefixSql())) {
				prefixStatement = connection.prepareStatement(executeParam.getPrefixSql());
				prefixStatement.execute();
			}
			preparedStatement = connection.prepareStatement(executeParam.getSql());
			// 设置当前的PreparedStatement
			statementMap.put(executeParam.getExecuteId(), preparedStatement);
			List<ParameterMapping> parameterMappings = executeParam.getParameterMappings();
			List<Object> paramDataList = executeParam.getParamList();
			if (!parameterMappings.isEmpty() && !paramDataList.isEmpty()) {
				int parameterCount = 99999;
				try {
					parameterCount = preparedStatement.getParameterMetaData().getParameterCount();
				} catch (Exception e) {
					logger.info("不支持获取总数：" + e.getMessage());
				}
				for (int paramIndex = 0; paramIndex < parameterMappings.size() && paramIndex < parameterCount; paramIndex++) {
					preparedStatement.setObject(paramIndex + 1, paramDataList.get(paramIndex));
				}
			}
			// 最大限制1分钟
			preparedStatement.setQueryTimeout(60);
			// 限制下最大数量
			if (executeParam.getMaxRows() != null) {
				preparedStatement.setMaxRows(executeParam.getMaxRows());
			}
			if (ExecuteType.SELECT.equals(executeParam.getExecuteType())) {
				preparedStatement.executeQuery();
			} else {
				preparedStatement.execute();
			}
			// 查询的结果集
			resultSet = preparedStatement.getResultSet();

			//最后一次
			if(Boolean.TRUE.equals(executeParam.getIsLastTime())){
				//推送查询完成信息
				DocUserDetails currentUser = DocUserUtil.getCurrentUser();
				String loginId = currentUser.getUserId().toString();
				String clientId = DbSseCacheUtil.getClientIdByLoginId(loginId);
				if(clientId!=null){
					dbSseEmitterService.sendMessageToOneClient(DbSseCacheUtil.getClientIdByLoginId(loginId),"1");
				}
			}

			List<String> headerList = new LinkedList<>();
			List<List<Object>> dataList = new LinkedList<>();
			if (resultSet != null) {
				int columnCount = resultSet.getMetaData().getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					headerList.add(resultSet.getMetaData().getColumnLabel(i + 1));
				}
				while (resultSet.next()) {
					List<Object> tempList = new LinkedList<>();
					for (int i = 0; i < columnCount; i++) {
						tempList.add(resultSet.getObject(i + 1));
					}
					dataList.add(tempList);
				}
			}
			// 更新的数量
			executeResult.setData(dataList);
			executeResult.setHeader(headerList);
			executeResult.setUpdateCount(Math.max(preparedStatement.getUpdateCount(), 0));
		} catch (Exception e) {
			logger.error("执行出错", e);
			executeResult.setException(e);
			executeResult.setErrMsg(e.getMessage());
			executeResult.setErrCode(ColumnExecuteResult.ExecuteResultCode.ERROR);
		} finally {
			statementMap.remove(executeParam.getExecuteId());
			IoUtil.close(resultSet);
			IoUtil.close(prefixStatement);
			IoUtil.close(preparedStatement);
			IoUtil.close(connection);
		}
		executeResult.setQueryTime(System.currentTimeMillis() - startExecuteTime);
		return executeResult;
	}
}
