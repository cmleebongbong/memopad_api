package com.almond.util.awsS3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.almond.util.UtilService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AWSWrapper {
	
	private UtilService utilService = new UtilService();
	
	private final String AWS_APP_KEY;
	private final String AWS_APP_SECRET;
	private final String AWS_APP_BUCKET;
	
	private AWSCredentials credentials;
	private AmazonS3Client amazonS3Client;
	
	public AWSWrapper(String appKey, String appSecret, String bucket) {
		this.AWS_APP_KEY = appKey;
		this.AWS_APP_SECRET = appSecret;
		this.AWS_APP_BUCKET = bucket;
		
		this.credentials = new BasicAWSCredentials(AWS_APP_KEY, AWS_APP_SECRET);
		
		amazonS3Client = new AmazonS3Client(credentials);
		amazonS3Client.setEndpoint("s3.ap-northeast-2.amazonaws.com");
	}
	
	private AWSObjectResult upload(InputStream inputStream, String uploadKey) throws IOException {
		
		String contentType = "";
		switch (uploadKey.substring(uploadKey.lastIndexOf(".") + 1)) {
			case "jpg": contentType = "image/jpeg"; break;
			case "png": contentType = "image/png"; break;
			case "gif": contentType = "image/gif"; break;
			default: break;
		}
		
		// 메타 데이터 설정
		ObjectMetadata metaData = new ObjectMetadata();
		byte[] bytes = IOUtils.toByteArray(inputStream);
		metaData.setContentLength(bytes.length);
		metaData.setContentType(contentType);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

		// 업로드
		PutObjectRequest putObjectRequest = new PutObjectRequest(AWS_APP_BUCKET, uploadKey, byteArrayInputStream, metaData);

		// 결과값 리턴
		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
		AWSObjectResult awsObjectResult = new AWSObjectResult(putObjectResult);
		awsObjectResult.setFileSize(String.valueOf(bytes.length));

		IOUtils.closeQuietly(inputStream);

		return awsObjectResult;
	}
	
	public List<AWSObjectResult> upload(MultipartFile[] multipartFiles) {
		List<AWSObjectResult> awsObjectResults = new ArrayList<>();
		
		Arrays.stream(multipartFiles)
	        .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
	        .forEach(multipartFile -> {
	            try {
	            	String originalFileName = multipartFile.getOriginalFilename();
	        		String fileExt = originalFileName.lastIndexOf(".") > -1 
	        				? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
	        		String uuid = UUID.randomUUID().toString();
	        		String fileName = uuid + fileExt;
	            	
	        		PutObjectResult result = upload(multipartFile.getInputStream(), fileName);
	        		
	        		AWSObjectResult bean = new AWSObjectResult(result);
	        		
	        		bean.setFileExt(fileExt);
	        		bean.setFileSize(String.valueOf(multipartFile.getSize()));
	        		bean.setOrginalFileName(multipartFile.getOriginalFilename());
	        		bean.setUuidFileName(fileName);
	        		
			    	// 이미지인지 검증하기
//	        		String[] imageTypes = {".jpg",".png",".jpge",".gif"};
//	        		boolean isImage = Arrays.stream(imageTypes).anyMatch(x->x.equals(fileExt));
	  			
	        		awsObjectResults.add(bean);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        });
		
		return awsObjectResults;
	}

	public AWSObjectResult uploadByUrl(String urlStr) {
		AWSObjectResult awsObjectResult = null;
		
		try {
	    	URL url = new URL(urlStr);
	    	InputStream is = url.openStream();
		    
        	String originalFileName = utilService.getFileNameByUrl(urlStr);
    		String fileExt = utilService.getExtByFilename(originalFileName);
        	
    		String uuid = UUID.randomUUID().toString();
    		String fileName = uuid + "." + fileExt;
    		
	    	// 이미지인지 검증하기
    		String[] imageTypes = {"jpg","png","jpeg","gif"};
    		boolean isImage = Arrays.stream(imageTypes).anyMatch(x->x.equals(fileExt));
    		if (isImage) fileName = "image/" + fileName;
        	
    		awsObjectResult = upload(is, fileName);
    		awsObjectResult.setFileExt(fileExt);
    		awsObjectResult.setOrginalFileName(originalFileName);
    		awsObjectResult.setUuidFileName(fileName);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return awsObjectResult;
	} 
	
	public ResponseEntity<byte[]> download(String key) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(AWS_APP_BUCKET, key);
		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(objectInputStream);
		String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
		HttpHeaders httpHeaders = new HttpHeaders();
		
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
	
	public List<S3ObjectSummary> list() {
        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(AWS_APP_BUCKET));
		List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
		return s3ObjectSummaries;
    }
}