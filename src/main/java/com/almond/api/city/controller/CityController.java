package com.almond.api.city.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.city.domain.City;
import com.almond.api.city.service.CityService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/city")
public class CityController {
	
	@Autowired
	private CityService cityService;

    /**
     * 모든 지역 목록 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> city() throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	ArrayList<City> locationList = cityService.cityList();
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(locationList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
    
    /**
     * 모든 지역 목록을 국가별로 분류하여 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> cityForArray() throws Exception {
    	CommonResponse res = new CommonResponse();
    	
//    	LinkedHashMap<String, ArrayList<City>> cityList = new LinkedHashMap<String, ArrayList<City>>();
//    	ArrayList<Nation> nationList = nationService.nationList();
//    	for(Nation nation : nationList) {
//    		ArrayList<City> locations = cityService.cityList(nation.getCode());
//    		cityList.put(nation.getCode(), locations);
//    	}
    	
    	LinkedHashMap<String, ArrayList<City>> cityList = cityService.cityListAll();
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(cityList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }

    /**
     * 특정 국가 지역 목록 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="/{nationCode}", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> cityByNation(
    		@PathVariable String nationCode) throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	ArrayList<City> cityList = cityService.cityList(nationCode);
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(cityList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
    
}
