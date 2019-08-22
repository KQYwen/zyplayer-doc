package com.zyplayer.doc.db.framework.db.mapper.base;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.zyplayer.doc.data.service.manage.DbHistoryService;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.interceptor.SqlLogUtil;
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	public void cancel(String executeId) {
		PreparedStatement preparedStatement = statementMap.remove(executeId);
		try {
			preparedStatement.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行sql，返回结果
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ExecuteResult execute(Long datasourceId, String executeId, ExecuteType executeType, String sql, Map<String, Object> paramMap) {
		DatabaseFactoryBean factoryBean = databaseRegistrationBean.getFactoryById(datasourceId);
		return this.execute(factoryBean, executeId, executeType, sql, paramMap, null);
	}
	
	/**
	 * 执行sql，可通过handler回调每一行的结果
	 * @author 暮光：城中城
	 * @since 2019年8月18日
	 */
	public ExecuteResult execute(DatabaseFactoryBean factoryBean, String executeId, ExecuteType executeType, String sql, Map<String, Object> paramMap, ResultHandler handler) {
		if (factoryBean == null) {
			return new ExecuteResult();
		}
		BoundSql boundSql = getBoundSql(sql, paramMap);
		sql = boundSql.getSql();
		String sqlStr = SqlLogUtil.getSqlString(paramMap, boundSql);
		logger.info("sql ==> {}", sqlStr);
		// 保留历史记录
		dbHistoryService.saveHistory(sqlStr);
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		PreparedStatement preparedStatement = null;
		DruidPooledConnection connection = null;
		// 执行查询
		try {
			connection = factoryBean.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			// 设置当前的PreparedStatement
			statementMap.put(executeId, preparedStatement);
			for (int i = 0; i < parameterMappings.size(); i++) {
				preparedStatement.setObject(i + 1, paramMap.get(parameterMappings.get(i).getProperty()));
			}
			// 限制下最大数量
			preparedStatement.setMaxRows(1000);
			if (ExecuteType.SELECT.equals(executeType)) {
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
			// 更新的数量
			int updateCount = preparedStatement.getUpdateCount();
			updateCount = (updateCount < 0) ? 0 : updateCount;
			return new ExecuteResult(updateCount, resultList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			statementMap.remove(executeId);
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
