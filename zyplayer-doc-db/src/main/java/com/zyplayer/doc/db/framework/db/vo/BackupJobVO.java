package com.zyplayer.doc.db.framework.db.vo;

import lombok.Data;

/**
 * 备份任务数据
 *
 * @author diantu
 * @since 2023年3月3日
 */
@Data
public class BackupJobVO {

    /**
     * 数据源ID
     */
    private Integer dbId;

    /**
     * 地址
     */
    private String host;
    /**
     * 端口号
     */
    private String port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 数据库表名
     */
    private String[] tables;
    /**
     * 备份形式
     * 0-只备份表结构
     * 1-只备份表数据
     * 2-备份表结构+表数据
     */
    private Integer dataType;
    /**
     * 是否压缩
     */
    private Boolean isCompress = false;
    /**
     * 是否上传
     */
    private Boolean isUpload = false;
    /**
     * 是否删除
     */
    private Boolean isDelete = false;
}
