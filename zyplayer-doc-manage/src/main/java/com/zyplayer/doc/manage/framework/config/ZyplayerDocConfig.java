package com.zyplayer.doc.manage.framework.config;

import com.zyplayer.doc.api.framework.config.EnableDocApi;
import com.zyplayer.doc.db.framework.configuration.EnableDocDb;
import com.zyplayer.doc.wiki.framework.config.EnableDocWiki;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 按需开启zyplayer-doc所有的服务
 *
 * @author 暮光：城中城
 * @author Sh1yu 2023年6月15日
 * @since 2019年3月31日
 * 规范：添加模块的类，命名需要和前端接受模块开启状态的参数一致，即  enbaleXxxxx enable模块名
 */
@Configuration
public class ZyplayerDocConfig  {
    @EnableDocWiki
    //wiki模块加载注解条件化，配合配置文件决定是否加载
    @ConditionalOnProperty(prefix = "zyplayer.doc.manage.enable", name = "wiki", matchIfMissing = true)
    public static class enableWiki {
    }

    @EnableDocDb
    //db模块加载注解条件化，配合配置文件决定是否加载
    @ConditionalOnProperty(prefix = "zyplayer.doc.manage.enable", name = "db", matchIfMissing = true)
    public static class enableDb {
    }

    @EnableDocApi
    //api模块加载注解条件化，配合配置文件决定是否加载
    @ConditionalOnProperty(prefix = "zyplayer.doc.manage.enable", name = "api", matchIfMissing = true)
    public static class enableApi {
    }
}
