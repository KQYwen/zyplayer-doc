package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 备份记录信息
 *
 * @author diantu
 * @since 2023年3月3日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("backup_log")
public class BackupLog implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 数据源id
     */
    private Long dbId;
    /**
     * 来源（手动备份|自动备份）
     */
    private String category;
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
     * 是否压缩
     */
    private Boolean isCompress;
    /**
     * 是否上传
     */
    private Boolean isUpload;
    /**
     * 是否删除
     */
    private Boolean isDelete;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 备份状态（-1-失败0-备份中1-上传中200-成功）
     */
    private Integer status;
    /**
     * 备份信息
     */
    private String msg;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 耗时(ms)
     */
    private Long spendTime;
    /**
     * 删除状态（0--未删除1--已删除）
     */
    private Integer delFlag;

}
