package com.mg.swagger.framework.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.mg.swagger.framework.filter.MgUiTestFilter;

//@EnableAutoConfiguration
@ComponentScan(basePackages = {
	"com.mg.swagger.controller",
	"com.mg.swagger.framework.service",
})
public class SwaggerCommonConfiguration {

	@Autowired
	private MgUiTestFilter mgUiTestFilter;

	@Bean
	public FilterRegistrationBean mockTestFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(mgUiTestFilter);
		registration.addUrlPatterns("/*");
		registration.setName("mgUiTestFilter");
		registration.setOrder(2);
		return registration;
	}
}
