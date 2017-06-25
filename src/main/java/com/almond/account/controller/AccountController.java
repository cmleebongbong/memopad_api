package com.almond.account.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.account.dao.AccountMapper;
import com.almond.account.domain.Account;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/account")
public class AccountController {
	
    @Autowired
    private AccountMapper accountMapper;
     
    @RequestMapping(value="/signin", method=RequestMethod.POST)
    public HashMap<String, Object> signin(
    		@RequestBody Account account) throws Exception {
    	
    	Account signinResult = accountMapper.signin(account);
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	if(signinResult != null && signinResult.getPassword().equals(account.getPassword())) {
        	map.put("result", "ok");
    	}else{
        	map.put("result", "error");
        	map.put("message", "Incorrect username or password");
    	}
    	
    	return map;
    }
    
   @RequestMapping(value="/signup", method=RequestMethod.POST)
   public HashMap<String, Object> signup(
   		@RequestBody Account account) throws Exception {
   	
   	int signupResult = accountMapper.signup(account);
   	HashMap<String, Object> map = new HashMap<String, Object>();

   	if(signupResult > 0) {
   		map.put("result", "ok");
   	}else{
    	map.put("result", "error");
    	map.put("message", "Incorrect username or password");
   	}
   	
   	return map;
   }
}