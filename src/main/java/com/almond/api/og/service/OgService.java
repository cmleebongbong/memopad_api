package com.almond.api.og.service;

import java.nio.charset.Charset;
import java.util.HashMap;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.almond.api.og.domain.Og;
import com.almond.common.domain.Map;
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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("text/plain;charset=utf-8"));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		ResponseEntity<String> result = template.exchange(url, HttpMethod.GET, entity, String.class);
		
		// HTML 추출 후 Document 형변환
    	Document doc = Jsoup.parse(result.getBody());
    	HashMap<String, Elements> pageData = crollingPage(doc, url);
		Elements ogTags = pageData.get("ogTags");
		Elements mapData = pageData.get("mapData");
		
		Og ogData = new Og();
        // 필요한 페이지 데이터를 추려낸다
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
        
        if (mapData.size() > 0) {
            Element mapTag = mapData.get(0);	
            JSONObject jsonObj = new JSONObject(mapTag.attr("data-linkdata"));
            Map map = new Map();
            map.setTitle(jsonObj.get("title").toString());
            map.setLatitude(jsonObj.get("latitude").toString());
            map.setLongitude(jsonObj.get("longitude").toString());
            map.setMarkerLatitude(jsonObj.get("markerLatitude").toString());
            map.setMarkerLongitude(jsonObj.get("markerLongitude").toString());
            ogData.setMap(map);
        }
		
		return ogData;
	}

	/**
	 * 재귀함수로 document안의 frame속 까지 og태그, map데이터를 찾는다.
	 * 
	 * @param doc
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Elements> crollingPage(Document doc, String url) throws Exception {
		Elements ogTags = doc.select("meta[property^=og:]");
		Elements mapData = doc.select("[data-linktype=map]");
		
		HashMap<String, Elements> pageData = new HashMap<String, Elements>();
		pageData.put("ogTags", ogTags);
		pageData.put("mapData", mapData);
		
		// OG 태그가 없는경우 frame iframe 검색
		if (ogTags.size() < 1) {
			Elements frames = doc.select("iframe, frame");
			
			if(frames.size() > 0) {
				for(int i = 0; i < frames.size(); i++) {
					String frameSrc = frames.get(i).attr("src");

					// src가 http로 시작하지 않는 경우 hostname 추가처리
					if (!frameSrc.substring(0, 4).equals("http")) {
						frameSrc = utilService.getHostName(url) + frameSrc;
					}
					
					Document innerDoc = Jsoup.connect(frameSrc).get();
					HashMap<String, Elements> innerPageData = crollingPage(innerDoc, frameSrc);
					if (innerPageData.get("ogTags").size() > 0) {
						return innerPageData;
					}
					if (innerPageData.get("ogTags").size() < 1) {
						return null;
					}
				}
			// frame도 없는경우 null 반환
			} else {
				return null;
			}
		}
		return pageData;
	}
}
