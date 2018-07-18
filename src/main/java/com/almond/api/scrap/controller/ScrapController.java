package com.almond.api.scrap.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
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

@Api(tags = "Scrap")
@RestController
@RequestMapping(value="/api/scrap")
public class ScrapController {
	private final ScrapService scrapService;
	private final AuthService authService;

	@Autowired
	public ScrapController(ScrapService scrapService, AuthService authService) {
		this.scrapService = scrapService;
		this.authService = authService;
	}

    /**
     * 스크랩 목록 조회
     *
     * @param authorization 인증정보
     * @param nickname 닉네임
     * @param nationCode 국가코드
     * @param city 도시 idx
     * @param category 카테고리 idx
     * @param limit 조회 갯수
     * @param page 조회 페이지
     * @return result, total, totalPage, limit, page, list
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> getScrapList(
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
    	HashMap<String, Object> responseData = new HashMap<>();
    	responseData.put("total", total);
    	responseData.put("totalPage", Math.ceil((double)total / (double)limit));
    	responseData.put("limit", limit);
    	responseData.put("page", page);
    	responseData.put("list", scrapList);
    	res.setResult(ResponseResult.OK);
    	res.setData(responseData);

    	return new ResponseEntity<>(res, HttpStatus.OK);
    }

	/**
	 * 스크랩 상세 조회
	 *
	 * @param authorization token
	 * @param idx 스크랩 idx
	 * @return result, scrap
	 */
	@RequestMapping(value="/{idx}", method=RequestMethod.GET)
	public ResponseEntity<CommonResponse> getScrap(
			@RequestHeader(value="Authorization", required=false) String authorization,
			@PathVariable int idx) throws Exception {

		int userIdx = authService.getUserIdxByToken(authorization);
		Scrap scrap = scrapService.getScrap(idx, userIdx);

		CommonResponse res = new CommonResponse();
		res.setResult(ResponseResult.OK);
		res.setData(scrap);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

    /**
     * 스크랩 등록
     *
     * @param authorization token
     * @param scrap 스크랩 data
     * @return result, message
     */
    @CheckAuth
    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> insertScrap(
		   @RequestHeader(value="Authorization") String authorization,
           @RequestBody Scrap scrap) throws Exception {
    	
    	CommonResponse res = new CommonResponse();

	    int userIdx = authService.getUserIdxByToken(authorization);
    	scrap.setWriter(Integer.toString(userIdx));
    	int result = scrapService.registerScrap(scrap);
    	if (result > 0) {
        	res.setResult(ResponseResult.OK);
        	res.setMessage("스크랩 되었습니다.");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("문제가 발생했습니다.");
    	}
    	return new ResponseEntity<>(res, HttpStatus.OK);
    }

	/**
	 * 스크랩 삭제
	 *
	 * @param authorization token
	 * @param idx 스크랩 idx
	 * @return result, message, idx
	 */
    @CheckAuth
    @RequestMapping(value="/{idx}", method=RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> deleteScrap(
		   @RequestHeader(value="Authorization") String authorization,
    	   @PathVariable int idx) throws Exception {
    	CommonResponse res = new CommonResponse();

	    int userIdx = authService.getUserIdxByToken(authorization);
    	int result = scrapService.deleteScrap(userIdx, idx);
    	if (result > 0) {
        	res.setResult(ResponseResult.OK);
        	res.setMessage("스크랩이 삭제 되었습니다.");
        	res.setData(idx);
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("문제가 발생했습니다.");
    	}
    	return new ResponseEntity<>(res, HttpStatus.OK);
    }

	/**
	 * 스크랩 좋아요
	 *
	 * @param authorization token
	 * @param idx 스크랩 idx
	 * @return result, message, likeCount
	 */
    @CheckAuth
    @RequestMapping(value="/like/{idx}", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> activeScrapLike(
    		@RequestHeader(value="Authorization") String authorization,
    		@PathVariable int idx) throws Exception {

    	int userIdx = authService.getUserIdxByToken(authorization);
    	CommonResponse res = new CommonResponse();
    	
    	int scrapResult = scrapService.getScrapLike(userIdx, idx);
    	int result;
    	
    	if (scrapResult > 0) {
    		result = scrapService.activeScrapLike(userIdx, idx);
    	} else {
        	result = scrapService.insertScrapLike(userIdx, idx);
    	}
    	
    	if (result > 0) {
    		HashMap<String, Object> data = new HashMap<>();
    		data.put("likeCount", scrapService.getScrapLikeCount(idx));
        	res.setResult(ResponseResult.OK);
        	res.setData(data);
        	res.setMessage("좋아요 완료");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("좋아요 실패");
    	}
    	return new ResponseEntity<>(res, HttpStatus.OK);
    }

	/**
	 * 스크랩 좋아요 취소
	 *
	 * @param authorization token
	 * @param idx 스크랩 idx
	 * @return result, message, likeCount
	 */
    @CheckAuth
    @RequestMapping(value="/like/{idx}", method=RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> cancelScrapLike(
    		@RequestHeader(value="Authorization") String authorization,
    		@PathVariable int idx) throws Exception {

	    int userIdx = authService.getUserIdxByToken(authorization);
    	CommonResponse res = new CommonResponse();

    	int result = scrapService.deleteScrapLike(userIdx, idx);
    	
    	if (result > 0) {
    		HashMap<String, Object> data = new HashMap<>();
    		data.put("likeCount", scrapService.getScrapLikeCount(idx));
        	res.setResult(ResponseResult.OK);
        	res.setData(data);
        	res.setMessage("좋아요 취소 완료");
    	} else {
    		res.setResult(ResponseResult.ERROR);
    		res.setMessage("좋아요 취소 실패");
    	}
    	return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
