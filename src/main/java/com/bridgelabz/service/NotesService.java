package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.model.Collaborator;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

public interface NotesService {
	
	public void addUserNotes(Notes notes);
	
	public void dalateUserNotes(int id);
	
	public void modifiedNotes(int id,Notes note);
	
	public List<Notes> fetchAllNotes(User user);
	public Notes fetchById(int id);
	
	
	/******* Many to many for collaborator **********/
	public void addCollaborator(Collaborator collaborate);
	
	public List<User> getUserList(int noteId);
	
	public List<Notes> getCollaboratedNotes(int userId);
	
	public int removeUser(int userId,int noteId);

}
