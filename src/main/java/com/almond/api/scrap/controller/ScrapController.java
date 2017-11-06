package com.almond.api.scrap.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.almond.annotations.CheckAuth;
import com.almond.api.scrap.domain.Scrap;
import com.almond.api.scrap.service.ScrapService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;

import io.swagger.annotations.Api;

@SpringBootApplication
@Api(tags = "Scrap")
@RestController
@RequestMapping(value="/api/scrap")
public class ScrapController {
	
	@Autowired
	private ScrapService scrapService;
	
    /**
     * 스크랩 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> scrapList(
    		@RequestHeader(value="Authorization", required=false) String authorization,
    		@RequestParam(required=false) String nationCode,
    		@RequestParam(required=false) String[] cityIdx,
    		@RequestParam(required=false) String[] categoryIdx) throws Exception {
    	
    	System.out.println("nation code : " + nationCode);
    	System.out.println("city idx : " + cityIdx);
    	System.out.println("nation idx : " + categoryIdx);
    	
    	ArrayList<Scrap> scrapList = scrapService.scrapList(nationCode, cityIdx, categoryIdx);
    	CommonResponse res = new CommonResponse();
    	res.setResult(ResponseResult.OK);
    	res.setMessage("조회 되었습니다.");
    	res.setData(scrapList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }

    /**
     * 스크랩 등록
     * 
     * @param Scrap
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @CheckAuth
    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> scrapRegister(
    		HttpServletRequest request,
    		@RequestHeader(value="Authorization") String authorization,
    		@RequestBody Scrap scrap) throws Exception {
    	
    	CommonResponse res = new CommonResponse();
    	scrap.setWriter(request.getAttribute("idx").toString());
    	int result = scrapService.scrapRegister(scrap);
    	if (result > 0) {
        	res.setResult(ResponseResult.OK);
        	res.setMessage("스크랩 되었습니다.");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("문제가 발생했습니다.");
    	}
    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
}
