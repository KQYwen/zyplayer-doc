package com.zyplayer.doc.data.repository.support.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * 日志拦截
 *
 * @author 暮光：城中城
 * @since 2019-02-26
 */
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class SqlLogInterceptor implements Interceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlLogInterceptor.class);
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		// 获取sql语句
		String sql = getSqlString(configuration, boundSql);
		LOGGER.info(sql);
		// 执行结果
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}
	
	@Override
	public void setProperties(Properties properties) {
	}
	
	private String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(obj) + "'";
			//System.out.println(value);
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "'null'";
			}
		}
		return value;
	}
	
	public String getSqlString(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		StringBuilder sqlSb = new StringBuilder(boundSql.getSql().replaceAll("[\\s]+", " "));
		int fromIndex = 0;
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				//sqlSb = sqlSb.replaceFirst("\\?", getParameterValue(parameterObject));
				fromIndex = replacePlaceholder(sqlSb, fromIndex, getParameterValue(parameterObject));
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						//sqlSb = sqlSb.replaceFirst("\\?", getParameterValue(obj));
						fromIndex = replacePlaceholder(sqlSb, fromIndex, getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						//sqlSb = sqlSb.replaceFirst("\\?", getParameterValue(obj));
						fromIndex = replacePlaceholder(sqlSb, fromIndex, getParameterValue(obj));
					}
				}
			}
		}
		return sqlSb.toString();
	}
	
	/**
	 * 替换？占位符
	 *
	 * @param sql
	 * @param fromIndex
	 * @param replaceStr
	 * @return
	 * @author 暮光：城中城
	 * @since 2018年10月27日
	 */
	private int replacePlaceholder(StringBuilder sql, int fromIndex, String replaceStr) {
		int index = sql.indexOf("?", fromIndex);
		if (index >= 0) {
			sql.replace(index, index + 1, replaceStr);
		}
		return index + replaceStr.length();
	}
}

