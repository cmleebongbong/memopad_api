package com.almond.common.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
public class PageController {
    
    /**
     * Page for Swagger
     * 
     * @return String
     */
    @RequestMapping(value="/api", method=RequestMethod.GET)
    public String swagger() throws Exception {
    	
   		return "redirect:/swagger-ui.html";
    }
}