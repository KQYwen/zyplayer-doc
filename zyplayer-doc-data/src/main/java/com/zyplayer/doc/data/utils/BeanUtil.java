package com.zyplayer.doc.data.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class BeanUtil {
	
	@SuppressWarnings({"unchecked"})
	public static <T extends Annotation> T getAnnotation(JoinPoint pjp, Class<T> t) throws Exception {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		T annotation = method.getAnnotation(t);// 方法上定义的
		if (annotation == null) {
			annotation = (T) pjp.getSignature().getDeclaringType().getAnnotation(t);// 类上定义的
			if (annotation == null) {
				Object target = pjp.getTarget();
				annotation = target.getClass().getAnnotation(t);// 实现类上定义的
				if (annotation == null) {
					Signature sig = pjp.getSignature();
					if (sig instanceof MethodSignature) {
						MethodSignature msig = (MethodSignature) sig;
						Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
						annotation = currentMethod.getAnnotation(t);// 实现类的方法上定义的
					}
				}
			}
		}
		return annotation;
	}
}
