package com.almond.user.controller;

import javax.servlet.http.HttpServletRequest;

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
     * Sign in
     * 
     * @param user
     * @return ResponseEntity<CommonResponse>
     */
    @RequestMapping(value="/signin", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> signin(
    		@RequestBody User user) throws Exception {
    	
    	CommonResponse res = new CommonResponse();
    	
    	if(user.getId() == null || user.getPassword() == null) {
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("ID또는 PASSWORD를 입력해주세요.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.BAD_REQUEST);
    	}
    	
    	User userInfo = userService.selectUserById(user.getId());
    	String passwordEncrypt = utilService.encryptSHA256(user.getPassword());
    	
    	if(userInfo != null && userInfo.getPassword().equals(passwordEncrypt)) {
    		String key = utilService.createRandomKey();
    		user.setLoginKey(key);
    		userService.updateLoginKey(user);
	   		
	   		res.setResult(ResponseResult.OK);
	   		res.setData(key);
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    	}else{
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("ID 또는 password가 틀렸습니다.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.UNAUTHORIZED);
    	}
    }
    
    
    /**
     * Sign	up
     * 
     * @param user
     * @return ResponseEntity<CommonResponse>
     */
    @RequestMapping(value="/signup", method=RequestMethod.POST)
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
    
    
    /**
     * Auth Info
     * 
     * @param request
     * @return ResponseEntity<CommonResponse>
     */
    @RequestMapping(value="/auth", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> authInfo(
    		HttpServletRequest request) throws Exception {

		CommonResponse res = new CommonResponse();

    	String key = request.getHeader("X-authorization");
    	User result = userService.selectUserByKey(key);
    	
    	if(result == null) {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("회원정보를 가져올수 없습니다.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.UNAUTHORIZED);
    	}else{
    		res.setResult(ResponseResult.OK);
       		return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    	}
    }
}