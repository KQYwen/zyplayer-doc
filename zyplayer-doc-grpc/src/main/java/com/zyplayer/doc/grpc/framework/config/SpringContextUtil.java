package com.zyplayer.doc.grpc.framework.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * context工具类
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	public static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static <T> T getBean(Class<T> clz) {
		return context.getBean(clz);
	}
	
	public static Object getBean(String string) {
		return getApplicationContext().getBean(string);
	}
	
	/**
	 * 获取类
	 *
	 * @param annotationType annotation
	 * @return 类对象
	 */
	public static List<Object> getBeanWithAnnotation(Class<? extends Annotation> annotationType) {
		if (context == null) {
			return null;
		}
		Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(annotationType);
		return new LinkedList<>(beansWithAnnotation.values());
	}
}

