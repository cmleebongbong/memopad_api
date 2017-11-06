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
     * 스크랩 목록 전체조회
     * 
     * @return
     * @throws Exception
     */
    public ArrayList<Scrap> scrapList() throws Exception {
    	return scrapMapper.scrapList();
    }
    
    /**
     * 스크랩 목록 by 국가별
     * 
     * @return
     * @throws Exception
     */
    public ArrayList<Scrap> scrapList(String nationCode) throws Exception {
    	return scrapMapper.scrapList(nationCode);
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
