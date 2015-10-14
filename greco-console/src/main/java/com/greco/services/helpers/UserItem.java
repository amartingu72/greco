package com.greco.services.helpers;

public class UserItem {
	private int id;
	private String nickname;
	private String email;
	private String profile;
	private String mydata;
	private String password;
	
	public boolean isAdmin(){
		
		return profile.equals(ProfileItem.ADMIN_STR);
	}
	
	//GETTERs y SETTERs.
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getMydata() {
		return mydata;
	}
	public void setMydata(String mydata) {
		this.mydata = mydata;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
