package com.zyplayer.doc.manage.framework.interceptor;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.manage.framework.config.ZyplayerDocConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模块未开启时判定失败响应拦截器
 *
 * @author Sh1yu
 * @since 2023年6月15日
 */
@Component
public class MoudleMissingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MoudleMissingInterceptor.class);

    @Resource
    ZyplayerDocConfig zyplayerDocConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        String simpleMoudleUri = requestURI.replace("/zyplayer-doc/", "");
        if (simpleMoudleUri.startsWith("zyplayer-doc-wiki") && !zyplayerDocConfig.isEnableWiki()) {
            doFailResponse(response, "wiki模块未启动，无法提供相应功能");
            return false;
        }
        if (simpleMoudleUri.startsWith("zyplayer-doc-db") && !zyplayerDocConfig.isEnableDb()) {
            doFailResponse(response, "db模块未启动，无法提供相应功能");
            return false;
        }
        if (simpleMoudleUri.startsWith("zyplayer-doc-api") && !zyplayerDocConfig.isEnableApi()) {
            doFailResponse(response, "api模块未启动，无法提供相应功能");
            return false;
        }
        return true;
    }

    public void doFailResponse(HttpServletResponse response, String msg) {
        DocResponseJson.warn(msg).send(response);
    }

}
