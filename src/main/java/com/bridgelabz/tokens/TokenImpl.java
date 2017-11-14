package com.bridgelabz.tokens;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenImpl implements Token {

	String key = "createKey";
	String token = null;

	@Override
	public String generateToken(int id) {

		System.out.println("inside token generate");
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 30);
		Date expireTime = calendar.getTime();
		token = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(currentTime).setExpiration(expireTime)
				.signWith(SignatureAlgorithm.HS256, key).compact();

		System.out.println(token);
		return token;
	}

	@Override
	public int validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			return Integer.parseInt(claims.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
