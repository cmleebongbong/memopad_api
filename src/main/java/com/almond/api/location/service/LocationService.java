package com.almond.api.location.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.location.dao.LocationMapper;
import com.almond.api.location.domain.Location;

@Service
public class LocationService {
    @Autowired
    private LocationMapper locationMapper;
	
    /**
     * @return ArrayList<Location>
     * @throws Exception
     */
    public ArrayList<Location> locationList() throws Exception {
    	ArrayList<Location> locationList = locationMapper.locationList();
    	return locationList;
    }
	
    /**
     * @param nationCode
     * @return ArrayList<Location>
     * @throws Exception
     */
    public ArrayList<Location> locationList(String nationCode) throws Exception {
    	ArrayList<Location> locationList = locationMapper.locationList(nationCode);
    	return locationList;
    }
}
