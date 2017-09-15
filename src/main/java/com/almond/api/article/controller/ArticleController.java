package com.almond.api.article.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.user.domain.User;
import com.almond.common.domain.CommonResponse;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/article")
public class ArticleController {

    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> article(
    		@RequestBody User user) throws Exception {
    	
    	CommonResponse res = new CommonResponse();

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
}
