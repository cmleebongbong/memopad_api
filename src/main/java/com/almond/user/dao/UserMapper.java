package com.almond.user.dao;

import com.almond.user.domain.User;

public interface UserMapper {
	public User selectUserById(String id) throws Exception;
	public User selectUserByKey(String key) throws Exception;
	public User signin(User user) throws Exception;
	public int signup(User user) throws Exception;
	public int updateLoginKey(User user) throws Exception;
}
