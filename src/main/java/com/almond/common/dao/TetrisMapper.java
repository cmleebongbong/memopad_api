package com.almond.common.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

public interface TetrisMapper {
	public ArrayList<HashMap<String, Object>> getScores() throws Exception;
	public int postScore(@Param(value="id") String id, 
						 @Param(value="score") int score, 
						 @Param(value="ip") String ip) throws Exception;
}
