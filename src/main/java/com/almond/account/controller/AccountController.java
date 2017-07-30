package com.almond.account.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.account.dao.AccountMapper;
import com.almond.account.domain.Account;
import com.almond.common.util.UtilService;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/account")
public class AccountController {
	
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UtilService utilService;
     
    /**
     * Sign in
     * 
     * @param account
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value="/signin", method=RequestMethod.POST)
    public ResponseEntity<Object> signin(
    		@RequestBody Account account) throws Exception {
    	
    	Account signinResult = accountMapper.signin(account);
    	String passwordEncrypt = utilService.encryptSHA256(account.getPassword());
    	
    	if(signinResult != null && signinResult.getPassword().equals(passwordEncrypt)) {
    		String key = utilService.createRandomKey();
    		account.setLoginKey(key);
    		accountMapper.updateKey(account);
	   		Map<String, String> data = new HashMap<String, String>();
	   		data.put("result", "ok");
	   		data.put("key", key);
        	return new ResponseEntity<Object>(data, HttpStatus.OK);
    	}else{
        	return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    	}
    }
    
    
    /**
     * Sign	up
     * 
     * @param account
     * @return ResponseEntity<Void>
     */
    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public ResponseEntity<Object> signup(
   		@RequestBody Account account) throws Exception {
   	
    	Account result = accountMapper.selectAccountById(account.getId());
	   	
	   	if(result != null) {
	   		// ID 중복
	   		return new ResponseEntity<Object>(HttpStatus.CONFLICT);
	   	}else{
	   		// 암호화
	   		account.setPassword(utilService.encryptSHA256(account.getPassword()));
	   		accountMapper.signup(account);
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
    	Account result = accountMapper.selectAccountByKey(key);
    	
    	if(result == null) {
        	return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    	}else{
	   		Map<String, Object> data = new HashMap<String, Object>();
	   		data.put("auth", result);
       		return new ResponseEntity<Object>(data, HttpStatus.OK);
    	}
    }
}