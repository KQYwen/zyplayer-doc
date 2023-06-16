package com.zyplayer.doc.manage.framework.config;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 按照类加载的情况获取模块的加载状态
 *
 * @author Sh1yu
 * @since 2023年6月15日
 */
@Configuration
public class ZyplayerMoudleKeeper implements ApplicationContextAware {
    HashMap<String, Boolean> moudleInfo = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //获取模块是否启动
    public boolean isMoudleStarted(Class<?> clazz) {
        if (moudleInfo.size() < 1) {
            getMoudleInfo();
        }
        return moudleInfo.get(clazz.getName().split("\\$")[1]);
    }

    //提供模块开启状态数组，给前端控制页面展示
    public HashMap<String, Boolean> getMoudleInfo() {
        if (moudleInfo.size() < 1) {
            synchronized (ZyplayerMoudleKeeper.class) {
                Class<? extends ZyplayerDocConfig> clazz = ZyplayerDocConfig.class;
                Class<?>[] innerClasses = clazz.getClasses();
                for (Class<?> innerClass : innerClasses) {
                    moudleInfo.put(innerClass.getName().split("\\$")[1], isMoudleConfigLoadUp(innerClass));
                }

            }
        }
        return moudleInfo;
    }

    private Boolean isMoudleConfigLoadUp(Class<?> innerClass) {
        Object bean = null;
        try {
            bean = applicationContext.getBean(innerClass);
        } catch (BeansException e) {
            return false;
        }
        return null != bean;
    }
}
