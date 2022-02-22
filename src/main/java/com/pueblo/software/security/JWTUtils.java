package com.pueblo.software.security;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {

	

	public static String createToken(Map<String, Object> claims, String subject, byte[] secret) {

		
		      return Jwts.builder()
		    		  .setClaims(claims)
		    		  .setSubject(subject)
		    		  .setIssuedAt(new Date(System.currentTimeMillis()))
		                .setExpiration(new Date(System.currentTimeMillis() + 1000 *  60 * 60 * 10))
		                .signWith(SignatureAlgorithm.HS256, secret).compact();

		

		    }


}
