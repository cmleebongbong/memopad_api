package com.almond.common.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.common.dao.TetrisMapper;

@Service
public class TetrisService {

    @Autowired
    private TetrisMapper tetrisMapper;
    
    /**
     * Scores 조회
     * 
     * @throws Exception
     */
    public List<HashMap<String, Object>> getScores() throws Exception {
    	return tetrisMapper.getScores();
    }
    
    /**
     * Score 등록
     * 
     * @throws Exception
     */
    public int postScore(String id, int score, String ip) throws Exception {
    	return tetrisMapper.postScore(id, score, ip);
    }
}
