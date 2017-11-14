package com.bridgelabz.service;

import com.bridgelabz.model.User;

public interface UserService {

	public void saveUserData(User user);
	
	public boolean isUserExist(String email);
	
	public User checkUserData(String email, String password);
	
	public User retrieveById(int id);
	
	public void activateUser(int id,User user);
	
	public User getByEmail(String email);
	
	public String passwordReset(User user);
}
