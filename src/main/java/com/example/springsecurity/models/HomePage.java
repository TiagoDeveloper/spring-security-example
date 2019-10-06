package com.example.springsecurity.models;

import java.io.Serializable;

public class HomePage implements Serializable {
	
	private static final long serialVersionUID = -2731698724103045563L;

	private String apresentation;
	private String additionalInformation;
	
	public HomePage() {
		super();
	}
	public HomePage(String apresentation, String additionalInformation) {
		super();
		this.apresentation = apresentation;
		this.additionalInformation = additionalInformation;
	}
	public String getApresentation() {
		return apresentation;
	}
	public void setApresentation(String apresentation) {
		this.apresentation = apresentation;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

}
