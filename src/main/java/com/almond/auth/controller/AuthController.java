package com.almond.auth.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.auth.service.AuthService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import com.almond.user.domain.User;
import com.almond.user.service.UserService;
import com.almond.util.UtilService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	@Autowired
	UserService userService;
	@Autowired
	UtilService utilService;
	
    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> article(
    		@RequestBody User user) throws Exception {
    	
    	CommonResponse res = new CommonResponse();

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
    
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
    		String accessToken = utilService.createToken(user);
    		user.setAccessToken(accessToken);
    		userService.updateAccessToken(userInfo);
	   		
	   		res.setResult(ResponseResult.OK);
	   		res.setMessage("로그인에 성공했습니다.");
	   		HashMap<String, String> data = new HashMap<String, String>();
	   		data.put("accessToken", accessToken);
	   		res.setData(data);
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    	}else{
	   		res.setResult(ResponseResult.ERROR);
	   		res.setMessage("ID 또는 password가 틀렸습니다.");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.UNAUTHORIZED);
    	}
    }
    
    /**
     * Auth Check
     * 
     * @param request
     * @return ResponseEntity<CommonResponse>
     */
    @RequestMapping(value="/check", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> authCheck(
    		HttpServletRequest request) throws Exception {

		CommonResponse res = new CommonResponse();

    	String accessToken = request.getHeader("X-authorization");
    	
    	DecodedJWT jwt = null;
    	
        try {
	    	jwt = authService.authCheck(accessToken);
	    	
	        System.out.println("getIssuer : " + jwt.getIssuer());
	        System.out.println("getSubject : " + jwt.getSubject());
	        System.out.println("getExpiresAt : " + jwt.getExpiresAt());

    		res.setResult(ResponseResult.OK);
       		return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
        } catch(JWTDecodeException exception) {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("Invalid signature/claims");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.UNAUTHORIZED);
        } catch(JWTVerificationException exception) {
    		res.setResult(ResponseResult.ERROR);
        	res.setMessage("Invalid token");
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.UNAUTHORIZED);
        }
    }
}
