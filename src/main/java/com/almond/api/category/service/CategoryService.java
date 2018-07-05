package com.almond.api.category.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.category.dao.CategoryMapper;
import com.almond.api.category.domain.Category;

@Service
public class CategoryService {
	
    @Autowired
    private CategoryMapper categoryMapper;
	
    /**
     * @return ArrayList<Location>
     * @throws Exception
     */
    public ArrayList<Category> categoryList() throws Exception {
    	ArrayList<Category> categoryList = categoryMapper.categoryList();
    	return categoryList;
    }
}
