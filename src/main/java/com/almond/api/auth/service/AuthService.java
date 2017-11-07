package com.almond.api.auth.service;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.almond.api.user.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class AuthService {
	
	@Value("${auth.secret}")
	private String secret;
	
	
	/**
	 * Auth Token 생성
	 * 
	 * @param user
	 * @return token
	 * @throws Exception
	 */
	public String createToken(User user) throws Exception {
	    Date expirationDate = Date.from(ZonedDateTime.now().plusDays(7).toInstant());
		
		Algorithm algorithm = Algorithm.HMAC256(secret);
		String token = JWT.create()
				.withSubject("user")
				.withIssuer("com.almond")
                .withExpiresAt(expirationDate)
				.withClaim("idx", user.getIdx())
				.withClaim("id", user.getId())
				.sign(algorithm);
		
		return token;
	}
	
    /**
     * Auth Token 체크
     * 
     * @param id
     * @return User
     * @throws Exception
     */
    public DecodedJWT tokenCheck(String accessToken) throws Exception {
    	Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("com.almond")
            .build();
        DecodedJWT jwt = verifier.verify(accessToken);
        
        return jwt;
    }
    
    /**
     * 유저 idx 조회 by accessToken
     * 
     * @param accessToken
     * @return userIdx
     * @throws Exception
     */
    public int getUserIdxByToken(String accessToken) throws Exception {
    	if (accessToken == null) {
    		return -1;
    	}
    	try {
    		DecodedJWT jwt = tokenCheck(accessToken);
    		Claim idx = jwt.getClaim("idx");
    		return idx.asInt();
    	} catch (Exception exception) {
    		return -1;
    	}
    }
}
