package com.zyplayer.doc.manage.framework.interceptor;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * 所有bean初始化后执行，bean初始化了，再初始化一些自己的东西
 */
@Component
public class InitServletContextAware implements InitializingBean,ServletContextAware {

	private ServletContext context;
	
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ctx();
	}
	
	private void ctx(){
		String ctx = context.getContextPath();
		if (ctx != null && ctx.trim().length() > 0 && !ctx.trim().equals("/")) {
			context.setAttribute("ctx", ctx+"/");
			context.setAttribute("statics", ctx+"/statics/");
		} else {
			context.setAttribute("ctx", "/");
			context.setAttribute("statics", "/statics/");
		}
	}

}
