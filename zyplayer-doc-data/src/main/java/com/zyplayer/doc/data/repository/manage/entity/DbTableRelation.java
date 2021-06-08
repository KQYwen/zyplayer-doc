package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 表关系
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
public class DbTableRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据源ID
     */
    private Long datasourceId;

    /**
     * 源库名
     */
    private String startDbName;

    /**
     * 源表名
     */
    private String startTableName;

    /**
     * 源字段名
     */
    private String startColumnName;

    /**
     * 目标库名
     */
    private String endDbName;

    /**
     * 目标表名
     */
    private String endTableName;

    /**
     * 目标字段名
     */
    private String endColumnName;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }
    public String getStartDbName() {
        return startDbName;
    }

    public void setStartDbName(String startDbName) {
        this.startDbName = startDbName;
    }
    public String getStartTableName() {
        return startTableName;
    }

    public void setStartTableName(String startTableName) {
        this.startTableName = startTableName;
    }
    public String getStartColumnName() {
        return startColumnName;
    }

    public void setStartColumnName(String startColumnName) {
        this.startColumnName = startColumnName;
    }
    public String getEndDbName() {
        return endDbName;
    }

    public void setEndDbName(String endDbName) {
        this.endDbName = endDbName;
    }
    public String getEndTableName() {
        return endTableName;
    }

    public void setEndTableName(String endTableName) {
        this.endTableName = endTableName;
    }
    public String getEndColumnName() {
        return endColumnName;
    }

    public void setEndColumnName(String endColumnName) {
        this.endColumnName = endColumnName;
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

    @Override
    public String toString() {
        return "DbTableRelation{" +
        "id=" + id +
        ", datasourceId=" + datasourceId +
        ", startDbName=" + startDbName +
        ", startTableName=" + startTableName +
        ", startColumnName=" + startColumnName +
        ", endDbName=" + endDbName +
        ", endTableName=" + endTableName +
        ", endColumnName=" + endColumnName +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        "}";
    }
}
