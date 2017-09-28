package com.almond.api.location.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.location.domain.Location;
import com.almond.api.location.service.LocationService;
import com.almond.api.nation.domain.Nation;
import com.almond.api.nation.service.NationService;
import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;

@SpringBootApplication
@RestController
@RequestMapping(value="/api/location")
public class LocationController {
	
	@Autowired
	LocationService locationService;
	@Autowired
	NationService nationService;

    /**
     * 모든 지역 목록 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> location() throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	ArrayList<Location> locationList = locationService.locationList();
    	
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
    public ResponseEntity<CommonResponse> locationForArray() throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	HashMap<String, ArrayList<Location>> locationList = new HashMap<String, ArrayList<Location>>();
    	ArrayList<Nation> nationList = nationService.nationList();
    	for(Nation nation : nationList) {
    		ArrayList<Location> locations = locationService.locationList(nation.getCode());
    		locationList.put(nation.getCode(), locations);
    	}
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(locationList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }

    /**
     * 특정 국가 지역 목록 조회
     * 
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="/{nationCode}", method=RequestMethod.GET)
    public ResponseEntity<CommonResponse> locationByNation(
    		@PathVariable String nationCode) throws Exception {
    	CommonResponse res = new CommonResponse();
    	
    	ArrayList<Location> locationList = locationService.locationList(nationCode);
    	
    	res.setResult(ResponseResult.OK);
    	res.setData(locationList);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
    
}
