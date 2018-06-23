package com.almond.api.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.almond.api.image.service.ImageService;
import com.almond.common.domain.CommonResponse;
import com.almond.common.domain.File;
import com.almond.util.awsS3.AWSObjectResult;
import com.almond.util.awsS3.AWSWrapper;

import io.swagger.annotations.Api;

@SpringBootApplication
@Api(tags = "Image")
@RestController
@RequestMapping(value="/api/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	@Value("${aws.app.key}")
	private String appKey;
	
	@Value("${aws.app.secret}")
	private String appSecret;
	
	@Value("${aws.app.bucket}")
	private String bucket;
	

    /**
     * 이미지 to Byte Array
     * 
     * @param url
     * @return byte[]
     * @throws Exception
     */
    @RequestMapping(value="", method=RequestMethod.GET)
    public byte[] imageToByte(
    		@RequestParam String url) throws Exception {
    	
//    	byte[] imageData = imageService.imageToByte(url);
    	byte[] imageData = imageService.imageToParseredByte(url);
    	return imageData;
    }
    
    /**
     * 이미지 url로 가져와서 저장
     * 
     * @param url
     * @return ResponseEntity<CommonResponse>
     * @throws Exception
     */
    @RequestMapping(value="/url/upload", method=RequestMethod.POST)
    public ResponseEntity<CommonResponse> urlUpload(
    	   @RequestParam String url) throws Exception {
    	CommonResponse res = new CommonResponse();

    	AWSWrapper awsWrapper = new AWSWrapper(appKey, appSecret, bucket);
		AWSObjectResult result = awsWrapper.uploadByUrl(url);

		File file = new File();

		file.setAwsUploadName(result.getUuidFileName());
		file.setFileName(result.getOrginalFileName());
		file.setFileSize(result.getFileSize());
		file.setExtension(result.getFileExt());
		file.setUrl("https://image.almondbongbong.com/" + result.getUuidFileName());
		
//		return fileService.uploadFile(file);

		// 파일 업로드 디비처리
//		List<File> files = list.stream().map(x-> {
//			File file = new File();
//
//			file.setAwsUploadName(x.getUuidFileName());
//			file.setFileName(x.getOrginalFileName());
//			file.setFileSize(x.getFileSize());
//
//			return fileService.uploadFile(file);
//		}).collect(Collectors.toList());

		res.setMessage("업로드 성공");
		res.setData(file);

    	return new ResponseEntity<CommonResponse>(res, HttpStatus.OK);
    }
}
