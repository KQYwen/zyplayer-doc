package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.stereotype.Service;

/**
 * 达梦数据查询服务实现类
 *
 * @author diantu
 * @since 2023-02-01
 */
@Service
public class DmServiceImpl extends DbBaseService {

    @Override
    public DatabaseProductEnum getDatabaseProduct() {
        return DatabaseProductEnum.DM;
    }

    /**
     * 达梦数据库中没有也不需要use语句,指定数据库名的情况下直接返回空即可
     * @author diantu
     * @since 2023-02-01
     */
    @Override
    public String getUseDbSql(String dbName) {
        return null;
    }
}
