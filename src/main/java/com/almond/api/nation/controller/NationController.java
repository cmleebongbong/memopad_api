package com.almond.api.nation.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.nation.domain.Nation;
import com.almond.api.nation.service.NationService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;

import io.swagger.annotations.Api;

@SpringBootApplication
@Api(tags = "Nation")
@RestController
@RequestMapping(value="/api/nation")
public class NationController {
	
	@Autowired
	NationService nationService;

    /**
     * 모든 국가 목록 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> nationList() throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	ArrayList<Nation> nationList = nationService.nationList();
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(nationList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
    
}
