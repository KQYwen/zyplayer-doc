package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 自建接口文档文件夹
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-12-22
 */
@Data
@TableName("api_custom_folder")
public class ApiCustomFolder implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * api_doc主键ID
     */
    private Long docId;

    /**
     * 父文件夹ID
     */
    private Long parentFolderId;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 文件夹说明
     */
    private String folderDesc;

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
}
