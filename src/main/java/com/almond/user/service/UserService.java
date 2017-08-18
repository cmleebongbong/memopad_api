package com.almond.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.user.dao.UserMapper;
import com.almond.user.domain.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
	
    /**
     * @param id
     * @return User
     * @throws Exception
     */
    public User selectUserById(String id) throws Exception {
    	User user = userMapper.selectUserById(id);
    	return user;
    }
    
    /**
     * @param key
     * @return
     * @throws Exception
     */
    public User selectUserByKey(String key) throws Exception {
    	User user = userMapper.selectUserByKey(key);
    	return user;
    }
    
    /**
     * @param user
     * @throws Exception
     */
    public void signup(User user) throws Exception {
    	userMapper.signup(user);
    }
    
    /**
     * @param user
     * @throws Exception
     */
    public void updateLoginKey(User user) throws Exception {
    	userMapper.updateLoginKey(user);
    }
}
