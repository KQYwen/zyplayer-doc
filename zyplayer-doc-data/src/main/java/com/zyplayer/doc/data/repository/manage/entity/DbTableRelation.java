package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 表关系
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
@Data
@TableName("db_table_relation")
public class DbTableRelation implements Serializable {

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
}
