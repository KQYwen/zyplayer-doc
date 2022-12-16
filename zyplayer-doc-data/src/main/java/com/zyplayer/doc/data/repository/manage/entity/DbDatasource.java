package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源对象
 *
 * @author 暮光：城中城
 * @since 2019-07-04
 */
@Data
@TableName("db_datasource")
public class DbDatasource implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据源驱动类
     */
    private String driverClassName;

    /**
     * 数据源地址
     */
    private String sourceUrl;

    /**
     * 数据源用户名
     */
    private String sourceName;

    /**
     * 数据源密码
     */
    private String sourcePassword;

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
     * 数据源名称
     */
    private String name;

    /**
     * 数据源分组
     */
    private String groupName;
}
