package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户消息表
 * </p>
 *
 * @author 暮光：城中城
 * @since 2020-06-23
 */
public class UserMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 系统类型 1=manage 2=wiki 3=db
     */
    private Integer sysType;

    /**
     * 消息类型 1=普通文本消息 2=wiki文档创建 3=wiki文档删除 4=wiki文档编辑 5=wiki文档权限修改 6=wiki文档评论 7=wiki文档删除评论
     */
    private Integer msgType;

    /**
     * 消息关联的数据ID
     */
    private Long dataId;

    /**
     * 消息关联的数据说明
     */
    private String dataDesc;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息状态 0=未读 1=已读 2=已删除
     */
    private Integer msgStatus;

    /**
     * 操作人用户ID
     */
    private Long operatorUserId;

    /**
     * 操作人用户名
     */
    private String operatorUserName;

    /**
     * 影响用户ID
     */
    private Long affectUserId;

    /**
     * 影响人用户名
     */
    private String affectUserName;

    /**
     * 接收人用户ID
     */
    private Long acceptUserId;

    /**
     * 创建时间
     */
    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getSysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }
    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }
    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }
    public Integer getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }
    public Long getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Long operatorUserId) {
        this.operatorUserId = operatorUserId;
    }
    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }
    public Long getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Long acceptUserId) {
        this.acceptUserId = acceptUserId;
    }
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
        "id=" + id +
        ", sysType=" + sysType +
        ", msgType=" + msgType +
        ", dataId=" + dataId +
        ", dataDesc=" + dataDesc +
        ", msgStatus=" + msgStatus +
        ", operatorUserId=" + operatorUserId +
        ", operatorUserName=" + operatorUserName +
        ", acceptUserId=" + acceptUserId +
        ", creationTime=" + creationTime +
        "}";
    }
    
    public String getMsgContent() {
        return msgContent;
    }
    
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
    
    public Long getAffectUserId() {
        return affectUserId;
    }
    
    public void setAffectUserId(Long affectUserId) {
        this.affectUserId = affectUserId;
    }
    
    public String getAffectUserName() {
        return affectUserName;
    }
    
    public void setAffectUserName(String affectUserName) {
        this.affectUserName = affectUserName;
    }
}
