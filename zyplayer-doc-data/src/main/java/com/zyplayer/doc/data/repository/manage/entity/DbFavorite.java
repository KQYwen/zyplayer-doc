package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-08-21
 */
public class DbFavorite implements Serializable {

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
     * 收藏标题
     */
    private String name;

    /**
     * 收藏内容
     */
    private String content;

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
    
    /**
     * 执行参数JSON
     */
    private String paramJson;

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
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "DbFavorite{" +
        "id=" + id +
        ", name=" + name +
        ", content=" + content +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", yn=" + yn +
        "}";
    }
    
    public Long getDatasourceId() {
        return datasourceId;
    }
    
    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }
    
    public String getParamJson() {
        return paramJson;
    }
    
    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }
}
