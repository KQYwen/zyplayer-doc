package com.zyplayer.doc.db.framework.db.enums;

import lombok.AllArgsConstructor;

/**
 * 备份类型枚举类
 *
 * @author diantu
 * @since 2023年3月8日
 */
@AllArgsConstructor
public enum BackupCategoryEnum {

    /**
     * 手动备份
     */
    MANUAL("0", "手动备份"),

    /**
     * 自动备份
     */
    AUTO("1", "自动备份");
    /**
     * 编码
     */
    private final String code;
    /**
     * 信息
     */
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
