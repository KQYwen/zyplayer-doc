package com.zyplayer.doc.swagger.framework.configuration;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * context工具类
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	public static ApplicationContext context;
	private static EnableSwaggerMgUi ENABLE_SWAGGER_MG_UI;

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
	
	/**
	 * 获取EnableSwaggerMgUi
	 * @since 2019/1/29 12:58
	 * @return EnableSwaggerMgUi注解对象
	**/
	public static EnableSwaggerMgUi getEnableSwaggerMgUi() {
		if (ENABLE_SWAGGER_MG_UI != null) {
			return ENABLE_SWAGGER_MG_UI;
		}
		Object annotation = SpringContextUtil.getBeanWithAnnotation(EnableSwaggerMgUi.class);
		if (annotation != null) {
			EnableSwaggerMgUi swaggerMgUi = annotation.getClass().getAnnotation(EnableSwaggerMgUi.class);
			if (swaggerMgUi == null) {
				// 直接通过superclass去找
				Class<?> superclass = annotation.getClass().getSuperclass();
				if (superclass != null) {
					swaggerMgUi = superclass.getAnnotation(EnableSwaggerMgUi.class);
				}
			}
			if (swaggerMgUi == null) {
				// 再通过AopUtils去找
				Class<?> targetClass = AopUtils.getTargetClass(annotation);
				if (targetClass != null) {
					swaggerMgUi = targetClass.getAnnotation(EnableSwaggerMgUi.class);
				}
			}
			if (swaggerMgUi != null) {
				ENABLE_SWAGGER_MG_UI = swaggerMgUi;
			}
			return swaggerMgUi;
		}
		return null;
	}
}

