package com.almond.api.scrap.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.almond.api.map.domain.Map;
import com.almond.api.map.service.MapService;
import com.almond.api.scrap.dao.ScrapMapper;
import com.almond.api.scrap.domain.Scrap;

@Service
public class ScrapService {
	@Autowired
	private MapService mapService;
	
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
    @Transactional
    public int scrapRegister(Scrap scrap) throws Exception {
    	int result = 0;
    	try {
    		scrapMapper.scrapRegister(scrap);
    		Map map = scrap.getMap();
    		if (scrap.getIdx() == 0) {
    			throw new Exception();
    		}
    		map.setArticleIdx(scrap.getIdx());
    		map.setArticleCategory("scrap");
			result = mapService.mapRegister(map);
			if (result == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
    	return result;
    }
}
