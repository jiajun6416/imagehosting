package com.jiajun.imagehosting.web.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jiajun.common.util.HttpUtils;
import com.jiajun.imagehosting.config.Constant;
import com.jiajun.imagehosting.domain.UserEntity;
import com.jiajun.imagehosting.service.UserService;

public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserEntity user = (UserEntity) session.getAttribute(Constant.SESSION_USER_KEY);
		if(user == null) {
			String username = null;
			String password = null;
			Cookie[] cookies = request.getCookies();
			Map<String, String> cookieAlbumId = new HashMap<>();
			for (Cookie cookie : cookies) {
				if("username".equals(cookie.getName())) {
					username = cookie.getValue();
				}
				if("password".equals(cookie.getName())) {
					password = cookie.getValue();
				}
				if(cookie.getName().startsWith("selected_album_")) {
					cookieAlbumId.put(cookie.getName(), cookie.getValue());
				}
			}
			if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
				user = userService.getByUsernameAndPw(username, password);
				if(user != null) {
					session.setAttribute(Constant.SESSION_USER_KEY, user);
					if(StringUtils.isNotEmpty(cookieAlbumId.get("selected_album_"+user.getUsername()))) {
						session.setAttribute(Constant.SESSION_ALBUM_SELECTED, Integer.valueOf(cookieAlbumId.get("selected_album_"+user.getUsername())));
					}
				}
				return true;
			}
			toLogin(request, response);
			return false;
		}
		return true;
	}

	private void toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getMethod();
		String callback = HttpUtils.getBasePath(request);
		if("get".equalsIgnoreCase(method)) {
			//get请求添加路径
			StringBuffer url = request.getRequestURL();
			callback = url.toString();
		}
		response.sendRedirect(request.getContextPath()+"/login?callback="+callback);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
