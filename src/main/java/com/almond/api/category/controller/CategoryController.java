package com.almond.api.category.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.category.domain.Category;
import com.almond.api.category.service.CategoryService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

    /**
     * 모든 카테고리 목록 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> category() throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	ArrayList<Category> categoryList = categoryService.categoryList();
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(categoryList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
    
  
}
