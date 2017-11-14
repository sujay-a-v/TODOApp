package com.bridgelabz.tokens;

public interface Token {

	public String generateToken(int id);
	
	public int validateToken(String token);
	
}
