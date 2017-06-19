package com.almond.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.almond.dao.ArticleMapper;
import com.almond.domain.Article;

@SpringBootApplication
@RestController
public class ArticleController {
	
    @Autowired
    private ArticleMapper articleMapper;
     
    @RequestMapping(value="/location",method=RequestMethod.GET)
    public List<Article> location() throws Exception {
        List<Article> list = articleMapper.getLocation();
         
        for(int i=0; i<list.size(); i++) {
            System.out.println(list.get(i).getId());
        }
        
        return list;
    }
}