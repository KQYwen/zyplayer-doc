package com.zyplayer.doc.manage.framework.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zyplayer.doc.data.config.DocLoginOriginInterceptor;
import com.zyplayer.doc.manage.framework.interceptor.RequestInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Resource
	RequestInfoInterceptor requestInfoInterceptor;
	@Resource
	DocLoginOriginInterceptor docLoginOriginInterceptor;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(true);
		DateFormatter dateFormatter = new DateFormatter();
		dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
		dateFormatter.setLenient(true);
		registry.addFormatter(dateFormatter);
	}

	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(new MediaType("application", "json", Charset.forName("UTF-8")));
		fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		return fastJsonHttpMessageConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(0, fastJsonHttpMessageConverter());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(docLoginOriginInterceptor);
		registry.addInterceptor(requestInfoInterceptor).excludePathPatterns("/**/*.js", "/**/*.css", "/**/*.png",
				"/**/*.gif", "/**/*.jpg", "/**/*.jpeg", "/**/fonts/*");
	}

}
