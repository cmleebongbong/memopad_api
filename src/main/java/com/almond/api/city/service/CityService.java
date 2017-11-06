package com.almond.api.city.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.city.dao.CityMapper;
import com.almond.api.city.domain.City;
import com.almond.api.nation.domain.Nation;
import com.almond.api.nation.service.NationService;

@Service
public class CityService {
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private NationService nationService;
	
    /**
     * 전체 도시목록
     * 
     * @return ArrayList<Location>
     * @throws Exception
     */
    public ArrayList<City> cityList() throws Exception {
    	ArrayList<City> cityList = cityMapper.cityList();
    	return cityList;
    }
	
    /**
     * 특정국가의 도시목록
     * 
     * @param nationCode
     * @return ArrayList<Location>
     * @throws Exception
     */
    public ArrayList<City> cityList(String nationCode) throws Exception {
    	ArrayList<City> cityList = cityMapper.cityList(nationCode);
    	return cityList;
    }
	
    /**
     * 국가별 도시 목록
     * 
     * @return LinkedHashMap<String, ArrayList<City>>
     * @throws Exception
     */
    public LinkedHashMap<String, ArrayList<City>> cityListAll() throws Exception {
    	LinkedHashMap<String, ArrayList<City>> cityList = new LinkedHashMap<String, ArrayList<City>>();
    	ArrayList<Nation> nationList = nationService.nationList();
    	for(Nation nation : nationList) {
    		ArrayList<City> locations = cityList(nation.getCode());
    		cityList.put(nation.getCode(), locations);
    	}
    	
    	return cityList;
    }
}
