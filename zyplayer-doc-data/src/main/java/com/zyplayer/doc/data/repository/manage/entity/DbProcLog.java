package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
@Data
@TableName("db_proc_log")
public class DbProcLog implements Serializable {

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
}
