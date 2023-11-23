package com.zyplayer.doc.data.repository.manage.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *     模板与文档信息的Vo
 * </p>
 *
 * @author Sh1yu
 * @since 2023-08-24
 */
@Data
public class WikiPageTemplateInfoVo{

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
     * 是否收藏 0=否 1=是
     */
    private Integer favorite;

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

    /**
     * 模板分享状态 0=个人模板 1=共享模板
     */
    private Integer shareStatus;

    /**
     * 模板ID
     */
    private Integer templateId;

    /**
     * 标签
     */
    private String tags;

    /**
     * 标题
     */
    private String title;

    /**
     * 模板的内容
     */
    private String content;
}
