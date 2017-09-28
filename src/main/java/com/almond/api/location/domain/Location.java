package com.almond.api.location.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Location implements Serializable {

	private static final long serialVersionUID = 3030084290085795326L;
	
	private long idx;
	private String nationCode;
	private String nationName;
	private String name;
	private Date regDate;
}