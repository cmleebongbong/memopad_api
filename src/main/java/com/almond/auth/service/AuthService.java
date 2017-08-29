package com.almond.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
    /**
     * @param id
     * @return User
     * @throws Exception
     */
    /*public DecodedJWT authCheck(String accessToken) throws Exception {
    	Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("com.almond")
            .build();
        DecodedJWT jwt = verifier.verify(accessToken);
        
        return jwt;
    }*/
}
