package com.codepath.app6.volunteerbeat.activities;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.profile, menu);
	    return true;
	}
	
	public void onEditProfile(MenuItem mi) {
		     // handle click here
	}

}
