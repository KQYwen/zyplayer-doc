package com.zyplayer.doc.db.framework.sse.util;

import cn.hutool.core.util.StrUtil;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.sse.enums.DbSseEmitterParameterEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

/**
 * SseEmitter工具类
 *
 * @author diantu
 * @date 2023/7/17
 **/
@Slf4j
public class DbSseCacheUtil {

    /**
     * 创建一个容器来存储所有的 SseEmitter(使用ConcurrentHashMap是因为它是线程安全的)。
     */
    public static final Map<String, Map<String,Object>> sseCache = new ConcurrentHashMap<>();


    /**
     * 根据客户端id获取连接对象
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static SseEmitter getSseEmitterByClientId(String clientId) {
        Map<String,Object> map = sseCache.get(clientId);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return (SseEmitter) map.get(DbSseEmitterParameterEnum.EMITTER.getValue());
    }

    /**
     * 根据客户端id获取心跳
     *
     * @author diantu
     * @date 2023/7/18
     **/
    public static ScheduledFuture<?> getSseFutureByClientId(String clientId) {
        Map<String,Object> map = sseCache.get(clientId);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return (ScheduledFuture<?>) map.get(DbSseEmitterParameterEnum.FUTURE.getValue());
    }

    /**
     * 根据客户端id获取用户id
     *
     * @author diantu
     * @date 2023/7/18
     **/
    public static ScheduledFuture<?> getLoginIdByClientId(String clientId) {
        Map<String,Object> map = sseCache.get(clientId);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return (ScheduledFuture<?>) map.get(DbSseEmitterParameterEnum.LOGINID.getValue());
    }

    /**
     * 根据用户id获取客户端id
     *
     * @author diantu
     * @date 2023/7/18
     **/
    public static String getClientIdByLoginId(String loginId){
        if(existSseCache()){
            for (Map.Entry<String, Map<String, Object>> entry : sseCache.entrySet()) {
                Map<String,Object> map = sseCache.get(entry.getKey());
                String lId = (String) map.get(DbSseEmitterParameterEnum.LOGINID.getValue());
                if(loginId.equals(lId)){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * 判断容器是否存在连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static boolean existSseCache() {
        return !sseCache.isEmpty();
    }

    /**
     * 判断连接是否有效
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static boolean connectionValidity(String clientId,String loginId){
        if(sseCache.get(clientId) == null){
            return false;
        }
        return Objects.equals(loginId, sseCache.get(clientId).get(DbSseEmitterParameterEnum.LOGINID.getValue()));
    }

    /**
     * 增加连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static void addConnection(String clientId,String loginId, SseEmitter emitter, ScheduledFuture<?> future) {
        final SseEmitter oldEmitter = getSseEmitterByClientId(clientId);
        if (oldEmitter != null) {
            throw new ConfirmException("连接已存在:"+clientId);
        }
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put(DbSseEmitterParameterEnum.EMITTER.getValue(),emitter);
        if(future!=null){
            map.put(DbSseEmitterParameterEnum.FUTURE.getValue(), future);
        }
        map.put(DbSseEmitterParameterEnum.LOGINID.getValue(), loginId);
        sseCache.put(clientId, map);
    }

    /**
     * 移除连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static void removeConnection(String clientId) {
        SseEmitter emitter = getSseEmitterByClientId(clientId);
        if (emitter != null) {
            cancelScheduledFuture(clientId);
        }
        sseCache.remove(clientId);
        log.info("移除连接:{}", clientId);
    }

    /**
     * 中断心跳发送任务
     *
     * @author diantu
     * @date 2023/7/17
     */
    public static void cancelScheduledFuture(String clientId){
        ScheduledFuture<?> future = getSseFutureByClientId(clientId);
        if (future != null) {
            future.cancel(true);
        }
    }


    /**
     * 长链接完成后回调
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static Runnable completionCallBack(String clientId) {
        return () -> {
            log.info("结束连接:{}", clientId);
            removeConnection(clientId);
            cancelScheduledFuture(clientId);
        };
    }

    /**
     * 连接超时回调
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static Runnable timeoutCallBack(String clientId){
        return ()->{
            log.info("连接超时:{}", clientId);
            removeConnection(clientId);
            cancelScheduledFuture(clientId);
        };
    }

    /**
     * 推送消息异常时回调
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static Consumer<Throwable> errorCallBack(String clientId) {
        return throwable -> {
            log.info("推送消息异常:{}", clientId);
            removeConnection(clientId);
            cancelScheduledFuture(clientId);
        };
    }

    /**
     * 推送消息到所有客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static void sendMessageToAllClient(String msg) {
        if (!existSseCache()) {
            return;
        }
        // 判断发送的消息是否为空
        if (StrUtil.isEmpty(msg)){
            log.info("群发消息为空");
            return;
        }
        for (Map.Entry<String, Map<String, Object>> entry : sseCache.entrySet()) {
            sendMessageToClientByClientId(entry.getKey(), DocDbResponseJson.ok(msg));
        }
    }

    /**
     * 根据clientId发送消息给某一客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static void sendMessageToOneClient(String clientId, String msg) {
        if (StrUtil.isEmpty(clientId)){
            log.info("客户端ID为空");
            return;
        }
        if (StrUtil.isEmpty(msg)){
            log.info("向客户端{}推送消息为空",clientId);
            return;
        }
        sendMessageToClientByClientId(clientId,DocDbResponseJson.ok(msg));
    }

    /**
     * 推送消息到客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    public static void sendMessageToClientByClientId(String clientId, DocDbResponseJson message) {
        Map<String, Object> map = sseCache.get(clientId);
        if (map==null|| map.isEmpty()) {
            log.error("推送消息失败:客户端{}未创建长链接,失败消息:{}",clientId, message.toString());
            return;
        }
        SseEmitter.SseEventBuilder sendData = SseEmitter.event().data(message,MediaType.APPLICATION_JSON);
        SseEmitter sseEmitter = getSseEmitterByClientId(clientId);
        try {
            Objects.requireNonNull(sseEmitter).send(sendData);
        } catch (Exception e) {
            log.error("推送消息失败,报错异常:",e);
            removeConnection(clientId);
        }
    }

}
