package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.model.Collaborator;
import com.bridgelabz.model.Label;
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
	
	public List<User> getUserList(Notes noteId);
	
	public List<Notes> getCollaboratedNotes(User user);
	
	public int removeUser(User userId,Notes noteId);
	
	
	/***** Label program ***********/
	public void addNewLabel(Label label);
	
	public void deleteUserLabel(Label label);

}
