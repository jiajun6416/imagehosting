package com.jiajun.imagehosting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jiajun.common.base.dao.BaseDao;
import com.jiajun.imagehosting.domain.UserEntity;
import com.jiajun.imagehosting.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BaseDao dao;
	
	private static final String USER_NAME_SPACE = "userEntityMapper.";
	
	@Override
	public UserEntity getByUsernameAndPw(String username, String password) throws Exception {
		Assert.notNull(username);
		Assert.notNull(password);
		Map<String, String> params = new HashMap<>();
		params.put("username", username);
		params.put("password", password);
		return (UserEntity) dao.selectObject(USER_NAME_SPACE+"selectByUsernameAndPw", params);
	}

	@Override
	public void updateLoginInfo(Integer userId, String loginIp) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);
		params.put("loginTime", new Date());
		params.put("loginIp", loginIp);
		dao.update(USER_NAME_SPACE+"updateLoginInfo", params);
	}


}
