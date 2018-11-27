
package com.zyplayer.doc.manage.framework.config;

import org.springframework.context.annotation.Configuration;

import com.mg.swagger.framework.configuration.EnableSwaggerMgUi;

/**
 * 不需要管理本项目的文档，只需要开启@EnableSwaggerMgUi即可
 * @author 暮光：城中城
 * @since 2018年11月11日
 */
@Configuration
@EnableSwaggerMgUi(
	selfDoc = false,// 不开启自身的文档，本项目只当管理文档的项目使用
	defaultResources = {// selfDoc=false时有用，启动后第一次访问没有数据情况下需要加载进来的swagger-resources地址
		//"http://localhost:8080/swagger-resources"
	}
)
public class SwaggerConfiguration {
	
}
