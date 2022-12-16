package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-05-31
 */
@Data
@TableName("user_auth")
public class UserAuth implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 权限ID
     */
    private Long authId;

    /**
     * 创建用户ID
     */
    private Long createUid;

    /**
     * 更新用户ID
     */
    private Long updateUid;

    /**
     * 是否删除 0=未删除 1=已删除
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 系统类型，DocSysType
     */
    private Integer sysType;

    /**
     * 系统模块类型，DocSysModuleType
     */
    private Integer sysModuleType;

    /**
     * 系统模块ID
     */
    private Long sysModuleId;

}
