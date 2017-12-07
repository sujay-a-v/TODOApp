package com.bridgelabz.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Label {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String labelName;
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonIgnore
	private User user;
	
	@ManyToMany(mappedBy="labels")
	@JsonIgnore
	private List<Notes> note;

	public int getId() {
		return id;
	}

	public String getLabelName() {
		return labelName;
	}

	public User getUser() {
		return user;
	}

	public List<Notes> getNote() {
		return note;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setNote(List<Notes> note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Label [id=" + id + ", labelName=" + labelName + ", user=" + user + ", note=" + note + "]";
	}
	
	

}
