package com.almond.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class AuthService {

	@Value("${auth.secret}") String secret;
	
    /**
     * @param id
     * @return User
     * @throws Exception
     */
    public DecodedJWT authCheck(String accessToken) throws Exception {
    	Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("com.almond")
            .build();
        DecodedJWT jwt = verifier.verify(accessToken);
        
        return jwt;
    }
}
