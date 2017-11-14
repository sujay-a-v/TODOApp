package com.bridgelabz.validation;

import com.bridgelabz.model.User;

public interface UserValidation {
	
	public String userValidate(User user);
	
	public String passwordValidate(String password);

}
