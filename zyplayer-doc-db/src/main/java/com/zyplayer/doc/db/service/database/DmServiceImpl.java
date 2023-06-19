package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 获取分页查询的SQL
     *
     * @return 分页查询的SQL
     * @author diantu
     * @since 2023年2月6日
     */
    @Override
    public String getQueryPageSql(DataViewParam dataViewParam) {
        String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
        if(!Objects.equals(queryColumns, "*")){
            //给字段加上双引号，解决关键字问题
            queryColumns = Arrays.stream(queryColumns.split(",")).map(word -> "\"" + word + "\"").collect(Collectors.joining(","));
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format("select ROWID as ZYPLAYDBROWID,%s from %s.%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
        if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
            sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
        }
        if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
            sqlSb.append(String.format(" order by %s %s", dataViewParam.getOrderColumn(), dataViewParam.getOrderType()));
        }
        sqlSb.append(String.format(" limit %s offset %s", dataViewParam.getPageSize(), dataViewParam.getOffset()));
        return sqlSb.toString();
    }
}
