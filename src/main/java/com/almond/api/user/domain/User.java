package com.almond.api.user.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class User implements Serializable {
	
	private static final long serialVersionUID = 3743306839160053303L;
	
	private long idx;
	private String id;
	private String nickname;
	private String password;
	private String token;
	private Date regDate;
	private int totalScrap;
}