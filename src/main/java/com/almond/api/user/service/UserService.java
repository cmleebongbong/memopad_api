package com.almond.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.user.dao.UserMapper;
import com.almond.api.user.domain.User;

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
     * @param nickname
     * @return User
     * @throws Exception
     */
    public User selectUserByNickname(String nickname) throws Exception {
    	User user = userMapper.selectUserByNickname(nickname);
    	return user;
    }
    
    /**
     * @param key
     * @return
     * @throws Exception
     */
    public User selectUserByToken(String token) throws Exception {
    	User user = userMapper.selectUserByToken(token);
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
    public void updateAccessToken(User user) throws Exception {
    	userMapper.updateAccessToken(user);
    }
}
