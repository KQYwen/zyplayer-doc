package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户组在各项目内的授权关系
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
@Data
@TableName("user_group_auth")
public class UserGroupAuth implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群ID
     */
    private Long groupId;

    /**
     * 授权数据的ID
     */
    private Long dataId;

    /**
     * 授权类型，依据各项目自己定义
     */
    private Integer authType;

    /**
     * 项目类型 1=WIKI模块 2=数据库模块
     */
    private Integer projectType;

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
}
