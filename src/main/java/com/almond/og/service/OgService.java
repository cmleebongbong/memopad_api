package com.almond.og.service;


import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.almond.og.domain.Og;

@Service
public class OgService {
	
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
            return null;
        }

		Og ogData = new Og();
        // 필요한 OGTag를 추려낸다
        for (int i = 0; i < ogTags.size(); i++) {
            Element tag = ogTags.get(i);

            String text = tag.attr("property");
            if ("og:url".equals(text)) {
            	ogData.setOgUrl(tag.attr("content"));
            } else if ("og:image".equals(text)) {
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
