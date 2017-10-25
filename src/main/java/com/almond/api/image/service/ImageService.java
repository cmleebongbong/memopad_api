package com.almond.api.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	/**
	 * 이미지 Url to Byte Array
	 * 
	 * @param url
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] imageToByte(String url) throws Exception {
    	URL imgUrl = new URL(url);
    	BufferedImage bufferedimage = ImageIO.read(imgUrl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedimage, "png", baos);
        byte[] imageData = baos.toByteArray();
		
		return imageData;
	}
}
