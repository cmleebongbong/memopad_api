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
    public int scrapListTotalCount(String nationCode, int[] cityIdx, int[] categoryIdx, String nickname) throws Exception {
    	return scrapMapper.scrapListTotalCount(nationCode, cityIdx, categoryIdx, nickname);
    }
    
    /**
     * 스크랩 목록 조회
     * 
     * @return
     * @throws Exception
     */
    public ArrayList<Scrap> scrapList(String nationCode, int[] cityIdx, int[] categoryIdx, int limit, int page, int userIdx, String nickname) throws Exception {
    	return scrapMapper.scrapList(nationCode, cityIdx, categoryIdx, limit, page, userIdx, nickname);
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
    		result = scrapMapper.scrapRegister(scrap);
    		if (scrap.getIdx() == 0) {
    			throw new Exception();
    		}
    		
    		if (scrap.getMap() != null) {
        		Map map = scrap.getMap();
        		map.setArticleIdx(scrap.getIdx());
        		map.setArticleCategory("scrap");
    			result = mapService.mapRegister(map);
    			if (result == 0) {
    				throw new Exception();
    			}
    		}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
    	return result;
    }
	
    /**
     * 스크랩 삭제
     * 
     * @param userIdx, scrapIdx
     * @return int
     * @throws Exception
     */
    public int scrapDelete(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.scrapDelete(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 조회
     * 
     * @param userIdx, scrapIdx
     * @return int
     * @throws Exception
     */
    public int getScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.getScrapLike(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 갯수 조회
     * 
     * @param scrapIdx
     * @return int
     * @throws Exception
     */
    public int getScrapLikeCount(int scrapIdx) throws Exception {
    	return scrapMapper.getScrapLikeCount(scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 등록
     * 
     * @param userIdx, scrapIdx
     * @return int
     * @throws Exception
     */
    public int insertScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.insertScrapLike(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 사용처리
     * 
     * @param userIdx, scrapIdx
     * @return int
     * @throws Exception
     */
    public int useScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.useScrapLike(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 삭제처리
     * 
     * @param userIdx, scrapIdx
     * @return int
     * @throws Exception
     */
    public int deleteScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.deleteScrapLike(userIdx, scrapIdx);
    }
}
