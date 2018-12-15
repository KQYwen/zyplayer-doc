package com.zyplayer.doc.manage.framework.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 自定义错误页面
 * @author 暮光：城中城
 * @since 2018年11月11日
 */
@Configuration
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/statics/common/403.html"));
		factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/statics/common/404.html"));
		factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/statics/common/500.html"));
	}

}
