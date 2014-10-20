package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.codepath.app6.volunteerbeat.models.Task;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineTasksFragment extends TasksListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public ArrayList<Task> onAddTasks(ArrayList<Task> tasks) {
		ArrayList<Task> volunteeredTasks = new ArrayList<Task>();
		for (Task t : tasks) {
			if (t.isVolunteeredTask()) {
				volunteeredTasks.add(t);
			}
		}
		return volunteeredTasks;
	}

	@Override
	public void refreshTasks() {

	}
}
