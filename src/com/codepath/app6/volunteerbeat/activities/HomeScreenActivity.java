package com.codepath.app6.volunteerbeat.activities;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.R.layout;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
//import com.codepath.app6.volunteerbeat.activities.ProfileActivity;
import android.view.View;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.activities.ProfileActivity;
import com.codepath.app6.volunteerbeat.activities.TaskDescriptionActivity;
import com.codepath.app6.volunteerbeat.adapters.TasksAdapter;
import com.codepath.app6.volunteerbeat.models.Task;
import com.codepath.app6.volunteerbeat.utils.CircularImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.squareup.picasso.Picasso;

public class HomeScreenActivity extends ActionBarActivity {
	private ArrayList<Task> tasks;
	private TasksAdapter aTasks;
	private ListView lvTasks;

	/*
	 * private String[][] data = new String[][] { // url, orgName, taskName,
	 * taskDesc, distance, dueDate, dueTime, // postedDate { "null",
	 * "VolunteerMatch", "Elders Need Helpers",
	 * "Volunteer to help elders for the activities you enjoy, on a schedule that works best for you. this is to test longer task description details and how the text view behaves for the long text"
	 * , "0.8mi", "10/15/2014", "2pm", "10/10/2014" }, { "NULL",
	 * "Great Non profit", "Administrative Support",
	 * "Volunteer to perform duties such as answering the phone, filing and running errands"
	 * , "2mi", "10/20/2014", "10am", "10/05/2014" } };
	 * 
	 * private double[][] gpsCoords = new double[][] { { 37.415423, -122.024399
	 * }, { 37.402943, -122.116440 } };
	 * 
	 * private float[] orgRatings = new float[] { (float) 4.5, 4 };
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		ProfileActionBar aBar = new ProfileActionBar(this);
		aBar.show();

		setUpRefrences();

		populateData();

	}

	public void onButtonClick(View v) {

		Intent i = new Intent(this, OrganizationActivity.class);
		startActivity(i);
	}

	private void setUpRefrences() {
		tasks = new ArrayList<Task>();

		aTasks = new TasksAdapter(this, tasks);

		lvTasks = (ListView) findViewById(R.id.lvTasks);

		lvTasks.setAdapter(aTasks);

		lvTasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(HomeScreenActivity.this,
						TaskDescriptionActivity.class);
				Log.d("OnItemClickListner", "Task count : " + tasks.size());
				Task task = aTasks.getItem(position);

				Bundle bundle = new Bundle();
				bundle.putParcelable("taskInfo", task);
				i.putExtras(bundle);

				startActivity(i);
			}

		});

	}

	private void populateData() {
		String tasksUrl = "http://api.volunteerbeat.com/tasks";
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
		client.addHeader("Content-Type", "application/json");
		
		client.get(tasksUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, 
					JSONObject response) {
				Log.d("onSuccess", "Success");
				try {
					aTasks.addAll(Task.fromJsonArray(response
							.getJSONArray("items")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				super.onSuccess(statusCode, response);
			}
/*
			@Override
			public void onFailure(
					String responseString, Throwable throwable) {
				Log.d("onFailure", "Failed");
				throwable.printStackTrace();
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Failed 1: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				throwable.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Failed 2: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				throwable.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Failed 3: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
*/
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case ProfileActionBar.ACTIONBAR_LOGIN_ACTIVITY_CODE:
			switch (resultCode) {
			case Activity.RESULT_OK:
				ProfileActionBar aBar = new ProfileActionBar(this);
				aBar.showProfile();
				break;
			}
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		ProfileActionBar aBar = new ProfileActionBar(this);
		aBar.show();
	}

}
