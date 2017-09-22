package com.almond.api.user.dao;

import com.almond.api.user.domain.User;

public interface UserMapper {
	public User selectUserById(String id) throws Exception;
	public User selectUserByToken(String token) throws Exception;
	public User signin(User user) throws Exception;
	public int signup(User user) throws Exception;
	public int updateAccessToken(User user) throws Exception;
}
