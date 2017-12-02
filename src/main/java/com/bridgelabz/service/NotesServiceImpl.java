package com.bridgelabz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.dao.UserNotesDao;
import com.bridgelabz.model.Collaborator;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

public class NotesServiceImpl implements NotesService {

	
	@Autowired
	private UserNotesDao userNotesDao;
	
	@Override
	public void addUserNotes(Notes notes) {
		userNotesDao.addUserNotes(notes);
	}

	@Override
	public void dalateUserNotes(int id) {
		userNotesDao.dalateUserNotes(id);
		
	}

	@Override
	public void modifiedNotes(int id, Notes note) {
		userNotesDao.modifiedNotes(id, note);
		
	}

	@Override
	public List<Notes> fetchAllNotes(User user) {
		return userNotesDao.fetchAllNotes(user);
	}

	@Override
	public Notes fetchById(int id) {
		return userNotesDao.fetchById(id);
	}

	@Override
	public void addCollaborator(Collaborator collaborate) {
		userNotesDao.addCollaborator(collaborate);
		
	}

	@Override
	public List<User> getUserList(Notes noteId) {
		// TODO Auto-generated method stub
		return userNotesDao.getUserList(noteId);
	}

	@Override
	public List<Notes> getCollaboratedNotes(User user) {
		// TODO Auto-generated method stub
		return userNotesDao.getCollaboratedNotes(user);
	}

	@Override
	public int removeUser(User userId, Notes noteId) {
		// TODO Auto-generated method stub
		return userNotesDao.removeUser(userId, noteId);
	}

}
