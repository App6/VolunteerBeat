package com.codepath.app6.volunteerbeat.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserProfile {
	private String name;
	private String address;
	private String email;
	private String phone;
	private String aboutMe;
	private String hobbies;
	private String photoUri;
	private int id;
	private Set<String> volunteeredTasks;

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

	public void addVolunteeredTasks(String taskId) {
		if (volunteeredTasks == null) {
			volunteeredTasks = new HashSet<String>();
			volunteeredTasks.add(taskId);
		}
	}

	public boolean isVolunteeredTask(String taskId) {
		boolean found = false;
		if (volunteeredTasks != null) {
			Log.d("isVolunteeredTask", volunteeredTasks.toString() + " : "
					+ taskId);
			for (String s : volunteeredTasks) {
				found = s.equalsIgnoreCase(taskId);
				if (found) {
					break;
				}
			}
		}
		return found;
	}

	public boolean isVolunteerdTask(int taskId) {
		return isVolunteeredTask(String.valueOf(taskId));
	}

	public static UserProfile getProfile(Context c) {
		UserProfile p = new UserProfile();
		p.readFromPreference(PreferenceManager.getDefaultSharedPreferences(c));
		return p;
	}

	public void readFromPreference(SharedPreferences preference) {
		this.name = preference.getString("name", "");
		this.address = preference.getString("address", "");
		this.phone = preference.getString("phone", "");
		this.email = preference.getString("email", "");
		this.aboutMe = preference.getString("aboutme", "");
		this.hobbies = preference.getString("hobbies", "");
		this.photoUri = preference.getString("photouri", "");
		volunteeredTasks = preference.getStringSet("volunteeredTasks", null);
		if (volunteeredTasks != null) {
			Log.d("Read shread preference, volunteered tasks",
					volunteeredTasks.toString());
		}
	}

	public void writeToPreference(SharedPreferences preference) {
		Editor editor = preference.edit();
		editor.putString("name", this.name);
		editor.putString("address", this.address);
		editor.putString("phone", this.phone);
		editor.putString("email", this.email);
		editor.putString("aboutme", this.aboutMe);
		editor.putString("hobbies", this.hobbies);
		editor.putString("photouri", this.photoUri);
		if (volunteeredTasks != null) {
			Log.d("Write shread preference, volunteered tasks",
					volunteeredTasks.toString());
		}
		editor.putStringSet("volunteeredTasks", volunteeredTasks);
		editor.commit();
	}
}
