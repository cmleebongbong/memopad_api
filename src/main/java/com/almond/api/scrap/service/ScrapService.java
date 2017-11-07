package com.almond.api.scrap.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.scrap.dao.ScrapMapper;
import com.almond.api.scrap.domain.Scrap;

@Service
public class ScrapService {
    @Autowired
    private ScrapMapper scrapMapper;
    
    /**
     * 스크랩 목록 갯수
     * 
     * @return
     * @throws Exception
     */
    public int scrapListTotalCount(String nationCode, int[] cityIdx, int[] categoryIdx) throws Exception {
    	return scrapMapper.scrapListTotalCount(nationCode, cityIdx, categoryIdx);
    }
    
    /**
     * 스크랩 목록 조회
     * 
     * @return
     * @throws Exception
     */
    public ArrayList<Scrap> scrapList(String nationCode, int[] cityIdx, int[] categoryIdx, int limit, int page, int userIdx) throws Exception {
    	return scrapMapper.scrapList(nationCode, cityIdx, categoryIdx, limit, page, userIdx);
    }
	
    /**
     * 스크랩 등록
     * 
     * @param Scrap
     * @return int
     * @throws Exception
     */
    public int scrapRegister(Scrap scrap) throws Exception {
    	return scrapMapper.scrapRegister(scrap);
    }
}
