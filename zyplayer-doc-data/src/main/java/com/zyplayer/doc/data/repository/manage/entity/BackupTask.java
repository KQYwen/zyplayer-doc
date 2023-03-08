package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 备份任务信息
 *
 * @author diantu
 * @since 2023年3月3日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "backup_task", autoResultMap = true)
public class BackupTask implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 数据源id
     */
    private Long dbId;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 参数
     */
    private String param;
    /**
     * 状态（0-停止1-启动）
     */
    private Boolean status;
    /**
     * 库名
     */
    private String databaseName;
    /**
     * 表名
     */
    private String tablesName;
    /**
     * 备份方式
     */
    private Integer dataType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除状态（0--未删除1--已删除）
     */
    private Integer delFlag;

}
