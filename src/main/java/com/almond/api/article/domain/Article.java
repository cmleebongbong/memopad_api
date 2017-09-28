package com.almond.api.article.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Article implements Serializable {

	private static final long serialVersionUID = -5416268702962198565L;
	
	private long idx;
	private String id;
	private String password;
	private Date regDate;
}