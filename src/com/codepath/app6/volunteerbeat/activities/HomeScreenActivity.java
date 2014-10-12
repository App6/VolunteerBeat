package com.codepath.app6.volunteerbeat.activities;



import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.R.layout;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
//import com.codepath.app6.volunteerbeat.activities.ProfileActivity;
import android.view.View;


public class HomeScreenActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		ProfileActionBar aBar = new ProfileActionBar(this);
		aBar.show();

	}
	
	public void onButtonClick(View v) {
		
		Intent i = new Intent(this, OrganizationActivity.class);
		startActivity(i);
	}
    

}
