package com.zyplayer.doc.db.framework.sse.param;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用SSE参数
 *
 * @author diantu
 * @date 2023/7/17
 */
@Getter
@Setter
public class DbCommonSseParam {

    private String clientId;

    private String loginId;
}
