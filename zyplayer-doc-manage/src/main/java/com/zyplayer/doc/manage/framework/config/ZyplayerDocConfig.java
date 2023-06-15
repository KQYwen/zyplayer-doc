package com.zyplayer.doc.manage.framework.config;

import com.zyplayer.doc.api.framework.config.EnableDocApi;
import com.zyplayer.doc.db.framework.configuration.EnableDocDb;
import com.zyplayer.doc.wiki.framework.config.EnableDocWiki;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 按需开启zyplayer-doc所有的服务
 *
 * @author 暮光：城中城
 * @author Sh1yu 2023年6月15日
 * @since 2019年3月31日
 */
@Configuration
public class ZyplayerDocConfig {
    //wiki模块的开启配置
    @Value("${zyplayer.doc.manage.enable.wiki:true}")
    private boolean enableWiki;

    //db模块的开启配置
    @Value("${zyplayer.doc.manage.enable.db:true}")
    private boolean enableDb;

    //api模块的开启配置
    @Value("${zyplayer.doc.manage.enable.api:true}")
    private boolean enableApi;

    @EnableDocWiki
    //wiki模块加载注解条件化，配合配置文件决定是否加载
    @ConditionalOnProperty(prefix = "zyplayer.doc.manage.enable", name = "wiki", matchIfMissing = true)
    public class enableDocWiki {
    }

    @EnableDocDb
    //db模块加载注解条件化，配合配置文件决定是否加载
    @ConditionalOnProperty(prefix = "zyplayer.doc.manage.enable", name = "db", matchIfMissing = true)
    public class enableDocDb {
    }

    @EnableDocApi
    //api模块加载注解条件化，配合配置文件决定是否加载
    @ConditionalOnProperty(prefix = "zyplayer.doc.manage.enable", name = "api", matchIfMissing = true)
    public class enableDocApi {
    }

    public boolean isEnableWiki() {
        return enableWiki;
    }

    public void setEnableWiki(boolean enableWiki) {
        this.enableWiki = enableWiki;
    }

    public boolean isEnableDb() {
        return enableDb;
    }

    public void setEnableDb(boolean enableDb) {
        this.enableDb = enableDb;
    }

    public boolean isEnableApi() {
        return enableApi;
    }

    public void setEnableApi(boolean enableApi) {
        this.enableApi = enableApi;
    }

    //提供模块开启状态数组，给前端控制页面展示
    public HashMap<String,Boolean> getMoudleInfo(){
        HashMap<String,Boolean> moudleInfo = new HashMap<>();
        moudleInfo.put("enableWiki",this.enableWiki);
        moudleInfo.put("enableDb",this.enableDb);
        moudleInfo.put("enableApi",this.enableApi);
        return moudleInfo;
    }
}
