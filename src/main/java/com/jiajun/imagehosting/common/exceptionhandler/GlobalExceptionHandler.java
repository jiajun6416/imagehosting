package com.jiajun.imagehosting.common.exceptionhandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.jiajun.imagehosting.common.bo.Result;
import com.jiajun.imagehosting.common.util.JsonUtils;


/**
 * 全局处理器 
 * @author tvu
 */
public class GlobalExceptionHandler extends SimpleMappingExceptionResolver{
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if(logger.isErrorEnabled()) {
			logger.error(ex.getMessage(), ex);
		}
		if(isAjaxRequest(request)) {
			//ajax
			Result result = Result.error(ex.getMessage());
			write(response, JsonUtils.toString(result));
			return null;
		} else {
			//http
			return new ModelAndView().addObject("message", ex.getMessage());
		}
	}

	
	private boolean isAjaxRequest(HttpServletRequest request) {
		boolean mine = request.getHeader("accept").contains("application/json");
		String header = request.getHeader("X-Requested-With");
		if(mine || StringUtils.isNoneEmpty(header) && header.contains("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}
	
	private void write(HttpServletResponse response, String msg) {
		PrintWriter write;
		try {
			write = response.getWriter();
			write.write(msg);
			write.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
