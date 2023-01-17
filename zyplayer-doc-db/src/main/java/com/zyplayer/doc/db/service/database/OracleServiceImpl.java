package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 获取分页查询的SQL(oracle分页)
     *
     * @author diantu
     * @since 2023年1月17日
     */
    @Override
    public String getQueryPageSql(DataViewParam dataViewParam) {
        String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format("select %s from %s.%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
        if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
            sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
        }
        if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
            sqlSb.append(String.format(" order by %s %s", dataViewParam.getOrderColumn(), dataViewParam.getOrderType()));
        }
        StringBuilder sqlSbFinal = new StringBuilder();
        Integer pageSize = dataViewParam.getPageSize() * dataViewParam.getPageNum();
        Integer offsetSize = dataViewParam.getPageSize() * (dataViewParam.getPageNum() - 1) + 1;
        sqlSbFinal.append(String.format("select * from ( select %s from %s", queryColumns + ",rownum rn", "(" + sqlSb + ") where rownum<=" + pageSize + " ) t2 where t2.rn >=" + offsetSize));
        return sqlSbFinal.toString();
    }
}
