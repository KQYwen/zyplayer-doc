package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * api文档全局参数记录
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@TableName("api_global_param")
public class ApiGlobalParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数类型 1=form 2=header 3=cookie
     */
    private Integer paramType;

    /**
     * 参数名
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 状态 1=启用 2=禁用
     */
    private Integer paramStatus;

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
     * 是否有效 0=无效 1=有效
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }
    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    public Integer getParamStatus() {
        return paramStatus;
    }

    public void setParamStatus(Integer paramStatus) {
        this.paramStatus = paramStatus;
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
    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    @Override
    public String toString() {
        return "ApiGlobalParam{" +
            "id=" + id +
            ", paramType=" + paramType +
            ", paramKey=" + paramKey +
            ", paramValue=" + paramValue +
            ", paramStatus=" + paramStatus +
            ", createUserId=" + createUserId +
            ", createUserName=" + createUserName +
            ", createTime=" + createTime +
            ", yn=" + yn +
        "}";
    }
}
