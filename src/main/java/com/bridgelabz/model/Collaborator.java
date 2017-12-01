package com.bridgelabz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Collaborator")
public class Collaborator {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int collaboretorId;
	
	@ManyToOne
	@JoinColumn
	private User ownerId;
	
	@ManyToOne
	@JoinColumn
	private User sharedId;
	
	@ManyToOne
	@JoinColumn
	private Notes noteId;

	public int getCollaboretorId() {
		return collaboretorId;
	}

	public User getOwnerId() {
		return ownerId;
	}

	public User getSharedId() {
		return sharedId;
	}

	public Notes getNoteId() {
		return noteId;
	}

	public void setCollaboretorId(int collaboretorId) {
		this.collaboretorId = collaboretorId;
	}

	public void setOwnerId(User ownerId) {
		this.ownerId = ownerId;
	}

	public void setSharedId(User sharedId) {
		this.sharedId = sharedId;
	}

	public void setNoteId(Notes noteId) {
		this.noteId = noteId;
	}

	@Override
	public String toString() {
		return "Collaborator [collaboretorId=" + collaboretorId + ", ownerId=" + ownerId + ", sharedId=" + sharedId
				+ ", noteId=" + noteId + "]";
	}
	
	

}
