package com.almond.api.category.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Category implements Serializable {

	private static final long serialVersionUID = 4583520926614409418L;
	
	private long idx;
	private String name;
	private Date regDate;
}