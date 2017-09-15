package com.almond.og.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import com.almond.og.domain.Og;
import com.almond.og.service.OgService;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/og")
public class OgController {

	@Autowired
	OgService ogService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> article(
    		@RequestParam String url) throws Exception {
    	
    	Og ogData = null;
    	CommonResponse res = new CommonResponse();
    	
    	try {
        	ogData = ogService.getOg(url);
        	res.setResult(ResponseResult.OK);
        	res.setData(ogData);
    	} catch(HttpClientErrorException e) {
        	res.setResult(ResponseResult.ERROR);
        	res.setMessage("연결할수 없는 URL 입니다.");
    	}
    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
}
