package com.almond.api.auth.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.annotations.CheckAuth;
import com.almond.api.auth.service.AuthService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import com.almond.api.user.domain.User;
import com.almond.api.user.service.UserService;
import com.almond.util.UtilService;

import io.swagger.annotations.Api;

@Api(tags = "Auth")
@RestController
@RequestMapping(value="/api/auth")
public class AuthController {
	private AuthService authService;
	private UserService userService;
	private UtilService utilService;

	@Autowired
	public AuthController(AuthService authService, UserService userService, UtilService utilService) {
		this.authService = authService;
		this.userService = userService;
		this.utilService = utilService;
	}

	/**
	 * Auth Check
	 *
	 * @param authorization token
	 * @return result, idx, id, nickname, totalScrap
	 */
	@CheckAuth
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> auth(
    	   @RequestHeader(value="Authorization") String authorization) throws Exception {
    	
    	CommonResponse res = new CommonResponse();

    	int idx = authService.getUserIdxByToken(authorization);
    	User user = userService.selectUserByIdx(idx);
    	
    	HashMap<String, Object> data = new HashMap<>();
    	data.put("idx", user.getIdx());
    	data.put("id", user.getId());
    	data.put("nickname", user.getNickname());
    	data.put("totalScrap", user.getTotalScrap());

		res.setResult(ResponseResult.OK);
		res.setData(data);
    	return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 로그인
     *
     * @param user 로그인 정보
     * @return result, message, userInfo
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> login(
    		@RequestBody User user) throws Exception {
    	
    	CommonResponse res = new CommonResponse();
    	
    	if(user.getId() == null || user.getPassword() == null) {
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("ID 또는 PASSWORD 를 입력해주세요.");
        	return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    	}
    	
    	User userInfo = userService.selectUserById(user.getId());
    	String passwordEncrypt = utilService.encryptSHA256(user.getPassword());
    	
    	// 로그인 성공
    	if(userInfo != null && userInfo.getPassword().equals(passwordEncrypt)) {
    		String token = authService.createToken(userInfo);
    		
    		userInfo.setToken(token);
    		userInfo.setPassword("");
    		userService.updateAccessToken(userInfo);
	   		
	   		res.setResult(ResponseResult.OK);
	   		res.setMessage("로그인에 성공했습니다.");
	   		res.setData(userInfo);
        	return new ResponseEntity<>(res, HttpStatus.OK);
    	}else{
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("ID 또는 PASSWORD 가 틀렸습니다.");
        	return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    	}
    }
}
