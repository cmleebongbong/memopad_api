package com.almond.util;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
	
	@Value("${auth.secret}")
	private String secret;
	
	/**
	 * SHA256 암호화
	 * 
	 * @param str
	 * @return String
	 */
	public String encryptSHA256(String str) {
	    String SHA = null;
	    try {
	        MessageDigest sh = MessageDigest.getInstance("SHA-256"); // 이 부분을 SHA-1으로 바꿔도 된다!
	        sh.update(str.getBytes());
	        byte byteData[] = sh.digest();
	        StringBuffer sb = new StringBuffer(); 
	        for(int i = 0 ; i < byteData.length ; i++){
	            sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
	        }
	        SHA = sb.toString();
	    } catch(NoSuchAlgorithmException e){
	        e.printStackTrace(); 
	    }
	    return SHA;
	}
	
	/**
	 * 20자 랜덤문자열 생성
	 * 
	 * @return String
	 */
	public String createRandomKey() throws Exception {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 20; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}
		
		return temp.toString();
	}
	
	/**
	 * GET HostName by url
	 * 
	 * @param url
	 * @return
	 */
	public String getHostName(String url) throws Exception {
	    URI uri = new URI(url);
	    String protocol = uri.getScheme();
	    String hostname = uri.getHost();
	    String host = protocol + "://" + hostname;

	    return host;
	}
}
