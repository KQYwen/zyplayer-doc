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
 * @since 2019-03-11
 */
@Data
@TableName("wiki_space")
public class WikiSpace implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 空间名
     */
    private String name;

    /**
     * 空间类型 1=公司 2=个人 3=私人
     */
    private Integer type;

    /**
     * 描述
     */
    private String spaceExplain;

    /**
     * 编辑类型 0=可编辑 1=不允许编辑
     */
    private Integer editType;

    /**
     * 目录延迟加载 0=否 1=是
     */
    private Integer treeLazyLoad;

    /**
     * 是否是开放文档 0=否 1=是
     */
    private Integer openDoc;

    /**
     * 唯一UUID
     */
    private String uuid;

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
