package com.zyplayer.doc.db.service.database;

import com.alibaba.druid.sql.SQLUtils;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if(!Objects.equals(queryColumns, "*")){
            //给字段加上双引号，解决关键字问题
            queryColumns = Arrays.stream(queryColumns.split(",")).map(word -> "\"" + word + "\"").collect(Collectors.joining(","));
        }
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format("select %s from %s.%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
        if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
            sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
        }
        if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
            sqlSb.append(String.format(" order by %s %s", "\""+dataViewParam.getOrderColumn()+"\"", dataViewParam.getOrderType()));
        }
        StringBuilder sqlSbFinal = new StringBuilder();
        int pageSize = dataViewParam.getPageSize() * dataViewParam.getPageNum();
        int pageNum = dataViewParam.getPageSize() * (dataViewParam.getPageNum() - 1) + 1;
        sqlSbFinal.append(String.format("select %s ,ZYPLAYDBROWID from ( select %s ,rowidtochar(rowid) as ZYPLAYDBROWID from %s",queryColumns, queryColumns + ",rownum rn", "(" + sqlSb + ") where rownum<=" + pageSize + " ) t2 where t2.rn >=" + pageNum));
        return sqlSbFinal.toString();
    }

    /**
     * 获取分页查询的SQL
     *
     * @return 分页查询的SQL
     * @author diantu
     * @since 2023年2月22日
     */
    @Override
    public String getQueryPageSqlBySql(String sql,Integer pageSize,Integer pageNum) {
        int pageSizeFinal = pageSize * pageNum;
        int pageNumFinal = pageSize * (pageNum - 1) + 1;
        return String.format("select * from ( select r.*,rownum rn from %s", "(" + sql + ") r where rownum<=" + pageSizeFinal + " ) t2 where t2.rn >=" + pageNumFinal);
    }

    /**
     * 获取建表语句
     *
     * @author diantu
     * @since 2023年1月29日
     */
    @Override
    public TableDdlVo getTableDdl(Long sourceId, String dbName, String tableName) {
        BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
        List<Map<String, Object>> tableDdlList = baseMapper.getTableDdl(dbName, tableName);
        TableDdlVo tableDdlVo = new TableDdlVo();
        tableDdlVo.setCurrent(DatabaseProductEnum.ORACLE.name().toLowerCase());
        if (CollectionUtils.isNotEmpty(tableDdlList)) {
            String oracleSql = "";
            try {
                oracleSql = SQLTransformUtils.ClobToString((Clob)tableDdlList.get(0).get("CREATETABLE"));
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            tableDdlVo.setOracle(SQLUtils.formatOracle(oracleSql));
            //oracle建表语句转换为mysql建表语句
            String mysqlSql = SQLTransformUtils.translateOracleToMySql(oracleSql);
            tableDdlVo.setMysql(mysqlSql);
            // TODO sqlserver等数据库同理
        }
        return tableDdlVo;
    }
}
