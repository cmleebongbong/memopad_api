package com.almond.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.common.domain.CommonResponse;
import com.almond.user.dao.UserMapper;
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
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value="/signin", method=RequestMethod.POST)
    public ResponseEntity<Object> signin(
    		@RequestBody User user) throws Exception {
    	
    	User userInfo = userService.selectUserById(user.getId());
    	String passwordEncrypt = utilService.encryptSHA256(user.getPassword());
    	
    	if(userInfo != null && userInfo.getPassword().equals(passwordEncrypt)) {
    		String key = utilService.createRandomKey();
    		user.setLoginKey(key);
    		userService.updateLoginKey(user);
	   		
	   		CommonResponse res = new CommonResponse();
	   		res.setResult("ok");
	   		res.setData(key);
	   		
        	return new ResponseEntity<Object>(res, HttpStatus.OK);
    	}else{
    		
	   		CommonResponse res = new CommonResponse();
	   		res.setResult("error");
	   		res.setMessage("ID 또는 password가 틀렸습니다.");
        	return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    	}
    }
    
    
    /**
     * Sign	up
     * 
     * @param user
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public ResponseEntity<Object> signup(
   		@RequestBody User user) throws Exception {
   	
    	User result = userMapper.selectUserById(user.getId());
	   	
	   	if(result != null) {
	   		// ID 중복
	   		return new ResponseEntity<Object>(HttpStatus.CONFLICT);
	   	}else{
	   		// 암호화
	   		user.setPassword(utilService.encryptSHA256(user.getPassword()));
	   		userMapper.signup(user);
	   		Map<String, String> data = new HashMap<String, String>();
	   		data.put("result", "ok");
	   		return new ResponseEntity<Object>(data, HttpStatus.CREATED);
	   	}
    }
    
    
    /**
     * Auth Info
     * 
     * @param account
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value="/auth", method=RequestMethod.GET)
    public ResponseEntity<Object> authInfo(
   		HttpServletRequest request) throws Exception {

    	String key = request.getHeader("X-authorization");
    	User result = userMapper.selectUserByKey(key);
    	
    	if(result == null) {
        	return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    	}else{
	   		Map<String, Object> data = new HashMap<String, Object>();
	   		data.put("auth", result);
       		return new ResponseEntity<Object>(data, HttpStatus.OK);
    	}
    }
}