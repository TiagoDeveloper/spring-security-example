package com.example.springsecurity.models;

public class TokenResponse {

	private String userName;
	private String token;
	
	public TokenResponse() {
		super();
	}
	public TokenResponse(String token) {
		super();
		this.token = token;
	}
	public TokenResponse(String userName, String token) {
		super();
		this.userName = userName;
		this.token = token;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "TokenResponse [userName=" + userName + ", token=" + token + "]";
	}
}
