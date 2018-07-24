package com.almond.api.map.dao;

import com.almond.api.map.domain.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface MapMapper {
	int registerMap(Map map) throws Exception;

    int updateMap(Map map) throws Exception;
}
