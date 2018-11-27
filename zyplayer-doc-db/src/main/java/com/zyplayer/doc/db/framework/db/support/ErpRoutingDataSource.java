package com.zyplayer.doc.db.framework.db.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态切换数据源
 */
public class ErpRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DBInfoHolder.getDbInfoNow();
	}

}
