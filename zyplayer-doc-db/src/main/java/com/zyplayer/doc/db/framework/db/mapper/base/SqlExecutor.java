package com.zyplayer.doc.db.framework.db.mapper.base;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.zyplayer.doc.data.service.manage.DbHistoryService;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sql执行器
 *
 * @author 暮光：城中城
 * @since 2019年8月18日
 */
@Repository
public class SqlExecutor {
	private static Logger logger = LoggerFactory.getLogger(SqlExecutor.class);
	
	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	@Resource
	DbHistoryService dbHistoryService;
	
	// 执行中的PreparedStatement信息，用于强制取消执行
	private static final Map<String, PreparedStatement> statementMap = new ConcurrentHashMap<>();
	
	/**
	 * 取消执行
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
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ExecuteResult execute(ExecuteParam param) {
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getOrCreateFactoryById(param.getDatasourceId());
		return this.execute(factoryBean, param, null);
	}
	
	/**
	 * 执行sql，返回结果
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ExecuteResult execute(ExecuteParam param, ResultHandler handler) {
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getOrCreateFactoryById(param.getDatasourceId());
		return this.execute(factoryBean, param, handler);
	}
	
	/**
	 * 执行sql，可通过handler回调每一行的结果
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ExecuteResult execute(DatabaseFactoryBean factoryBean, ExecuteParam executeParam, ResultHandler handler) {
		if (factoryBean == null) {
			return ExecuteResult.error("未找到数据库连接", executeParam.getSql());
		}
//		BoundSql boundSql = getBoundSql(sql, paramMap);
//		sql = boundSql.getSql();
//		String sqlStr = SqlLogUtil.getSqlString(paramMap, boundSql);
		// 有参数的时候不输出日志，暂时只有导数据才有参数
		if (CollectionUtils.isEmpty(executeParam.getParamList())) {
			if (StringUtils.isNotBlank(executeParam.getPrefixSql())) {
				logger.info("prefix sql ==> {}", executeParam.getPrefixSql());
			}
			logger.info("sql ==> {}", executeParam.getSql());
		}
//		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		PreparedStatement preparedStatement = null;
		PreparedStatement prefixStatement = null;
		DruidPooledConnection connection = null;
		// 执行查询
		try {
			long startTime = System.currentTimeMillis();
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
			if (parameterMappings != null && paramDataList != null && parameterMappings.size() > 0 && paramDataList.size() > 0) {
				for (int i = 0; i < parameterMappings.size(); i++) {
					preparedStatement.setObject(i + 1, paramDataList.get(i));
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
			ResultSet resultSet = preparedStatement.getResultSet();
			List<Map<String, Object>> resultList = new LinkedList<>();
			if (resultSet != null) {
				while (resultSet.next()) {
					Map<String, Object> resultMap = new LinkedHashMap<>();
					ResultSetMetaData metaData = resultSet.getMetaData();
					for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
						resultMap.put(metaData.getColumnName(i), resultSet.getObject(i));
					}
					if (handler != null) {
						handler.handleResult(resultMap);
					} else {
						resultList.add(resultMap);
					}
				}
			}
			// 更新的数量，小于0代表不是更新语句
			int updateCount = preparedStatement.getUpdateCount();
			long useTime = System.currentTimeMillis() - startTime;
			return new ExecuteResult(updateCount, resultList, useTime, executeParam.getSql());
		} catch (Exception e) {
			logger.error("执行出错", e);
			throw new RuntimeException(e);
		} finally {
			statementMap.remove(executeParam.getExecuteId());
			try {
				if (prefixStatement != null && !prefixStatement.isClosed()) {
					prefixStatement.close();
				}
			} catch (Exception e) {
				logger.error("关闭Statement失败");
			}
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (Exception e) {
				logger.error("关闭Statement失败");
			}
			try {
				if (connection != null && !connection.isClosed()) {
					connection.recycle();
				}
			} catch (Exception e) {
				logger.error("回收connection失败");
			}
		}
	}
	
	private BoundSql getBoundSql(String sql, Map<String, Object> paramMap){
		// 组装参数
		GenericTokenParser parser = new GenericTokenParser("${", "}", content -> {
			Object o = paramMap.get(content);
			return (o == null) ? null : String.valueOf(o);
		});
		sql = parser.parse(sql);
		SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(new MybatisConfiguration());
		StaticSqlSource parse = (StaticSqlSource) sqlSourceBuilder.parse(sql, Object.class, paramMap);
		return parse.getBoundSql(new Object());
	}
}
