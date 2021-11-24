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
}
