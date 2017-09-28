package com.almond.api.nation.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Nation implements Serializable {

	private static final long serialVersionUID = -4242113918647332265L;
	
	private long idx;
	private String code;
	private String name;
	private Date regDate;
}