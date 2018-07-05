package com.almond.common.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.almond.common.service.TetrisService;

@RestController
@RequestMapping(value="/tetris")
public class TetrisController {

	@Autowired
	TetrisService tetrisService;
    
    /**
     * Tetris get Scores
     * 
     * @return String
     */
    @RequestMapping(value="/score", method=RequestMethod.GET)
    public List<HashMap<String, Object>> getScore() throws Exception {
    	List<HashMap<String, Object>> scores = tetrisService.getScores(); 
   		return scores;
    }

    /**
     * Tetris Post Score
     * 
     * @return String
     */
    @RequestMapping(value="/score", method=RequestMethod.POST)
    public int postScore(@RequestParam String id,
    					 @RequestParam int score) throws Exception {
    	
    	HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = req.getRemoteAddr();
    	
    	int result = tetrisService.postScore(id, score, ip);
   		return result;
    }
}