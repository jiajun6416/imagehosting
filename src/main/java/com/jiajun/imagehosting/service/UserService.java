package com.jiajun.imagehosting.service;

import com.jiajun.imagehosting.domain.UserEntity;

public interface UserService {
	UserEntity getByUsernameAndPw(String username, String password) throws Exception;
	void updateLoginInfo(Integer userId, String loginIp) throws Exception;
}
