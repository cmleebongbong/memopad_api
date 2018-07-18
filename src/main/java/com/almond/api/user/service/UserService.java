package com.almond.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.user.dao.UserMapper;
import com.almond.api.user.domain.User;

@Service
public class UserService {
    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 유저 조회 by idx
     */
    public User selectUserByIdx(int idx) throws Exception {
        return userMapper.selectUserByIdx(idx);
    }
	
    /**
     * 유저 조회 by id
     */
    public User selectUserById(String id) throws Exception {
    	return userMapper.selectUserById(id);
    }
	
    /**
     * 유저 조회 by nickname
     */
    public User selectUserByNickname(String nickname) throws Exception {
    	return userMapper.selectUserByNickname(nickname);
    }
    
    /**
     * 유저 조회 by token
     */
    public User selectUserByToken(String token) throws Exception {
    	return userMapper.selectUserByToken(token);
    }
    
    /**
     * 회원 가입
     */
    public int signUp(User user) throws Exception {
    	return userMapper.signUp(user);
    }
    
    /**
     * 토큰 갱신
     */
    public void updateAccessToken(User user) throws Exception {
    	userMapper.updateAccessToken(user);
    }
}
