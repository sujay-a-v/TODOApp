package com.bridgelabz.validation;

import com.bridgelabz.model.User;

public class UserValidationImplement implements UserValidation {

	@Override
	public String userValidate(User user) {
		
		String error="false";
		
		String nameFormat="^[a-zA-Z]+$";
		
		String contactValidation="[a-zA-Z]";
		
		String emailFormat="[a-zA-Z0-9\\.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}$";
		
		String passwordFormat="[a-zA-Z0-9]{6,15}";
		
		String contactFormat="[0-9]{10}";
		
		if(user.getUserName()==""|| user.getUserName()==null)
		{
			error="Name can't be empty.";
			return error;
		}
		else if(user.getUserMobile()=="" || user.getUserMobile()==null )
		{
			error="Mobile number is required";
			return error;
		}
		else if(user.getUserEmail()=="" || user.getUserEmail()==null)
		{
			error="Email cannot be empty, Please enter the email.";
			return error;
		}
		else if(user.getUserPassword()=="" || user.getUserPassword()==null)
		{
			error="Password cannot be empty, please enter.";
			return error;
		}
		else if(!user.getUserName().matches(nameFormat))
		{
			error="Name contains only characters.";
			return error;
		}
		else if(!user.getUserEmail().matches(emailFormat))
		{
			error="Please enter the valid email.";
			return error;
		}
		else if(user.getUserMobile().matches(contactValidation))
		{
			error="Mobile number contains numbers only.";
			return error;
		}
		else if(!user.getUserMobile().matches(contactFormat))
		{
			error="Mobile number contains only 10 digits.";
			return error;
		}
		else if(!user.getUserPassword().matches(passwordFormat))
		{
			error="Password length between 6 and 15";
			return error;
		}
		else if(!user.getUserPassword().equals(user.getConfirmPasswod()))
		{
			error="Password mismatch";
			return error;
		}
		
		else
		{
			return "true";
		}
		
	}

	@Override
	public String passwordValidate(String password) {
		
		String passwordFormat="[a-zA-Z0-9]{6,15}";
		
		if(!password.matches(passwordFormat))
		{
			return "Password length between 6 and 15";
		}
		return "true";
	}

}
