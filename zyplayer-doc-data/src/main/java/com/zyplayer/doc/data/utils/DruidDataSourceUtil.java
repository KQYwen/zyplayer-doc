package com.zyplayer.doc.data.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.zyplayer.doc.core.exception.ConfirmException;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Druid连接池工具类
 *
 * @author 暮光：城中城
 * @since 2020-04-08
 */
public class DruidDataSourceUtil {
	
	private static AtomicLong nameId = new AtomicLong(0);
	
	public static DruidDataSource createDataSource(String driverClassName, String url, String username, String password, boolean breakAfterAcquireFailure) throws Exception {
		// 数据源配置
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(2);
		dataSource.setMinIdle(2);
		dataSource.setMaxActive(50);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setValidationQuery("select 1");
		dataSource.setMaxWait(3000);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(3600000);
		// 重试3次，失败退出，源码里是errorCount > connectionErrorRetryAttempts，所以写成2就是3次、、、
		// CreateConnectionThread 源码在这个类里面
		dataSource.setConnectionErrorRetryAttempts(2);
		dataSource.setBreakAfterAcquireFailure(breakAfterAcquireFailure);// false：连接出错后不退出，等待下个定时周期重试，true：失败不再重试
		dataSource.setTimeBetweenConnectErrorMillis(180000);// 连接出错后重试时间间隔3分钟
		dataSource.setName("zyplayer-doc-db-" + nameId.incrementAndGet());
		if (Objects.equals("oracle.jdbc.driver.OracleDriver", driverClassName)) {
			dataSource.setValidationQuery("select 1 from dual");
		}
		DruidPooledConnection connection = dataSource.getConnection(3000);
		if (connection == null) {
			throw new ConfirmException("尝试获取该数据源连接失败：" + url);
		}
		connection.recycle();
		return dataSource;
	}
}
