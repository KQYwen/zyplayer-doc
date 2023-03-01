package com.zyplayer.doc.db.service.database;

import cn.hutool.core.util.IdUtil;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.framework.db.dto.ColumnInfoDto;
import com.zyplayer.doc.db.framework.db.dto.ProcedureDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.mysql.MysqlMapper;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * MySQL数据查询服务实现类
 *
 * @author 暮光：城中城
 * @since 2021-02-01
 */
@Service
public class MysqlServiceImpl extends DbBaseService {

    @Override
    public DatabaseProductEnum getDatabaseProduct() {
        return DatabaseProductEnum.MYSQL;
    }

    /**
     * mysql数据库名中含有 - 等特殊字符,需要用反引号包裹
     *
     * @author diantu
     * @since 2023年1月16日
     */
    @Override
    public String getUseDbSql(String dbName) {
        if (StringUtils.isNotBlank(dbName)) {
            return "use " + "`" + dbName + "`";
        }
        return null;
    }

    /**
     * 获取查询总条数的SQL(mysql数据库名中含有 - 等特殊字符,需要用反引号包裹)
     *
     * @author diantu
     * @since 2023年1月16日
     */
    @Override
    public String getQueryCountSql(DataViewParam dataViewParam) {
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format("select count(1) as counts from %s.%s", "`"+dataViewParam.getDbName()+"`", dataViewParam.getTableName()));
        if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
            sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
        }
        return sqlSb.toString();
    }

    /**
     * 获取全量数据查询的SQL(mysql数据库名中含有 - 等特殊字符,需要用反引号包裹)
     *
     * @author diantu
     * @since 2023年1月17日
     */
    @Override
    public String getQueryAllSql(DataViewParam dataViewParam) {
        String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format("select %s from %s.%s", queryColumns, "`" + dataViewParam.getDbName() + "`", dataViewParam.getTableName()));
        if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
            sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
        }
        return sqlSb.toString();
    }

    /**
     * 获取分页查询的SQL(mysql数据库名中含有 - 等特殊字符,需要用反引号包裹)
     *
     * @author diantu
     * @since 2023年1月17日
     */
    @Override
    public String getQueryPageSql(DataViewParam dataViewParam) {
        String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append(String.format("select %s from %s.%s", queryColumns, "`" + dataViewParam.getDbName() + "`", dataViewParam.getTableName()));
        if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
            sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
        }
        if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
            sqlSb.append(String.format(" order by %s %s", dataViewParam.getOrderColumn(), dataViewParam.getOrderType()));
        }
        sqlSb.append(String.format(" limit %s offset %s", dataViewParam.getPageSize(), dataViewParam.getOffset()));
        return sqlSb.toString();
    }

    @Override
    public TableDdlVo getTableDdl(Long sourceId, String dbName, String tableName) {
        BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
        List<Map<String, Object>> tableDdlList = baseMapper.getTableDdl(dbName, tableName);
        TableDdlVo tableDdlVo = new TableDdlVo();
        tableDdlVo.setCurrent(DatabaseProductEnum.MYSQL.name().toLowerCase());
        if (CollectionUtils.isNotEmpty(tableDdlList)) {
            String mysqlSql = tableDdlList.get(0).get("Create Table") + ";";
            tableDdlVo.setMysql(mysqlSql);
            tableDdlVo.setOracle(SQLTransformUtils.translateMySqlToOracle(mysqlSql));
            tableDdlVo.setSqlserver(SQLTransformUtils.translateMySqlToSqlServer(mysqlSql));
            // TODO 其他数据库同理
        }
        return tableDdlVo;
    }

    @Override
    public void updateTableColumnDesc(Long sourceId, String dbName, String tableName, String columnName, String newDesc) {
        BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
        // mysql要同时修改类型默认值等，所以先查出来
        MysqlMapper mysqlMapper = databaseRegistrationBean.getBaseMapper(sourceId, MysqlMapper.class);
        ColumnInfoDto columnInfo = mysqlMapper.getColumnInfo(dbName, tableName, columnName);
        String isNullable = Optional.ofNullable(columnInfo.getIsNullable()).orElse("");
        columnInfo.setIsNullable("yes".equalsIgnoreCase(isNullable) ? "null" : "not null");
        String columnDefault = columnInfo.getColumnDefault();
        if (StringUtils.isNotBlank(columnDefault)) {
            columnInfo.setColumnDefault("DEFAULT " + columnDefault);
        } else {
            columnInfo.setColumnDefault("");
        }
        String extra = columnInfo.getExtra();
        columnInfo.setExtra(StringUtils.isBlank(extra) ? "" : extra);
        baseMapper.updateTableColumnDesc(dbName, tableName, columnName, newDesc, columnInfo);
    }

    @Override
    public ProcedureDto getProcedureDetail(Long sourceId, String dbName, String typeName, String procName) {
        BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
        ProcedureDto procedureDetail = baseMapper.getProcedureDetail(dbName, typeName, procName);
        if (procedureDetail == null) {
            // 新建的时候
            ProcedureDto procedureDetailNew = new ProcedureDto();
            if (Objects.equals(typeName, "FUNCTION")) {
                procedureDetailNew.setBody("CREATE DEFINER = CURRENT_USER " + typeName + " `" + dbName + "`.`" + procName + "`() RETURNS integer\n" +
                        "BEGIN\n" +
                        "\t#Routine body goes here...\n" +
                        "\tRETURN 0;\n" +
                        "END;");
            } else {
                procedureDetailNew.setBody("CREATE DEFINER = CURRENT_USER " + typeName + " `" + dbName + "`.`" + procName + "`()\n" +
                        "BEGIN\n" +
                        "\t#Routine body goes here...\n" +
                        "END;");
            }
            procedureDetailNew.setDb(dbName);
            procedureDetailNew.setDefiner("CURRENT_USER");
            procedureDetailNew.setType(typeName);
            return procedureDetailNew;
        }
        // 组装好SQL
        String type = procedureDetail.getType();
        String name = procedureDetail.getName();
        String db = procedureDetail.getDb();
        String paramList = StringUtils.defaultIfBlank(procedureDetail.getParamList(), "");
        String[] definerArr = procedureDetail.getDefiner().split("@");
        String createStr = String.format("CREATE DEFINER=`%s`@`%s` %s `%s`.`%s`(%s)", definerArr[0], definerArr[1], type, db, name, paramList);
        if (Objects.equals(procedureDetail.getType(), "FUNCTION")) {
            createStr += " RETURNS " + procedureDetail.getReturns();
        }
        procedureDetail.setBody(createStr + "\r\n" + procedureDetail.getBody());
        return procedureDetail;
    }

    @Override
    public ExecuteResult saveProcedure(Long sourceId, String dbName, String typeName, String procName, String procSql) {
        String firstLine = procSql.split("\n")[0];
        // 看函数名是否被修改了，修改会导致函数名的不确定，有认知上的成本，明确的先删再建吧
        if (!firstLine.contains("`" + procName + "`(") && !firstLine.contains(" " + procName + "(") && !firstLine.contains("." + procName + "(")) {
            return ExecuteResult.error("在编辑页面不允许修改函数名，如需新建或修改，请到列表页删除后再新建函数", procSql);
        }
        ProcedureDto procedureDetail = this.getProcedureDetail(sourceId, dbName, typeName, procName);
        // 按MySQL的来是先删除再创建，如果其他数据库不是这个逻辑，需要重写本方法实现自己的逻辑
        BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
        baseMapper.deleteProcedure(dbName, typeName, procName);
        // 执行创建SQL
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setDatasourceId(sourceId);
        executeParam.setExecuteId(IdUtil.randomUUID());
        executeParam.setExecuteType(ExecuteType.ALL);
        executeParam.setSql(procSql);
        executeParam.setMaxRows(1000);
        try {
            return sqlExecutor.execute(executeParam);
        } catch (Exception e) {
            try {
                // 尝试恢复函数
                executeParam.setSql(procedureDetail.getBody());
                sqlExecutor.execute(executeParam);
            } catch (Exception e1) {
                return ExecuteResult.error("执行和恢复函数均失败，请先备份您的SQL，以防丢失\n" + e.getMessage(), procSql);
            }
            return ExecuteResult.error(e.getMessage(), procSql);
        }
    }
}
