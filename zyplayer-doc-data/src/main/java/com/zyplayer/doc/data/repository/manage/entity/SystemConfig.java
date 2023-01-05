package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author 暮光：城中城
 * @since 2022-12-01
 */
@TableName("system_config")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置Key
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 创建用户名字
     */
    private String createUser;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 修改人ID
     */
    private Long modifyUserId;

    /**
     * 修改人姓名
     */
    private String modifyUser;

    /**
     * 是否有效 1=有效
     */
    private Integer yn;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getConfigKey() {
        return configKey;
    }
    
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
    
    public String getConfigValue() {
        return configValue;
    }
    
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    
    public Date getCreated() {
        return created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }
    
    public Long getCreateUserId() {
        return createUserId;
    }
    
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public Date getModified() {
        return modified;
    }
    
    public void setModified(Date modified) {
        this.modified = modified;
    }
    
    public Long getModifyUserId() {
        return modifyUserId;
    }
    
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
    
    public String getModifyUser() {
        return modifyUser;
    }
    
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
    
    public Integer getYn() {
        return yn;
    }
    
    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
