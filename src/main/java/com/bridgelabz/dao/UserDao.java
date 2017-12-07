package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.User;

public interface UserDao {

	public void saveUser(User user);
	public boolean isUserExit(String email);
	public User checkUser(String email,String password);
	
	public User retrieveById(int id);
	
	public void activateUser(int id,User user);
	
	public User getByEmail(String email);
	
	public String passwordReset(User user);
	
	public  List<User> getAllEmail();
	
	public List<Label> getUserLabel(User use);

}
