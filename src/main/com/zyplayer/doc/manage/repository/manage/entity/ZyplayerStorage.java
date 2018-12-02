package com.zyplayer.doc.manage.repository.support.plus.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public class ZyplayerStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String docKey;

    private String docValue;

    private Date creationTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }
    public String getDocValue() {
        return docValue;
    }

    public void setDocValue(String docValue) {
        this.docValue = docValue;
    }
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "ZyplayerStorage{" +
        "id=" + id +
        ", docKey=" + docKey +
        ", docValue=" + docValue +
        ", creationTime=" + creationTime +
        "}";
    }
}
