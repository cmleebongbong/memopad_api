package com.almond.api.user.dao;

import com.almond.api.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	User selectUserByIdx(int idx) throws Exception;
	User selectUserById(String id) throws Exception;
	User selectUserByNickname(String nickname) throws Exception;
	User selectUserByToken(String token) throws Exception;
	int signUp(User user) throws Exception;
	int updateAccessToken(User user) throws Exception;
}
