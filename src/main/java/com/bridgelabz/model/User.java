package com.bridgelabz.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ToDo_User")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	private String userName;
	
	private String userMobile;
	private String userEmail;
	private String userPassword;
	@Transient
	private String confirmPasswod;
	
	private boolean isActive;
	private boolean listView;
	
	@Lob
	@Column(name="PROFILE",columnDefinition="LONGBLOB")
	private String profile;

	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Label> labels;

	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Notes> notes;

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	
	
	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getConfirmPasswod() {
		return confirmPasswod;
	}

	public void setConfirmPasswod(String confirmPasswod) {
		this.confirmPasswod = confirmPasswod;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public boolean isListView() {
		return listView;
	}

	public void setListView(boolean listView) {
		this.listView = listView;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userMobile=" + userMobile + ", userEmail=" + userEmail
				+ ", userPassword=" + userPassword + ", confirmPasswod=" + confirmPasswod + ", isActive=" + isActive
				+ ", listView=" + listView + ", profile=" + profile + ", labels=" + labels + ", notes=" + notes + "]";
	}

	
}
