package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据库函数修改日志
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-04-26
 */
public class DbProcLog implements Serializable {

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
     * 所属数据库
     */
    private String procDb;

    /**
     * 名字
     */
    private String procName;

    /**
     * 类型
     */
    private String procType;

    /**
     * 函数创建SQL
     */
    private String procBody;

    /**
     * 保存状态 1=成功 2=失败
     */
    private Integer status;

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
    public String getProcDb() {
        return procDb;
    }

    public void setProcDb(String procDb) {
        this.procDb = procDb;
    }
    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }
    public String getProcType() {
        return procType;
    }

    public void setProcType(String procType) {
        this.procType = procType;
    }
    public String getProcBody() {
        return procBody;
    }

    public void setProcBody(String procBody) {
        this.procBody = procBody;
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
        return "DbProcLog{" +
        "id=" + id +
        ", datasourceId=" + datasourceId +
        ", procDb=" + procDb +
        ", procName=" + procName +
        ", procType=" + procType +
        ", procBody=" + procBody +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        "}";
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}
