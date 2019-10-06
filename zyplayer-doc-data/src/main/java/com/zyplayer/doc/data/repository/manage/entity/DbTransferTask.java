package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-09-30
 */
public class DbTransferTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 查询数据源ID
     */
    private Long queryDatasourceId;

    /**
     * 入库数据源ID
     */
    private Long storageDatasourceId;

    /**
     * 查询数据的sql
     */
    private String querySql;

    /**
     * 数据入库的sql
     */
    private String storageSql;

    /**
     * 自动查询总条数 0=否 1=是
     */
    private Integer needCount;

    /**
     * 最后执行状态 0=未执行 1=执行中 2=执行成功 3=执行失败 4=取消执行
     */
    private Integer lastExecuteStatus;

    /**
     * 最后执行时间
     */
    private Date lastExecuteTime;

    /**
     * 最后执行信息
     */
    private String lastExecuteInfo;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人名字
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标记 0=正常 1=已删除
     */
    private Integer delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getQueryDatasourceId() {
        return queryDatasourceId;
    }

    public void setQueryDatasourceId(Long queryDatasourceId) {
        this.queryDatasourceId = queryDatasourceId;
    }
    public Long getStorageDatasourceId() {
        return storageDatasourceId;
    }

    public void setStorageDatasourceId(Long storageDatasourceId) {
        this.storageDatasourceId = storageDatasourceId;
    }
    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }
    public String getStorageSql() {
        return storageSql;
    }

    public void setStorageSql(String storageSql) {
        this.storageSql = storageSql;
    }
    public Integer getNeedCount() {
        return needCount;
    }

    public void setNeedCount(Integer needCount) {
        this.needCount = needCount;
    }
    public Integer getLastExecuteStatus() {
        return lastExecuteStatus;
    }

    public void setLastExecuteStatus(Integer lastExecuteStatus) {
        this.lastExecuteStatus = lastExecuteStatus;
    }
    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }
    public String getLastExecuteInfo() {
        return lastExecuteInfo;
    }

    public void setLastExecuteInfo(String lastExecuteInfo) {
        this.lastExecuteInfo = lastExecuteInfo;
    }
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "DbTransferTask{" +
        "id=" + id +
        ", name=" + name +
        ", queryDatasourceId=" + queryDatasourceId +
        ", storageDatasourceId=" + storageDatasourceId +
        ", querySql=" + querySql +
        ", storageSql=" + storageSql +
        ", needCount=" + needCount +
        ", lastExecuteStatus=" + lastExecuteStatus +
        ", lastExecuteTime=" + lastExecuteTime +
        ", lastExecuteInfo=" + lastExecuteInfo +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", delFlag=" + delFlag +
        "}";
    }
}
