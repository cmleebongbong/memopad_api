package com.almond.dao;

import java.util.List;
import com.almond.domain.Article;

public interface ArticleMapper {
	//public int getLocation(@Param("useremail")String useremail)throws Exception;
	public List<Article> getLocation() throws Exception;
}
