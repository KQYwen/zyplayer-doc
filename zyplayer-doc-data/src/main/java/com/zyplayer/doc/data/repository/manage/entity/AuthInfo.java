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
@TableName("auth_info")
public class AuthInfo implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名
     */
    private String authName;

    /**
     * 权限说明
     */
    private String authDesc;

    /**
     * 是否可编辑 0=否 1=是
     */
    private Integer canEdit;

    /**
     * 创建人
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Date creationTime;
}
