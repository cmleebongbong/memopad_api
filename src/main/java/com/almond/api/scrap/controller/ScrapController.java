package com.almond.api.scrap.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.almond.annotations.CheckAuth;
import com.almond.api.auth.service.AuthService;
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
	
	@Autowired
	private AuthService authService;
	
    /**
     * 스크랩 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> selectScrapList(
    		@RequestHeader(value="Authorization", required=false) String authorization,
    		@RequestParam(required=false) String nickname,
    		@RequestParam(required=false) String nationCode,
    		@RequestParam(required=false, value = "city[]") int[] city,
    		@RequestParam(required=false, value = "category[]") int[] category,
    		@RequestParam(required=false, defaultValue="10") int limit,
    		@RequestParam(required=false, defaultValue="1") int page) throws Exception {
    	
    	int userIdx = authService.getUserIdxByToken(authorization);
    	int total = scrapService.getScrapListTotalCount(nationCode, city, category, nickname);
    	
    	ArrayList<Scrap> scrapList = scrapService.getScrapList(nationCode, city, category, limit, page, userIdx, nickname);
    	
    	CommonResponse res = new CommonResponse();
    	HashMap<String, Object> responseData = new HashMap<String, Object>();
    	responseData.put("total", total);
    	responseData.put("totalPage", Math.ceil((double)total / (double)limit));
    	responseData.put("limit", limit);
    	responseData.put("page", page);
    	responseData.put("list", scrapList);
    	res.setResult(ResponseResult.OK);
    	res.setData(responseData);

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
    public ResponseEntity<CommonResponse> insertScrap(
    		HttpServletRequest request,
    		@RequestHeader(value="Authorization") String authorization,
    		@RequestBody Scrap scrap) throws Exception {
    	
    	CommonResponse res = new CommonResponse();
    	
    	// intercepter 에서 token 을 식별해 주입해준 id, idx를 이용
    	scrap.setWriter(request.getAttribute("idx").toString());
    	int result = scrapService.registerScrap(scrap);
    	if (result > 0) {
        	res.setResult(ResponseResult.OK);
        	res.setMessage("스크랩 되었습니다.");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("문제가 발생했습니다.");
    	}
    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }

    /**
     * 스크랩 삭제
     * 
     * @param scrapIdx
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @CheckAuth
    @RequestMapping(value="/{scrapIdx}", method=RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> deleteScrap(
    		HttpServletRequest request,
    		@RequestHeader(value="Authorization") String authorization,
    		@PathVariable int scrapIdx) throws Exception {
    	CommonResponse res = new CommonResponse();

    	int userIdx = Integer.parseInt(request.getAttribute("idx").toString());
    	int result = scrapService.deleteScrap(userIdx, scrapIdx);
    	if (result > 0) {
        	res.setResult(ResponseResult.OK);
        	res.setMessage("스크랩이 삭제 되었습니다.");
        	res.setData(scrapIdx);
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("문제가 발생했습니다.");
    	}
    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }

    /**
     * 스크랩 좋아요
     * 
     * @param scrapIdx
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @CheckAuth
    @RequestMapping(value="/like/{scrapIdx}", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> activeScrapLike(
    		HttpServletRequest request,
    		@RequestHeader(value="Authorization") String authorization,
    		@PathVariable int scrapIdx) throws Exception {
    	
    	int userIdx = Integer.parseInt(request.getAttribute("idx").toString());
    	CommonResponse res = new CommonResponse();
    	
    	int scrapResult = scrapService.getScrapLike(userIdx, scrapIdx);
    	int result = 0;
    	
    	if (scrapResult > 0) {
    		result = scrapService.activeScrapLike(userIdx, scrapIdx);
    	} else {
        	result = scrapService.insertScrapLike(userIdx, scrapIdx);
    	}
    	
    	if (result > 0) {
    		HashMap<String, Object> data = new HashMap<String, Object>();
    		data.put("likeCount", scrapService.getScrapLikeCount(scrapIdx));
        	res.setResult(ResponseResult.OK);
        	res.setData(data);
        	res.setMessage("좋아요 완료");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("좋아요 실패");
    	}
    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }

    /**
     * 스크랩 좋아요 취소
     * 
     * @param scrapIdx
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @CheckAuth
    @RequestMapping(value="/like/{scrapIdx}", method=RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> cancelScrapLike(
    		HttpServletRequest request,
    		@RequestHeader(value="Authorization") String authorization,
    		@PathVariable int scrapIdx) throws Exception {
    	
    	int userIdx = Integer.parseInt(request.getAttribute("idx").toString());
    	CommonResponse res = new CommonResponse();

    	int result = scrapService.deleteScrapLike(userIdx, scrapIdx);
    	
    	if (result > 0) {
    		HashMap<String, Object> data = new HashMap<String, Object>();
    		data.put("likeCount", scrapService.getScrapLikeCount(scrapIdx));
        	res.setResult(ResponseResult.OK);
        	res.setData(data);
        	res.setMessage("좋아요 취소 완료");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("좋아요 취소 실패");
    	}
    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
}
