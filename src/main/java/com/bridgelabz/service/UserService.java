package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.User;

public interface UserService {

	public void saveUserData(User user);
	
	public boolean isUserExist(String email);
	
	public User checkUserData(String email, String password);
	
	public User retrieveById(int id);
	
	public void activateUser(int id,User user);
	
	public User getByEmail(String email);
	
	public String passwordReset(User user);
	
	public List<User> getAllEmail();
	
	public List<Label> getUserLabel(User use);
}
