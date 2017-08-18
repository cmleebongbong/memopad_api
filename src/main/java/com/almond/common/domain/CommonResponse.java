package com.almond.common.domain;

import java.io.Serializable;

import com.almond.common.data.ResponseResult;

public class CommonResponse implements Serializable {
	
	private static final long serialVersionUID = -4437304850925467729L;

	private String result;
	private String message;
	private Object data;
	
	public CommonResponse() {
		
	}
	public CommonResponse(ResponseResult result) {
		this.result = result.toString();
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(ResponseResult result) {
		this.result = result.toString();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
