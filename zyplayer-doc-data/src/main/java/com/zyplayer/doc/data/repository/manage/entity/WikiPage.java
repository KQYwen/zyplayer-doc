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
 * @since 2019-06-06
 */
@Data
@TableName("wiki_page")
public class WikiPage implements Serializable {

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 名字
     */
    private String name;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 节点类型 0=有子节点 1=终节点
     */
    private Integer nodeType;

    /**
     * 赞的数量
     */
    private Integer zanNum;

    /**
     * 编辑类型 0=可编辑 1=不允许编辑
     */
    private Integer editType;

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
     * 修改人ID
     */
    private Long updateUserId;

    /**
     * 修改人名字
     */
    private String updateUserName;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 0=有效 1=删除
     */
    private Integer delFlag;

    /**
     * 阅读数
     */
    private Integer viewNum;

    /**
     * 顺序
     */
    private Integer seqNo;

    /**
     * 编辑框类型 1=HTML 2=Markdown
     */
    private Integer editorType;

}
