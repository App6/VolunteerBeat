package com.codepath.app6.volunteerbeat.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UserProfile {
	private static 	UserProfile 	instance;
	private int 	id;
	private	String	name;
	private String	address;
	private String	email;
	private String	phone;
	private String	aboutMe;
	private String	hobbies;
	private String	photoUri;
	private boolean isLoggedIn;
	
	private UserProfile() {
		
	}
	
	public static synchronized UserProfile getInstance(Context context) {
		if (instance == null) {
			instance = new UserProfile();
			instance.readFromPreference(PreferenceManager.getDefaultSharedPreferences(context));
		}
		
		return instance;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	
	
	public String getPhotoUri() {
		return photoUri;
	}
	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}
	
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public void readFromPreference(SharedPreferences preference) {
		this.id = preference.getInt("id", -1);
		this.name = preference.getString("name", "");
		this.address = preference.getString("address", "");
		this.phone = preference.getString("phone", "");
		this.email = preference.getString("email", "");
		this.aboutMe = preference.getString("aboutme", "");
		this.hobbies = preference.getString("hobbies", "");		
		this.photoUri = preference.getString("photouri", "");
		this.isLoggedIn = preference.getBoolean("isloggedin", false);
	}
	
	public void writeToPreference(SharedPreferences preference) {
		Editor editor = preference.edit();
		editor.putInt("id", this.id);
		editor.putString("name", this.name);
		editor.putString("address", this.address);
		editor.putString("phone", this.phone);
		editor.putString("email", this.email);
		editor.putString("aboutme", this.aboutMe);
		editor.putString("hobbies", this.hobbies);
		editor.putString("photouri", this.photoUri);
		editor.putBoolean("isloggedin", this.isLoggedIn);
		editor.commit();
	}
}
