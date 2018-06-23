package com.almond.util.awsS3;

import com.amazonaws.services.s3.model.PutObjectResult;

public class AWSObjectResult extends PutObjectResult {

	private String orginalFileName;
	private String uuidFileName;
	private String fileExt;
	private String fileSize;
	
	AWSObjectResult(PutObjectResult result) {
		this.setETag(result.getETag());
		this.setContentMd5(result.getContentMd5());
	}
	
	public String getOrginalFileName() {
		return orginalFileName;
	}
	
	public void setOrginalFileName(String orginalFileName) {
		this.orginalFileName = orginalFileName;
	}
	
	public String getUuidFileName() {
		return uuidFileName;
	}
	
	public void setUuidFileName(String uuidFileName) {
		this.uuidFileName = uuidFileName;
	}
	
	public String getFileExt() {
		return fileExt;
	}
	
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}
