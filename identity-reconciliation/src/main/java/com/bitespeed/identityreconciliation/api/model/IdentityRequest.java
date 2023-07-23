package com.bitespeed.identityreconciliation.api.model;

public class IdentityRequest {
	
	private String email;
    private String phoneNumber;
    
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
