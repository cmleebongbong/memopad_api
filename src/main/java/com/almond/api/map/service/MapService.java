package com.almond.api.map.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.map.dao.MapMapper;
import com.almond.api.map.domain.Map;

@Service
public class MapService {
    private MapMapper mapMapper;

    @Autowired
    public MapService(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    /**
     * 지도 조회 by article_idx, article_category
     */
    public Map getMapByArticle(Map map) throws Exception {
        return mapMapper.getMapByArticle(map);
    }

    /**
     * 지도 등록
     */
    public int registerMap(Map map) throws Exception {
    	return mapMapper.registerMap(map);
    }

    /**
     * 지도 수정
     */
    public int updateMap(Map map) throws Exception {
        return mapMapper.updateMap(map);
    }
}
