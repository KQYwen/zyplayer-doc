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
 * @since 2019-09-30
 */
@Data
@TableName("db_transfer_task")
public class DbTransferTask implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 查询数据源ID
     */
    private Long queryDatasourceId;

    /**
     * 入库数据源ID
     */
    private Long storageDatasourceId;

    /**
     * 查询数据的sql
     */
    private String querySql;

    /**
     * 数据入库的sql
     */
    private String storageSql;

    /**
     * 自动查询总条数 0=否 1=是
     */
    private Integer needCount;

    /**
     * 最后执行状态 0=未执行 1=执行中 2=执行成功 3=执行失败 4=取消执行
     */
    private Integer lastExecuteStatus;

    /**
     * 最后执行时间
     */
    private Date lastExecuteTime;

    /**
     * 最后执行信息
     */
    private String lastExecuteInfo;

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
