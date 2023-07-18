package com.zyplayer.doc.db.framework.sse.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.db.framework.sse.param.DbCommonSseParam;
import com.zyplayer.doc.db.framework.sse.service.DbSseEmitterService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * SSE通信控制器
 *
 * @author diantu
 * @date 2023/7/17
 **/
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/doc-db")
public class DbSseEmitterController {

    @Resource
    private DbSseEmitterService dbSseEmitterService;

    /**
     * 创建sse连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @GetMapping("/sse/createConnect")
    public SseEmitter createConnect(String clientId,
                                    @RequestParam(required = false)Boolean setHeartBeat,
                                    @RequestParam(required = false)Boolean defaultHeartbeat,
                                    @RequestParam(required = false) Consumer<DbCommonSseParam> consumer){
        return dbSseEmitterService.createSseConnect(clientId,setHeartBeat,defaultHeartbeat,consumer);
    }

    /**
     * 关闭sse连接
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @GetMapping("/sse/closeSseConnect")
    public void closeSseConnect(String clientId){
        dbSseEmitterService.closeSseConnect(clientId);
    }

    /**
     * 推送消息到所有客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @PostMapping("/sse/broadcast")
    public void sendMessageToAllClient(@RequestBody(required = false) String msg){
        dbSseEmitterService.sendMessageToAllClient(msg);
    }

    /**
     * 根据clientId发送消息给某一客户端
     *
     * @author diantu
     * @date 2023/7/17
     **/
    @PostMapping("/sse/sendMessage")
    public void sendMessageToOneClient(String clientId,String msg){
        dbSseEmitterService.sendMessageToOneClient(clientId,msg);
    }

}
