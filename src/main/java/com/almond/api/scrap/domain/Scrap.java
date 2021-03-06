package com.almond.api.scrap.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.almond.api.map.domain.Map;

import lombok.Data;

@Data
public class Scrap implements Serializable {
	
	private static final long serialVersionUID = -1613440005373741248L;
	
	private long idx;
	private String nationCode;
	private int cityIdx;
	private int categoryIdx;
	private long imageIdx;
	private String imageUrl;
	private String title;
	private String description;
	private String writer;
	private String url;
	private Map map;
	private int likeCount;
	private Date regDate;
	private boolean owner;
	private boolean liked;
	
	public String getRegDate() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		return regDate != null ? df.format(regDate) : null;
	}
}