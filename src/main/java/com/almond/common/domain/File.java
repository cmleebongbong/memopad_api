package com.almond.common.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class File implements Serializable {

	private static final long serialVersionUID = -685179890697122984L;
	
	private long idx;
	private String awsUploadName;
	private String fileName;
	private String fileSize;
	private String extension;
	private String url;
	private Date regDate;
	
	public String getRegDate() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		return regDate != null ? df.format(regDate) : null;
	}
}