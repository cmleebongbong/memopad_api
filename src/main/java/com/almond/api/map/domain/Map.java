package com.almond.api.map.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class Map implements Serializable {

	private static final long serialVersionUID = 8315632623992743216L;

	private long idx;
	private long articleIdx;
	private String articleCategory;
	private String title;
	private String latitude;
	private String longitude;
	private String markerLatitude;
	private String markerLongitude;
	private Date regDate;
	
	public String getRegDate() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		return df.format(regDate);
	}
}