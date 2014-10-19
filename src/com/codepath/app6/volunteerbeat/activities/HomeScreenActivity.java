package com.codepath.app6.volunteerbeat.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.fragments.NewTasksFragment;
import com.codepath.app6.volunteerbeat.fragments.SaveTasksFragment;
import com.codepath.app6.volunteerbeat.fragments.TimelineTasksFragment;
import com.codepath.app6.volunteerbeat.listeners.FragmentTabListener;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;

//import com.codepath.app6.volunteerbeat.activities.ProfileActivity;

public class HomeScreenActivity extends ActionBarActivity {

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

		setupTabs();

	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar.newTab()
				.setText("New")
				// .setIcon(R.drawable.ic_home)
				.setTag("NewTasks")
				.setTabListener(
						new FragmentTabListener<NewTasksFragment>(
								R.id.flContainer, this, "new",
								NewTasksFragment.class));
		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar.newTab()
				.setText("Save")
				// .setIcon(R.drawable.ic_mentions)
				.setTag("SaveTasks")
				.setTabListener(
						new FragmentTabListener<SaveTasksFragment>(
								R.id.flContainer, this, "save",
								SaveTasksFragment.class));

		actionBar.addTab(tab2);

		Tab tab3 = actionBar.newTab()
				.setText("Timeline")
				// .setIcon(R.drawable.ic_mentions)
				.setTag("TimelineTasks")
				.setTabListener(
						new FragmentTabListener<TimelineTasksFragment>(
								R.id.flContainer, this, "timeline",
								TimelineTasksFragment.class));

		actionBar.addTab(tab3);
	}

	public void onButtonClick(View v) {

		Intent i = new Intent(this, OrganizationActivity.class);
		startActivity(i);
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
