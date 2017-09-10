package com.jiajun.imagehosting.web.controller;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiajun.common.base.controller.BaseController;
import com.jiajun.common.bo.Result;
import com.jiajun.imagehosting.config.Constant;
import com.jiajun.imagehosting.domain.UserEntity;
import com.jiajun.imagehosting.service.UserService;

@Controller
public class SystemController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("doLogin")
	@ResponseBody
	public Result login(String username, String password, boolean rememberme, 
			HttpServletRequest request, HttpSession session) throws Exception {
		UserEntity user = userService.getByUsernameAndPw(username, password);
		if(user != null) {
			userService.updateLoginInfo(user.getId(), this.getIP(request));
			user.setPassword("");
			session.setAttribute(Constant.SESSION_USER_KEY, user);
			//存储选中的相册
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("selected_album_"+user.getUsername())) {
					session.setAttribute(Constant.SESSION_ALBUM_SELECTED, Integer.valueOf(cookie.getValue()));
					break;
				}
			}
			return Result.success(null);
		} else {
			return Result.forbidden("username or password error!");
		}
	}
	
	@RequestMapping("login")
	public String toLogin(HttpSession session) throws Exception{
		if(this.getLoginUser(session) != null) {
			return "redirect:/index";
		}
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		//清除cookie
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String name = cookie.getName();
			if(StringUtils.isNotEmpty(name) && name.equals("password")) {
				//设置为0立即删除, 设置为-1表示会话级别
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		//清除session
		session.invalidate();
		return "redirect:login";
	}

}
