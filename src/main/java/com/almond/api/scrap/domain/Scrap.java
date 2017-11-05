package com.almond.api.scrap.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Scrap implements Serializable {
	
	private static final long serialVersionUID = -1613440005373741248L;
	
	private long idx;
	private String nationCode;
	private int cityIdx;
	private int categoryIdx;
	private String imageUrl;
	private String title;
	private String description;
	private String writer;
	private String url;
	private Date regDate;
}