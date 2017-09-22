package com.almond.api.auth.controller;

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

import com.almond.annotations.CheckAuth;
import com.almond.api.auth.service.AuthService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import com.almond.api.user.domain.User;
import com.almond.api.user.service.UserService;
import com.almond.util.UtilService;

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
 
    /**
     * Auth Check
     * 
     * @param user
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @CheckAuth(value=true)
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> auth(
    		HttpServletRequest request) throws Exception {
    	
    	CommonResponse res = new CommonResponse();
    	
    	String token = request.getHeader("X-Authorization");
    	User user = userService.selectUserByToken(token);
    	
    	if (user == null) {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("유효하지 않은 토큰입니다.");
    		return new ResponseEntity<CommonResponse>(res, HttpStatus.UNAUTHORIZED);
    	} else {
    		res.setResult(ResponseResult.OK);
    		res.setData(user);
        	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    	}
    }
    
    /**
     * Sign in
     * 
     * @param user
     * @return ResponseEntity<CommonResponse>
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
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
    		String token = authService.createToken(userInfo);
    		userInfo.setToken(token);
    		userService.updateAccessToken(userInfo);
	   		
	   		res.setResult(ResponseResult.OK);
	   		res.setMessage("로그인에 성공했습니다.");
	   		HashMap<String, String> data = new HashMap<String, String>();
	   		data.put("token", token);
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
    /*@RequestMapping(value="/check", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> authCheck(
    		HttpServletRequest request) throws Exception {

		CommonResponse res = new CommonResponse();

    	String accessToken = request.getHeader("X-Authorization");
    	
        try {
        	DecodedJWT jwt = authService.tokenCheck(accessToken);
	    	
	        System.out.println("getIssuer : " + jwt.getIssuer());
	        System.out.println("getSubject : " + jwt.getSubject());
	        System.out.println("getExpiresAt : " + jwt.getExpiresAt());

    		res.setResult(ResponseResult.OK);
    		res.setData(jwt);
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
    }*/
}
