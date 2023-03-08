package com.zyplayer.doc.db.framework.db.vo;

import lombok.Data;

import java.io.File;

/**
 * 备份响应数据
 *
 * @author diantu
 * @since 2023年3月3日
 */
@Data
public class BackupRespVO {

    private String msg;

    private File file;

    public boolean isSuccess() {
        return null != this.file && 0 < this.file.length();
    }
}
