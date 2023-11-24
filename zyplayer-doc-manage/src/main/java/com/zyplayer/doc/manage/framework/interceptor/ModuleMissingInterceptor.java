package com.zyplayer.doc.manage.framework.interceptor;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.manage.framework.config.ZyplayerDocConfig;
import com.zyplayer.doc.manage.framework.config.ZyplayerModuleKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模块未开启时判定失败响应拦截器
 *
 * @author Sh1yu
 * @since 2023年6月15日
 */
@Component
public class ModuleMissingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ModuleMissingInterceptor.class);


    private  static boolean enableWiki = true;
    private  static boolean enableDb = true;
    private  static boolean enableApi = true;

    public ModuleMissingInterceptor(ZyplayerModuleKeeper zyplayerModuleKeeper){
        enableWiki= zyplayerModuleKeeper.ismoduleStarted(ZyplayerDocConfig.enableWiki.class);
        enableDb= zyplayerModuleKeeper.ismoduleStarted(ZyplayerDocConfig.enableDb.class);
        enableApi= zyplayerModuleKeeper.ismoduleStarted(ZyplayerDocConfig.enableApi.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        String simplemoduleUri = requestURI.replace("/zyplayer-doc/", "");
        if (simplemoduleUri.startsWith("zyplayer-doc-wiki") && !enableWiki) {
            doFailResponse(response, "wiki模块未开启，无法提供相应功能");
            return false;
        }
        if (simplemoduleUri.startsWith("zyplayer-doc-db") && !enableDb) {
            doFailResponse(response, "db模块未开启，无法提供相应功能");
            return false;
        }
        if (simplemoduleUri.startsWith("zyplayer-doc-api") && !enableApi) {
            doFailResponse(response, "api模块未开启，无法提供相应功能");
            return false;
        }
        return true;
    }

    public boolean validate(String simpleModuleUri,String uriPrefix,boolean moduleEnabled,HttpServletResponse response,String failMsg){
        if (simpleModuleUri.startsWith(uriPrefix) && !moduleEnabled) {
            doFailResponse(response, failMsg);
            return false;
        }
        return true;
    }

    public void doFailResponse(HttpServletResponse response, String msg) {
        DocResponseJson.warn(msg).send(response);
    }

}
