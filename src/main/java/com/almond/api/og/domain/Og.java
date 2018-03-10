package com.almond.api.og.domain;

import java.io.Serializable;

import com.almond.common.domain.Map;

import lombok.Data;

@Data
public class Og implements Serializable {

	private static final long serialVersionUID = -5416268702962198565L;
	
	private String ogUrl;
	private String ogImageUrl;
	private String ogDescription;
	private String ogTitle;
	private Map map;
}