package com.almond.common.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Map implements Serializable {

	private static final long serialVersionUID = 8315632623992743216L;

	private String title;
	private String latitude;
	private String longitude;
	private String markerLatitude;
	private String markerLongitude;
}