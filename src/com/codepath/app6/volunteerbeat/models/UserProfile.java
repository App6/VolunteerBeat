package com.codepath.app6.volunteerbeat.models;


import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserProfile {
	private static 	String	CURR_USER_ID_PROP_NAME = "CURR_USER_ID";
	private static	String	USER_PROFILE_PROP_NAME_PREFIX = "USER_PROFILE_";
	private static 	UserProfile 	currUser;
	private int 	id;
	private	String	name;
	private String	address;
	private String	email;
	private String	phone;
	private String	aboutMe;
	private String	hobbies;
	private String	photoUri;
	private boolean isLoggedIn;
	private Set<String> volunteeredTasks;

	private UserProfile() {

	}
	
	public static synchronized UserProfile getCurrentUser(Context context) {
		if (currUser == null) {
			currUser = new UserProfile();
			readFromPreference(currUser, PreferenceManager.getDefaultSharedPreferences(context));

		}
		return currUser;
	}
	
	public synchronized void resetCurrentUser(Context context) {
		readFromPreference(currUser, PreferenceManager.getDefaultSharedPreferences(context));
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

	public void addVolunteeredTasks(String taskId) {
		if (volunteeredTasks == null) {
			volunteeredTasks = new HashSet<String>();
		}
		volunteeredTasks.add(taskId);
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


	public boolean isVolunteeredTask(long taskId) {
		return isVolunteeredTask(String.valueOf(taskId));
	}

	public boolean isLoggedIn() {
		//return isLoggedIn && VolunteerBeatClient.isClientLoggedIn();
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public static void readFromPreference(UserProfile user, SharedPreferences preference) {
		int userId = user.getId();
		if (userId <=0 ) {
			userId = preference.getInt(CURR_USER_ID_PROP_NAME, -1);
			user.id = userId;
		}
		
		String jsonStr = preference.getString(getUserProfileKey(userId), "");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            user.name =  jsonObject.isNull("name")?null:jsonObject.getString("name");
            user.address = jsonObject.isNull("address")?null:jsonObject.getString("address");
            user.phone = jsonObject.isNull("phone")?null:jsonObject.getString("phone");
            user.email = jsonObject.isNull("email")?null:jsonObject.getString("email");
            user.aboutMe =jsonObject.isNull("aboutme")?null: jsonObject.getString("aboutme");
            user.hobbies = jsonObject.isNull("hobbies")?null:jsonObject.getString("hobbies");
            user.photoUri = jsonObject.isNull("photouri")?null:jsonObject.getString("photouri");
            user.isLoggedIn = jsonObject.isNull("isloggedin")?false:jsonObject.getBoolean("isloggedin");
            
            if (!jsonObject.isNull("volunteeredtasks")) {
            	String[] volunteeredtasks = jsonObject.getString("volunteeredtasks").split(",");
            	user.volunteeredTasks = new HashSet<String>();
            	for (String t : volunteeredtasks) {
            		if (t != null && !t.isEmpty()) {
            			user.volunteeredTasks.add(t);
            		}
            	}
            	
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
	

	public void writeToPreference(SharedPreferences preference) {
		Editor editor = preference.edit();
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", this.id);
			obj.put("name", this.name);
			obj.put("address", this.address);
			obj.put("phone", this.phone);
			obj.put("email", this.email);
			obj.put("aboutme", this.aboutMe);
			obj.put("hobbies", this.hobbies);
			obj.put("photouri", this.photoUri);
			obj.put("isloggedin", this.isLoggedIn);
			

			if (volunteeredTasks != null && !volunteeredTasks.isEmpty()) {
				Log.d("Write shread preference, volunteered tasks",
						volunteeredTasks.toString());
				StringBuilder strBld = new StringBuilder();
				for (String t : volunteeredTasks) {
					strBld.append(t).append(",");
				}
				obj.put("volunteeredtasks", strBld.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		editor.putString(getUserProfileKey(this.id), obj.toString());
		editor.putInt(CURR_USER_ID_PROP_NAME, this.id);
		editor.commit();
	}
	
	private static String getUserProfileKey(int userId) {
		return USER_PROFILE_PROP_NAME_PREFIX+userId;
	}
}
