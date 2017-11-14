package com.bridgelabz.passwordencrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypt {

	public String encrypt(String password) {
		BCryptPasswordEncoder bcryptPasswordEncoder=new BCryptPasswordEncoder();
		String encryptPassword=bcryptPasswordEncoder.encode(password);
		return encryptPassword;
	}
}
