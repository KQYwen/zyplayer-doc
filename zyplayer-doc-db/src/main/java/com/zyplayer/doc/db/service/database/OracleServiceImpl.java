package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.stereotype.Service;

/**
 * Oracle数据查询服务实现类
 *
 * @author 暮光：城中城
 * @since 2021-02-01
 */
@Service
public class OracleServiceImpl extends DbBaseService {

	@Override
	public DatabaseProductEnum getDatabaseProduct() {
		return DatabaseProductEnum.ORACLE;
	}

	/**
	 * oracle数据库中没有也不需要use语句,指定数据库名的情况下直接返回空即可
	 * @author diantu
	 * @since 2023年1月16日
	 */
	@Override
	public String getUseDbSql(String dbName) {
		return null;
	}
}
