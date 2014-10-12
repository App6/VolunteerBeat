package com.codepath.app6.volunteerbeat.models;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserProfile {
	private	String	name;
	private String	address;
	private String	email;
	private String	phone;
	private String	aboutMe;
	private String	hobbies;
	
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
	
	public void readFromPreference(SharedPreferences preference) {
		this.name = preference.getString("name", "");
		this.address = preference.getString("address", "");
		this.phone = preference.getString("phone", "");
		this.email = preference.getString("email", "");
		this.aboutMe = preference.getString("aboutme", "");
		this.hobbies = preference.getString("hobbies", "");		
	}
	
	public void writeToPreference(SharedPreferences preference) {
		Editor editor = preference.edit();
		editor.putString("name", this.name);
		editor.putString("address", this.address);
		editor.putString("phone", this.phone);
		editor.putString("email", this.email);
		editor.putString("aboutme", this.aboutMe);
		editor.putString("hobbies", this.hobbies);
		editor.commit();
	}
}
