package com.almond.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import com.almond.api.user.domain.User;
import com.almond.api.user.service.UserService;
import com.almond.util.UtilService;

import io.swagger.annotations.Api;

@Api(tags = "User")
@RestController
@RequestMapping(value="/api/user")
public class UserController {
    private UserService userService;
    private UtilService utilService;

    @Autowired
    public UserController(UserService userService, UtilService utilService) {
        this.userService = userService;
        this.utilService = utilService;
    }

    /**
     * 회원가입
     *
     * @param user 가입정보
     * @return result, message
     */
    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> signUp(
    		@RequestBody User user) throws Exception {
    	
   		CommonResponse res = new CommonResponse();
   	
    	User idResult = userService.selectUserById(user.getId());
    	User nicknameResult = userService.selectUserByNickname(user.getNickname());
	   	
	   	if (idResult != null) {
	   		// ID 중복
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("이미 사용중인 ID 입니다.");
        	return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	   	} else if (nicknameResult != null) {
	   		// Nickname 중복
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("이미 사용중인 닉네임입니다.");
        	return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	   	} else {
	   		// 암호화
	   		user.setPassword(utilService.encryptSHA256(user.getPassword()));
	   		int result = userService.signUp(user);
	   		if (result > 0) {
			    res.setResult(ResponseResult.OK);
			    res.setMessage("가입이 완료되었습니다.");
		    } else {
	   			res.setResult(ResponseResult.ERROR);
	   			res.setMessage("가입이 실패했습니다.");
		    }
	   		return new ResponseEntity<>(res, HttpStatus.CREATED);
	   	}
    }
}