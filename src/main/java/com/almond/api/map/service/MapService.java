package com.almond.api.map.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.map.dao.MapMapper;
import com.almond.api.map.domain.Map;

@Service
public class MapService {
    @Autowired
    private MapMapper mapMapper;
	
    /**
     * 지도 등록
     * 
     * @param Map
     * @return int
     * @throws Exception
     */
    public int mapRegister(Map map) throws Exception {
    	System.out.println("map : " + map);
    	return mapMapper.mapRegister(map);
    }
}
