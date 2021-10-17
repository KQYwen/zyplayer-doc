package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * swagger文档地址
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-10-15
 */
public class SwaggerDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档类型 1=url 2=swagger文档json
     */
    private Integer docType;

    /**
     * 文档URL地址
     */
    private String docUrl;

    /**
     * swagger文档json内容
     */
    private String jsonContent;

    /**
     * 重写的域名
     */
    private String rewriteDomain;

    /**
     * 是否开放访问 0=否 1=是
     */
    private Integer openVisit;

    /**
     * 状态 1=启用 2=禁用
     */
    private Integer docStatus;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }
    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }
    public String getRewriteDomain() {
        return rewriteDomain;
    }

    public void setRewriteDomain(String rewriteDomain) {
        this.rewriteDomain = rewriteDomain;
    }
    public Integer getOpenVisit() {
        return openVisit;
    }

    public void setOpenVisit(Integer openVisit) {
        this.openVisit = openVisit;
    }
    public Integer getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(Integer docStatus) {
        this.docStatus = docStatus;
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
        return "SwaggerDoc{" +
        "id=" + id +
        ", name=" + name +
        ", docType=" + docType +
        ", docUrl=" + docUrl +
        ", jsonContent=" + jsonContent +
        ", rewriteDomain=" + rewriteDomain +
        ", openVisit=" + openVisit +
        ", docStatus=" + docStatus +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", yn=" + yn +
        "}";
    }
}
