package com.bridgelabz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TodoNotes")
public class Notes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Date createDate;
	private Date modifiedDate;
	private String title;
	private String description;
	
	@Column(name = "PIN")
	private String pin;
	
	@Column(name="ARCHIVE_STATUS")
	private String archiveStatus;
	
	@Column(name="DELETE_STATUS")
	private String deleteStatus;
	
	@Column(name="REMINDER_STATUS")
	private String reminderStatus;
	
	@Column(name = "NOTE_STATUS")
	private String noteStatus;
	
	@Column(name="NOTE_COLOR")
	private String noteColor;
	
	public String getPin() {
		return pin;
	}

	public String getArchiveStatus() {
		return archiveStatus;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public String getReminderStatus() {
		return reminderStatus;
	}

	public String getNoteStatus() {
		return noteStatus;
	}

	public String getNoteColor() {
		return noteColor;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setArchiveStatus(String archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public void setReminderStatus(String reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="userId")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
