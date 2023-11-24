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
public class ZyplayerModuleKeeper implements ApplicationContextAware {
    final HashMap<String, Boolean> moduleInfo = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //获取模块是否启动
    public boolean ismoduleStarted(Class<?> clazz) {
        if (moduleInfo.isEmpty()) {
            getmoduleInfo();
        }
        return moduleInfo.get(clazz.getName().split("\\$")[1]);
    }

    //提供模块开启状态数组，给前端控制页面展示
    public HashMap<String, Boolean> getmoduleInfo() {
        if (moduleInfo.isEmpty()) {
            synchronized (ZyplayerModuleKeeper.class) {
                Class<? extends ZyplayerDocConfig> clazz = ZyplayerDocConfig.class;
                Class<?>[] innerClasses = clazz.getClasses();
                for (Class<?> innerClass : innerClasses) {
                    moduleInfo.put(innerClass.getName().split("\\$")[1], ismoduleConfigLoadUp(innerClass));
                }

            }
        }
        return moduleInfo;
    }

    private Boolean ismoduleConfigLoadUp(Class<?> innerClass) {
        Object bean = null;
        try {
            bean = applicationContext.getBean(innerClass);
        } catch (BeansException e) {
            return false;
        }
        return true;
    }
}
