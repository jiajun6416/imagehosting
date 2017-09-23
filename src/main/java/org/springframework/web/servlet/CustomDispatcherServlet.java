package org.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义 springmvc控制器
 * Created by jiajun on 2017/09/23 18:47
 */
public class CustomDispatcherServlet extends org.springframework.web.servlet.DispatcherServlet{
	private static final long serialVersionUID = 2709080075381025530L;
	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (pageNotFoundLogger.isWarnEnabled()) {
			pageNotFoundLogger.warn("No mapping found for HTTP request with URI [" + request.getRequestURI() +
					"] in DispatcherServlet with name '" + getServletName() + "'");
		}
		//跳转首页
		request.getRequestDispatcher("/").forward(request, response);
	}
}	
