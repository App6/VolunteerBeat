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
	private UserProfile profile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		profile = UserProfile.getCurrentUser(getActivity());

		populateData(false);
	}

	private void populateData(final boolean refresh) {
		String tasksUrl = "http://api.volunteerbeat.com/tasks";
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
		client.addHeader("Content-Type", "application/json");

		client.get(tasksUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				Log.d("onSuccess", "Success");
				try {
					ArrayList<Task> tasks = Task.fromJsonArray(response
							.getJSONArray("items"));

					ArrayList<Task> tasksSaved = new ArrayList<Task>();
					for (Task task : tasks) {
						if (profile.isVolunteeredTask(task.getTaskId())) {
							tasksSaved.add(task);
						}
					}

					if (refresh == true)
						deleteAll();

					addAll(tasksSaved);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				super.onSuccess(statusCode, response);
			}
			/*
			 * @Override public void onFailure( String responseString, Throwable
			 * throwable) { Log.d("onFailure", "Failed");
			 * throwable.printStackTrace(); // TODO Auto-generated method stub
			 * Toast.makeText(getApplicationContext(), "Failed 1: " +
			 * throwable.toString(), Toast.LENGTH_SHORT) .show();
			 * 
			 * super.onFailure(statusCode, headers, responseString, throwable);
			 * }
			 * 
			 * @Override public void onFailure(int statusCode, Header[] headers,
			 * Throwable throwable, JSONObject errorResponse) {
			 * throwable.printStackTrace();
			 * Toast.makeText(getApplicationContext(), "Failed 2: " +
			 * throwable.toString(), Toast.LENGTH_SHORT) .show();
			 * 
			 * super.onFailure(statusCode, headers, throwable, errorResponse); }
			 * 
			 * @Override public void onFailure(int statusCode, Header[] headers,
			 * Throwable throwable, JSONArray errorResponse) {
			 * throwable.printStackTrace();
			 * Toast.makeText(getApplicationContext(), "Failed 3: " +
			 * throwable.toString(), Toast.LENGTH_SHORT) .show();
			 * 
			 * super.onFailure(statusCode, headers, throwable, errorResponse); }
			 */
		});

	}

	@Override
	public void refreshTasks() {
		populateData(true);

	}
}
