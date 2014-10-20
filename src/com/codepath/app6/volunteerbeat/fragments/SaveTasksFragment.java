package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.models.Task;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SaveTasksFragment extends TasksListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public ArrayList<Task> onAddTasks(ArrayList<Task> tasks) {
		ArrayList<Task> savedTasks = new ArrayList<Task>();
		for (Task t : tasks) {
			if (t.isSavedTask()) {
				savedTasks.add(t);
			}
		}
		return savedTasks;
	}

	@Override
	public void refreshTasks() {
		// TODO Auto-generated method stub
		
	}


}
