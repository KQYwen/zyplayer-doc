package com.zyplayer.doc.manage.framework.config;

import com.zyplayer.doc.db.framework.configuration.EnableDocDb;
import com.zyplayer.doc.dubbo.framework.config.EnableDocDubbo;
import com.zyplayer.doc.swaggerplus.framework.config.EnableDocSwaggerPlus;
import com.zyplayer.doc.wiki.framework.config.EnableDocWiki;
import org.springframework.context.annotation.Configuration;

/**
 * 按需开启zyplayer-doc所有的服务
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Configuration
public class ZyplayerDocConfig {
	
	@EnableDocWiki
	public class enableDocWiki{}
	
	@EnableDocDubbo
	public class enableDocDubbo{}
	
	@EnableDocDb
	public class enableDocDb{}
	
	@EnableDocSwaggerPlus
	public class enableDocSwaggerPlus{}
}
