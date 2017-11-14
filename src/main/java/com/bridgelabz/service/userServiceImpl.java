package com.bridgelabz.service;



import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.User;

public class userServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public void saveUserData(User user) {
		userDao.saveUser(user);	
	}

	@Override
	public User checkUserData(String email, String password) {
		return userDao.checkUser(email, password);
	}
	
	@Override
	public boolean isUserExist(String email) {
		System.out.println("inside service impl");
		return userDao.isUserExit(email);
	}
	
	@Override
	public User retrieveById(int id) {
		return userDao.retrieveById(id);
	}

	@Override
	public void activateUser(int id, User user) {
		userDao.activateUser(id, user);
		
	}

	@Override
	public User getByEmail(String email) {
		return userDao.getByEmail(email);
	}

	@Override
	public String passwordReset(User user) {
		return userDao.passwordReset(user);
	}

}
