package com.almond.api.category.dao;

import java.util.ArrayList;

import com.almond.api.category.domain.Category;

public interface CategoryMapper {
	public ArrayList<Category> categoryList() throws Exception;
}
