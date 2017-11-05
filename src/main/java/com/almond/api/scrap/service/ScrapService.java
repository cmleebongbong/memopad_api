package com.almond.api.scrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.scrap.dao.ScrapMapper;
import com.almond.api.scrap.domain.Scrap;

@Service
public class ScrapService {
    @Autowired
    private ScrapMapper scrapMapper;
	
    /**
     * @param Scrap
     * 
     * @return int
     * @throws Exception
     */
    public int scrapRegister(Scrap scrap) throws Exception {
    	return scrapMapper.scrapRegister(scrap);
    }
}
