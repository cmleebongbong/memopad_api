package com.almond.account.domain;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 3743306839160053303L;
	
	private long idx;
	private String id;
	private String password;
	private Date reg_date;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
}