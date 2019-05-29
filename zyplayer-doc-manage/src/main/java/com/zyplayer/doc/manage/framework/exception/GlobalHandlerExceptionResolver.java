package com.zyplayer.doc.manage.framework.exception;

import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理器
 */
@Component
public class GlobalHandlerExceptionResolver extends SimpleMappingExceptionResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		LOGGER.error("---自定义异常处理---", ex);
		request.setAttribute("throwable", ex);
		ModelAndView mv = new ModelAndView();
		response.setStatus(HttpStatus.OK.value());// 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);// 设置ContentType
		response.setCharacterEncoding("UTF-8");// 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		DocResponseJson<Object> responseJson = null;
		if (ex instanceof ConfirmException) {// 提示性异常
			responseJson = DocResponseJson.warn(ex.getMessage());
		} else {// 其他异常
			responseJson = DocResponseJson.warn("系统错误");
		}
		boolean isResponseBody = isResponseBody(handler);// 是否返回body
		// 返回页面或者返回内容处理
		if (!isResponseBody) {
			mv.addObject("errJson", responseJson);
			String customErrPage = (String) request.getAttribute("customErrPage");
			// 有定义过错误页面的直接返回自定义的错误页面
			if(StringUtils.isNotBlank(customErrPage)) {
				mv.setViewName(customErrPage);
			} else {// 否则返回默认的错误页面
				mv.setViewName("/statics/common/500.html");
			}
		} else {
			try {
				String jsonStr = JSON.toJSONString(responseJson);
				response.getWriter().write(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mv;
	}

	/**
	 * 如果是HttpMessageNotReadableException 则获取错误字段
	 * @param exception
	 * @return
	 */
	public static String getExceptionField(String exception) {
		Pattern pattern = Pattern.compile("Unrecognized field \"(\\w*)[$\"]");
		Matcher matcher = pattern.matcher(exception);
		String field = "";
		if (matcher.find()) {
			field = matcher.group(1);
		} else {
			pattern = Pattern.compile("Field error in object '.+' on field '(\\w+)'");
			matcher = pattern.matcher(exception);
			if (matcher.find()) {
				field = matcher.group(1);
			}
		}
		return field;
	}
	
	/**
	 * 是否返回body
	 * @author 
	 * @since 2017年5月11日
	 * @param handler
	 * @return
	 */
	private boolean isResponseBody(Object handler){
		if (handler instanceof HandlerMethod) {
			HandlerMethod mathod = (HandlerMethod) handler;
			ResponseBody body = mathod.getMethodAnnotation(ResponseBody.class);
			if(body == null){
				RestController restController = mathod.getMethod().getDeclaringClass().getAnnotation(RestController.class);
				if(restController == null){
					if(!mathod.getMethod().getReturnType().isAssignableFrom(ResponseJson.class)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
}

