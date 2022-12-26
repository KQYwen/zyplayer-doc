package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 暮光：城中城
 * @since 2018-12-05
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号，用于登录等
     */
    private String userNo;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否删除 0=未删除 1=已删除
     */
    private Integer delFlag;

    /**
     * 创建人
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别 0=女 1=男
     */
    private Integer sex;
}
