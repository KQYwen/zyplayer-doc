package com.zyplayer.doc.manage.framework.exception;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.DocResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author 暮光：城中城
 * @since 2018年12月8日
 */
@Component
public class GlobalHandlerExceptionResolver extends SimpleMappingExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		DocResponseJson<Object> responseJson;
		// 无需处理的异常
		if (ex instanceof HttpMediaTypeNotAcceptableException) {
			responseJson = DocResponseJson.warn("系统错误");
		} else {
			// 其他异常
			logger.error("---自定义异常处理---", ex);
			if (ex instanceof ConfirmException) {
				responseJson = DocResponseJson.warn(ex.getMessage());
			} else {
				responseJson = DocResponseJson.warn("系统错误");
			}
		}
		responseJson.send(response);
		return new ModelAndView();
	}
}

