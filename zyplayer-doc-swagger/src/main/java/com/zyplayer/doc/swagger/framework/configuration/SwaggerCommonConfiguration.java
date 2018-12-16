package com.zyplayer.doc.swagger.framework.configuration;

import org.springframework.context.annotation.ComponentScan;

//@EnableAutoConfiguration
@ComponentScan(basePackages = {
	"com.zyplayer.doc.swagger.controller",
	"com.zyplayer.doc.swagger.framework.service",
})
public class SwaggerCommonConfiguration {

//	不再默认开启拦截
//	@Autowired
//	private ZyplayerApiTestFilter mgUiTestFilter;
//
//	@Bean
//	public FilterRegistrationBean mockTestFilter() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(mgUiTestFilter);
//		registration.addUrlPatterns("/*");
//		registration.setName("mgUiTestFilter");
//		registration.setOrder(2);
//		return registration;
//	}
}
