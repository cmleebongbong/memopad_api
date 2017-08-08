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
    	User userInfo = userMapper.selectUserById(id);
    	return userInfo;
    }
    
    /**
     * @param user
     * @throws Exception
     */
    public void updateLoginKey(User user) throws Exception {
    	userMapper.updateLoginKey(user);
    }
}
