package com.almond.api.scrap.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.almond.api.file.domain.File;
import com.almond.api.file.service.FileService;
import com.almond.api.map.domain.Map;
import com.almond.api.map.service.MapService;
import com.almond.api.scrap.dao.ScrapMapper;
import com.almond.api.scrap.domain.Scrap;
import com.almond.util.awsS3.AWSObjectResult;
import com.almond.util.awsS3.AWSWrapper;

@Service
public class ScrapService {
	private final MapService mapService;
    private final ScrapMapper scrapMapper;
	private final FileService fileService;
	
	@Value("${aws.app.key}")
	private String appKey;
	
	@Value("${aws.app.secret}")
	private String appSecret;
	
	@Value("${aws.app.bucket}")
	private String bucket;

	@Autowired
	public ScrapService(MapService mapService, ScrapMapper scrapMapper, FileService fileService) {
		this.mapService = mapService;
		this.scrapMapper = scrapMapper;
		this.fileService = fileService;
	}

    /**
     * 스크랩 목록 갯수
     */
    public int getScrapListTotalCount(String nationCode, int[] cityIdx, int[] categoryIdx, String nickname) throws Exception {
    	return scrapMapper.getScrapListTotalCount(nationCode, cityIdx, categoryIdx, nickname);
    }
    
    /**
     * 스크랩 목록 조회
     */
    public ArrayList<Scrap> getScrapList(String nationCode, int[] cityIdx, int[] categoryIdx, int limit, int page, int userIdx, String nickname) throws Exception {
        return scrapMapper.getScrapList(nationCode, cityIdx, categoryIdx, limit, page, userIdx, nickname);
    }

    /**
     * 스크랩 상세 조회
     */
    public Scrap getScrap(int idx, int userIdx) throws Exception {
        return scrapMapper.getScrap(idx, userIdx);
    }

    /**
     * 스크랩 등록
     */
    @Transactional
    public int registerScrap(Scrap scrap) {
    	int result = 0;
    	try {
        	AWSWrapper awsWrapper = new AWSWrapper(appKey, appSecret, bucket);
    		AWSObjectResult uploadResult = awsWrapper.uploadByUrl(scrap.getImageUrl());

    		File file = new File();

    		file.setAwsUploadName(uploadResult.getUuidFileName());
    		file.setFileName(uploadResult.getOrginalFileName());
    		file.setFileSize(uploadResult.getFileSize());
    		file.setExtension(uploadResult.getFileExt());
    		file.setUrl("https://image.almondbongbong.com/" + uploadResult.getUuidFileName());
    		
    		result = fileService.insertFile(file);
    		if (result == 0) {
    			throw new Exception();
    		}
    		
    		scrap.setImageIdx(file.getIdx());
    		result = scrapMapper.registerScrap(scrap);
    		if (result == 0) {
    			throw new Exception();
    		}
    		
    		if (scrap.getMap() != null) {
        		Map map = scrap.getMap();
        		map.setArticleIdx(scrap.getIdx());
        		map.setArticleCategory("scrap");
    			result = mapService.registerMap(map);
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
     * 스크랩 수정
     */
    @Transactional
    public int updateScrap(Scrap scrap) {
        int result = 0;

        try {
            result = scrapMapper.updateScrap(scrap);
            if (result == 0) {
                throw new Exception();
            }

            if (scrap.getMap() != null) {
                Map map = scrap.getMap();
                map.setArticleIdx(scrap.getIdx());
                map.setArticleCategory("scrap");

                Map hasMap = mapService.getMapByArticle(map);
                if (hasMap == null) {
                    result = mapService.registerMap(map);
                } else {
                    result = mapService.updateMap(map);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    	return result;
    }
	
    /**
     * 스크랩 삭제
     */
    public int deleteScrap(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.deleteScrap(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 조회
     */
    public int getScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.getScrapLike(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 갯수 조회
     */
    public int getScrapLikeCount(int scrapIdx) throws Exception {
    	return scrapMapper.getScrapLikeCount(scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 등록
     */
    public int insertScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.insertScrapLike(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 사용처리
     */
    public int activeScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.activeScrapLike(userIdx, scrapIdx);
    }
	
    /**
     * 스크랩 좋아요 삭제처리
     */
    public int deleteScrapLike(int userIdx, int scrapIdx) throws Exception {
    	return scrapMapper.deleteScrapLike(userIdx, scrapIdx);
    }
}
