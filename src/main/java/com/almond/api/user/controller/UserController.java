package com.almond.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

@SpringBootApplication
@Api(tags = "User")
@RestController
@RequestMapping(value="/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UtilService utilService;
    
    
    /**
     * Sign	up
     * 
     * @param user
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> signup(
    		@RequestBody User user) throws Exception {
    	
   		CommonResponse res = new CommonResponse();
   	
    	User idResult = userService.selectUserById(user.getId());
    	User nicknameResult = userService.selectUserByNickname(user.getNickname());
	   	
	   	if (idResult != null) {
	   		// ID 중복
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("이미 사용중인 ID입니다.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.CONFLICT);
	   	} else if (nicknameResult != null) {
	   		// Nickname 중복
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("이미 사용중인 닉네임입니다.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.CONFLICT);
	   	} else {
	   		// 암호화
	   		user.setPassword(utilService.encryptSHA256(user.getPassword()));
	   		userService.signup(user);
	   		res.setResult(ResponseResult.OK);
	   		res.setMessage("가입이 완료되었습니다. 로그인 해주세요.");
	   		return new ResponseEntity<CommonResponse>(res, HttpStatus.CREATED);
	   	}
    }
    
    /**
     * User Info
     * 
     * @param id
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> userInfo(
    		@PathVariable String id) throws Exception {
    	
   		CommonResponse res = new CommonResponse();
   	
    	User user = userService.selectUserById(id);
	   	
	   	if(user != null) {
	   		res.setResult(ResponseResult.OK);
	   		res.setData(user);
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
	   	}else{
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("사용자 정보를 조회할수 없습니다.");
	   		return new ResponseEntity<CommonResponse>(res, HttpStatus.BAD_REQUEST);
	   	}
    }
}