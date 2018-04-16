package com.almond.api.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	/**
	 * 이미지 Url 그대로 to Byte Array
	 * 
	 * @param urlStr
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] imageToByte(String urlStr) throws Exception {
    	URL url = new URL(urlStr);
        InputStream is = url.openStream();
        
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}
	
		buffer.flush();
		return buffer.toByteArray();
	}
	
	
	/**
	 * 이미지 Url 파싱후 to Byte Array
	 * 
	 * @param url
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] imageToParseredByte(String url) throws Exception {
    	URL imgUrl = new URL(url);
    	BufferedImage bufferedimage = ImageIO.read(imgUrl);
    	
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        ImageIO.write(bufferedimage, "jpg", baos);
      
        baos.flush();
        byte[] imageData = baos.toByteArray();
        baos.close();
		
		return imageData;
	}
}
