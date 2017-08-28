package com.almond.user.controller;

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
import com.almond.user.domain.User;
import com.almond.user.service.UserService;
import com.almond.util.UtilService;

@SpringBootApplication
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
     */
    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> signup(
    		@RequestBody User user) throws Exception {
    	
   		CommonResponse res = new CommonResponse();
   	
    	User result = userService.selectUserById(user.getId());
	   	
	   	if(result != null) {
	   		// ID 중복
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("이미 사용중인 ID입니다.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.CONFLICT);
	   	}else{
	   		// 암호화
	   		user.setPassword(utilService.encryptSHA256(user.getPassword()));
	   		userService.signup(user);
	   		res.setResult(ResponseResult.OK);
	   		res.setMessage("가입이 완료되었습니다. 로그인 해주세요.");
	   		return new ResponseEntity<CommonResponse>(res, HttpStatus.CREATED);
	   	}
    }
}