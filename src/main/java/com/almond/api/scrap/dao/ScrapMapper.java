package com.almond.api.scrap.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.almond.api.scrap.domain.Scrap;

public interface ScrapMapper {
	public int scrapListTotalCount(@Param(value="nationCode") String nationCode, @Param(value="cityIdx") int[] cityIdx, @Param(value="categoryIdx") int[] categoryIdx) throws Exception;
	public ArrayList<Scrap> scrapList(@Param(value="nationCode") String nationCode,
									  @Param(value="cityIdx") int[] cityIdx, 
									  @Param(value="categoryIdx") int[] categoryIdx, 
									  @Param(value="limit") int limit, 
									  @Param(value="page") int page, 
									  @Param(value="userIdx") int userIdx) throws Exception;
	public int scrapRegister(Scrap scrap) throws Exception;
}
