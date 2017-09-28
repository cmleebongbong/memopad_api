package com.almond.api.location.dao;

import java.util.ArrayList;

import com.almond.api.location.domain.Location;

public interface LocationMapper {
	public ArrayList<Location> locationList() throws Exception;
	public ArrayList<Location> locationList(String nationCode) throws Exception;
}
