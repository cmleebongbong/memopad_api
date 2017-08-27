package com.almond.article.domain;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

	private static final long serialVersionUID = -5416268702962198565L;
	
	private long idx;
	private String id;
	private String password;
	private String loginKey;
	private Date regDate;
	
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
	public String getLoginKey() {
		return loginKey;
	}
	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}