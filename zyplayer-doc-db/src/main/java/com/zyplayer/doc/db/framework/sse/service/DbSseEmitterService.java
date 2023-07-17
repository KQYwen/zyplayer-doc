package com.zyplayer.doc.db.framework.sse.service;

import com.zyplayer.doc.db.framework.sse.param.DbCommonSseParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.function.Consumer;

/**
 * SSE通信Service接口
 *
 * @author diantu
 * @date 2023/7/17
 **/
public interface DbSseEmitterService {

    /**
     * 创建连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public SseEmitter createSseConnect(String clientId,Boolean setHeartBeat, Consumer<DbCommonSseParam> consumer);

    /**
     * 关闭连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public void closeSseConnect(String clientId);

    /**
     * 推送消息到所有客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public void sendMessageToAllClient(String msg);

    /**
     * 根据clientId发送消息给某一客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public void sendMessageToOneClient(String clientId, String msg);
}
