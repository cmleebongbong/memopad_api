package com.almond.api.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.image.service.ImageService;

import io.swagger.annotations.Api;

@SpringBootApplication
@Api(tags = "Image")
@RestController
@RequestMapping(value="/api/image")
public class ImageController {

	@Autowired
	ImageService imageService;

    /**
     * 이미지 to Byte Array
     * 
     * @param url
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public byte[] imageToByte(
    		@RequestParam String url) throws Exception {
    	
    	System.out.println("url : " + url);
    	byte[] imageData = imageService.imageToByte(url);
    	return imageData;
    }
}
