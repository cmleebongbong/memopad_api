package com.almond.domain;

import java.io.Serializable;

public class Article implements Serializable {
	
	private static final long serialVersionUID = 5411789690193912114L;
	
	private long idx;
	private String id;
	private Double lat;
	private Double lon;
	private String reg_date;
	
	public long getIdx() {
		return idx;
	}
	public void setIdx(long idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}