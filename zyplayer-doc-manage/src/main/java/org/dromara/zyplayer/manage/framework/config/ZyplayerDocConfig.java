package org.dromara.zyplayer.manage.framework.config;

import org.dromara.zyplayer.wiki.framework.config.EnableDocWiki;
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
public class ZyplayerDocConfig {
	
	@EnableDocWiki
	public static class enableWiki {
	}
}
