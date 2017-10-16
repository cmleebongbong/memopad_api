package com.almond.api.og.service;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.almond.api.og.domain.Og;
import com.almond.util.UtilService;

@Service
public class OgService {
	
	@Autowired
	UtilService utilService;
	
	/**
	 * Url로 Og 태그 추출
	 * 
	 * @param user
	 * @return token
	 * @throws Exception
	 */
	public Og getOg(String url) throws Exception {
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		String result = template.getForObject(url, String.class);
    	
    	Document doc = Jsoup.parse(result);
        Elements ogTags = doc.select("meta[property^=og:]");
        
        if (ogTags.size() <= 0) {
            Element iframe = doc.select("iframe").first();
            if (iframe != null) {
                String iframeSrc = iframe.attr("src");
                Document iframeDoc = Jsoup.connect(iframeSrc).get();
            	ogTags = iframeDoc.select("meta[property^=og:]");
            }
            Element frame = doc.select("frame").first();
            if (frame != null) {
                String frameSrc = frame.attr("src");
                if (!frameSrc.substring(0, 3).equals("http")) {
                	frameSrc = utilService.getHostName(url) + frameSrc;
                }
                
                Document frameDoc = Jsoup.connect(frameSrc).get();
            	ogTags = frameDoc.select("meta[property^=og:]");
                if (ogTags.size() <= 0) {
                    return null;
                }
            }
        }

		Og ogData = new Og();
        // 필요한 OGTag를 추려낸다
        for (int i = 0; i < ogTags.size(); i++) {
            Element tag = ogTags.get(i);

            String text = tag.attr("property");
            if ("og:url".equals(text)) {
            	ogData.setOgUrl(tag.attr("content"));
            } else if ("og:image".equals(text)) {
            	URL imgUrl = new URL(tag.attr("content"));
            	BufferedImage bufferedimage = ImageIO.read(imgUrl);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedimage, "png", baos);
                byte[] imageData = baos.toByteArray();
                ogData.setOgImageData(imageData);
            	ogData.setOgImageUrl(tag.attr("content"));
            } else if ("og:description".equals(text)) {
            	ogData.setOgDescription(tag.attr("content"));
            } else if ("og:title".equals(text)) {
            	ogData.setOgTitle(tag.attr("content"));
            }
        }
		
		return ogData;
	}
}
