package com.zyplayer.doc.db.framework.sse.enums;

import lombok.Getter;

/**
 * SSE通信参数枚举
 *
 * @author diantu
 * @date 2023/7/17
 **/
@Getter
public enum DbSseEmitterParameterEnum {

    /**
     * 通信
     */
    EMITTER("EMITTER"),

    /**
     * 心跳
     */
    FUTURE("FUTURE"),

    /**
     * 用户ID
     */
    LOGINID("LOGINID");

    private final String value;

    DbSseEmitterParameterEnum(String value) {
        this.value = value;
    }

}
