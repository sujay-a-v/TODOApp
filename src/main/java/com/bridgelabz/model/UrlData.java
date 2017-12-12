package com.bridgelabz.model;

public class UrlData {
	
	String title;
	String imageURL;
	String domain;
	public UrlData(String title, String imageURL, String domain) {
		super();
		this.title = title;
		this.imageURL = imageURL;
		this.domain = domain;
	}
	public String getTitle() {
		return title;
	}
	public String getImageURL() {
		return imageURL;
	}
	public String getDomain() {
		return domain;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	
}
