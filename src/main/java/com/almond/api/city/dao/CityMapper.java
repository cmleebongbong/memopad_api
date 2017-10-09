package com.almond.api.city.dao;

import java.util.ArrayList;

import com.almond.api.city.domain.City;

public interface CityMapper {
	public ArrayList<City> cityList() throws Exception;
	public ArrayList<City> cityList(String nationCode) throws Exception;
}
