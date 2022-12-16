package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 自建接口文档
 * </p>
 *
 * @author 暮光：城中城
 * @since 2022-01-29
 */
@Data
@TableName("api_custom_params")
public class ApiCustomParams implements Serializable {

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
     * 节点ID
     */
    private Long nodeId;

    /**
     * 请求方式：get、head、post、put、patch、delete、options、trace
     */
    private String method;

    /**
     * 接口url
     */
    private String apiUrl;

    /**
     * form参数
     */
    private String formData;

    /**
     * body参数
     */
    private String bodyData;

    /**
     * header参数
     */
    private String headerData;

    /**
     * cookie参数
     */
    private String cookieData;

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
