package com.almond.api.scrap.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.almond.api.scrap.domain.Scrap;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapMapper {
	int getScrapListTotalCount(@Param(value = "nationCode") String nationCode,
								   @Param(value = "cityIdx") int[] cityIdx,
								   @Param(value = "categoryIdx") int[] categoryIdx,
								   @Param(value = "nickname") String nickname) throws Exception;
	ArrayList<Scrap> getScrapList(@Param(value = "nationCode") String nationCode,
									  @Param(value = "cityIdx") int[] cityIdx,
									  @Param(value = "categoryIdx") int[] categoryIdx,
									  @Param(value = "limit") int limit,
									  @Param(value = "page") int page,
									  @Param(value = "userIdx") int userIdx,
									  @Param(value = "nickname") String nickname) throws Exception;
	Scrap getScrap(@Param(value = "idx") int idx,
	               @Param(value = "userIdx") int userIdx) throws Exception;
	int registerScrap(Scrap scrap) throws Exception;
	int getScrapLike(@Param(value = "userIdx") int userIdx,
			 				@Param(value = "scrapIdx") int scrapIdx) throws Exception;
	int updateScrap(Scrap scrap) throws Exception;
	int deleteScrap(@Param(value = "userIdx") int userIdx,
			   			   @Param(value = "scrapIdx") int scrapIdx) throws Exception;
	int getScrapLikeCount(@Param(value = "scrapIdx") int scrapIdx) throws Exception;
	int insertScrapLike(@Param(value = "userIdx") int userIdx,
						 	   @Param(value = "scrapIdx") int scrapIdx) throws Exception;
	int activeScrapLike(@Param(value = "userIdx") int userIdx,
		 	   				   @Param(value = "scrapIdx") int scrapIdx) throws Exception;
	int deleteScrapLike(@Param(value = "userIdx") int userIdx,
							   @Param(value = "scrapIdx") int scrapIdx) throws Exception;
}
