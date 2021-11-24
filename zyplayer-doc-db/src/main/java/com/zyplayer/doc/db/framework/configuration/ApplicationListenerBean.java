package com.zyplayer.doc.db.framework.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 应用启动监听
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Component
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	
	}
	
}
