package com.jiajun.imagehosting.common.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jiajun.imagehosting.common.config.Constant;
import com.jiajun.imagehosting.image.domain.AlbumEntity;
import com.jiajun.imagehosting.image.domain.UserEntity;


public class BaseController {
	
	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	private final boolean isMock = true;
	
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
			return  (UserEntity) session.getAttribute(Constant.LOGIN_USER);
		}
	}
	
	public AlbumEntity getSelectedAlbum(HttpSession session) {
		if(isMock) {
			AlbumEntity album = new AlbumEntity();
			album.setId(10010);
			album.setIsPublic(true);
			album.setName("markdon");
			album.setUserId(10086);
			return album;
		}  else {
			return (AlbumEntity) session.getAttribute(Constant.SELECTED_ALBUM);
		}
	}
	
	
	
}
