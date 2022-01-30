package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("api_custom_params")
public class ApiCustomParams implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }
    public String getBodyData() {
        return bodyData;
    }

    public void setBodyData(String bodyData) {
        this.bodyData = bodyData;
    }
    public String getHeaderData() {
        return headerData;
    }

    public void setHeaderData(String headerData) {
        this.headerData = headerData;
    }
    public String getCookieData() {
        return cookieData;
    }

    public void setCookieData(String cookieData) {
        this.cookieData = cookieData;
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
        return "ApiCustomParams{" +
            "id=" + id +
            ", docId=" + docId +
            ", nodeId=" + nodeId +
            ", method=" + method +
            ", apiUrl=" + apiUrl +
            ", formData=" + formData +
            ", bodyData=" + bodyData +
            ", headerData=" + headerData +
            ", cookieData=" + cookieData +
            ", createUserId=" + createUserId +
            ", createUserName=" + createUserName +
            ", createTime=" + createTime +
            ", yn=" + yn +
        "}";
    }
    
    public Long getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
}
