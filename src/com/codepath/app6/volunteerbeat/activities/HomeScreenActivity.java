package com.codepath.app6.volunteerbeat.activities;



import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.R.layout;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
//import com.codepath.app6.volunteerbeat.activities.ProfileActivity;
import android.view.View;

import java.util.ArrayList;

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
import com.codepath.app6.volunteerbeat.models.TaskItem;
import com.codepath.app6.volunteerbeat.utils.CircularImageView;
import com.squareup.picasso.Picasso;


public class HomeScreenActivity extends ActionBarActivity {
	private ArrayList<TaskItem> tasks;
	private TasksAdapter aTasks;
	private ListView lvTasks;

	private String[][] data = new String[][] {
			// url, orgName, taskName, taskDesc, distance, dueDate, dueTime,
			// postedDate
			{
					"null",
					"VolunteerMatch",
					"Elders Need Helpers",
					"Volunteer to help elders for the activities you enjoy, on a schedule that works best for you. this is to test longer task description details and how the text view behaves for the long text",
					"0.8mi", "10/15/2014", "2pm", "10/10/2014" },
			{
					"NULL",
					"Great Non profit",
					"Administrative Support",
					"Volunteer to perform duties such as answering the phone, filing and running errands",
					"2mi", "10/20/2014", "10am", "10/05/2014" } };

	private double[][] gpsCoords = new double[][] { { 37.415423, -122.024399 },
			{ 37.402943, -122.116440 } };

	private float[] orgRatings = new float[] { (float) 4.5, 4 };

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
		tasks = new ArrayList<TaskItem>();

		aTasks = new TasksAdapter(this, tasks);

		lvTasks = (ListView) findViewById(R.id.lvTasks);

		lvTasks.setAdapter(aTasks);

		lvTasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(HomeScreenActivity.this,
						TaskDescriptionActivity.class);

				TaskItem task = tasks.get(position);

				Bundle bundle = new Bundle();
				bundle.putParcelable("taskInfo", task);
				i.putExtras(bundle);

				startActivity(i);
			}

		});

	}

	private void populateData() {
		// Hard coding for now
		// First Item
		for (int i = 0; i < data.length; i++) {
			TaskItem item = new TaskItem();
			item.setOrgImageUrl(data[i][0]);
			item.setOrgName(data[i][1]);
			item.setRating(orgRatings[i]);
			item.setTaskName(data[i][2]);
			item.setTaskShortDesc(data[i][3]);
			item.setDistance(data[i][4]);
			item.setDueDate(data[i][5]);
			item.setDueTime(data[i][6]);
			item.setPostedDate(data[i][7]);
			item.setGpsLatitude(gpsCoords[i][0]);
			item.setGpsLongitude(gpsCoords[i][1]);

			tasks.add(item);
		}

		// Some some more default data
		for (int i = 0; i < 5; i++) {
			TaskItem item = new TaskItem();
			item.setOrgImageUrl("www.chconline.org");
			item.setOrgName("Children’s Health Council");
			item.setRating((float) 4.5);
			item.setTaskName("Task Name");
			item.setTaskShortDesc("We have our annual fund raising gala at San Carlos. We need someone to pick up brochures");
			item.setDistance("4mi");
			item.setDueDate("10/20/2014");
			item.setDueTime("11am");
			item.setPostedDate("10/01/2014");
			item.setGpsLatitude(37.404661);
			item.setGpsLongitude(-121.975432);

			tasks.add(item);
		}
		aTasks.notifyDataSetChanged();
	}

}
