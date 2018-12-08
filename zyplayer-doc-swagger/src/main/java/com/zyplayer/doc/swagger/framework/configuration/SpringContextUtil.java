package com.zyplayer.doc.swagger.framework.configuration;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * context工具类
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
	 * @param annotationType annotation
	 * @return 类对象
	 */
	public static Object getBeanWithAnnotation(Class<? extends Annotation> annotationType) {
		if (context == null) {
			return null;
		}
		Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(annotationType);
		if(beansWithAnnotation != null && beansWithAnnotation.size() > 0) {
			for (Object element : beansWithAnnotation.values()) {
				return element;
			}
		}
		return null;
	}
}

