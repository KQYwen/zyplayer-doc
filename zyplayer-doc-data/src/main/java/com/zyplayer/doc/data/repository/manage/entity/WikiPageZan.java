package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-05
 */
public class WikiPageZan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 页面ID
     */
    private Long pageId;

    /**
     * 评论ID
     */
    private Long commentId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    @Override
    public String toString() {
        return "WikiPageZan{" +
        "id=" + id +
        ", pageId=" + pageId +
        ", commentId=" + commentId +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", yn=" + yn +
        "}";
    }
}
