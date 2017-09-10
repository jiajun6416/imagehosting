package com.jiajun.common.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jiajun.imagehosting.config.Constant;
import com.jiajun.imagehosting.domain.UserEntity;


public class BaseController {
	
	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	private final boolean isMock = false;
	
	/**
	 * 在其他层获得request的方法
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 获得请求来源的ip地址
	 * @param request
	 * @return
	 */
	public String getIP(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		if(ip != null && "0:0:0:0:0:0:0:1".equals(ip)) {
			return "127.0.0.1";
		} else {
			return ip;
		}
	}
	
	/**
	 * 获得登录用户
	 * @param session
	 * @return
	 */
	public UserEntity getLoginUser(HttpSession session) {
		if(isMock) {
			UserEntity user = new UserEntity();
			user.setUsername("jiajun");
			user.setId(10086);
			return user;
		} else {
			return  (UserEntity) session.getAttribute(Constant.SESSION_USER_KEY);
		}
	}
	
	public Integer getSelectedAlbum(HttpSession session) {
		Integer selectAlbumId = (Integer) session.getAttribute(Constant.SESSION_ALBUM_SELECTED);
		return selectAlbumId;
	}
	
	
}
