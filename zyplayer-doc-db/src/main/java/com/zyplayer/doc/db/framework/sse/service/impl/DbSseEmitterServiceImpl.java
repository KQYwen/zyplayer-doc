package com.zyplayer.doc.db.framework.sse.service.impl;

import cn.hutool.core.util.IdUtil;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.sse.param.DbCommonSseParam;
import com.zyplayer.doc.db.framework.sse.service.DbSseEmitterService;
import com.zyplayer.doc.db.framework.sse.util.DbSseCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * SSE通信Service接口实现类
 *
 * @author diantu
 * @date 2023/7/17
 **/
@Slf4j
@Service
public class DbSseEmitterServiceImpl implements DbSseEmitterService {

    /**
     * 心跳线程池
     */
    private static final ScheduledExecutorService heartbeatExecutors = Executors.newScheduledThreadPool(10);


    /**
     * 创建SSE连接
     *
     * @param clientId 客户端id,不传则自动生成
     * @param setHeartBeat 是否设置心跳定时任务,默认为false（true:设置 false:不设置）
     * @param defaultHeartbeat 是否使用默认心跳任务
     * @param consumer 自定义心跳任务,需要自定义实现Consumer接口中的accept方法（setHeartBeat必须为true,defaultHeartbeat为false才有意义）
     * @return 初次建立连接会推送客户端id,状态码为0
     * @author diantu
     * @date 2023/7/17
     **/
    @Override
    public SseEmitter createSseConnect(String clientId, Boolean setHeartBeat, Boolean defaultHeartbeat, Consumer<DbCommonSseParam> consumer) {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);
        DocUserDetails currentUser = DocUserUtil.getCurrentUser();
        String loginId = currentUser.getUserId().toString();
        // 判断连接是否有效
        if (DbSseCacheUtil.connectionValidity(clientId,loginId)) {
            return DbSseCacheUtil.getSseEmitterByClientId(clientId);
        }else{
            DbSseCacheUtil.removeConnection(clientId);
        }
        clientId = IdUtil.simpleUUID();
        String finalClientId = clientId;
        // 增加心跳
        final ScheduledFuture<?> future;
        // 是否设置心跳任务
        if (setHeartBeat!=null&&setHeartBeat) {
            //是否使用默认心跳任务
            if(defaultHeartbeat!=null&&defaultHeartbeat){
                //默认心跳任务
                future = heartbeatExecutors.scheduleAtFixedRate(() ->
                                DbSseCacheUtil.sendMessageToOneClient(finalClientId,finalClientId+"-"+loginId),
                        2, 10, TimeUnit.SECONDS);
            }else{
                //自定义心跳任务
                DbCommonSseParam commonSseParam = new DbCommonSseParam();
                commonSseParam.setClientId(clientId);
                commonSseParam.setLoginId(loginId);
                future = heartbeatExecutors.scheduleAtFixedRate(() -> consumer.accept(commonSseParam),
                        2, 10, TimeUnit.SECONDS);
            }
            // 增加连接
            DbSseCacheUtil.addConnection(clientId, loginId, sseEmitter, future);
        }else{
            // 增加连接
            DbSseCacheUtil.addConnection(clientId, loginId, sseEmitter, null);
        }
        // 长链接完成后回调(即关闭连接时调用)
        sseEmitter.onCompletion(DbSseCacheUtil.completionCallBack(clientId));
        // 连接超时回调
        sseEmitter.onTimeout(DbSseCacheUtil.timeoutCallBack(clientId));
        // 推送消息异常回调
        sseEmitter.onError(DbSseCacheUtil.errorCallBack(clientId));
        // 初次建立连接,推送客户端id
        DocDbResponseJson message = new DocDbResponseJson(0,"",clientId);
        DbSseCacheUtil.sendMessageToClientByClientId(clientId,message);
        return sseEmitter;
    }

    /**
     * 关闭连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @Override
    public void closeSseConnect(String clientId){
        DbSseCacheUtil.removeConnection(clientId);
    }

    /**
     * 推送消息到所有客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @Override
    public void sendMessageToAllClient(String msg) {
        DbSseCacheUtil.sendMessageToAllClient(msg);
    }

    /**
     * 根据clientId发送消息给某一客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @Override
    public void sendMessageToOneClient(String clientId, String msg) {
        DbSseCacheUtil.sendMessageToOneClient(clientId,msg);
    }

}
