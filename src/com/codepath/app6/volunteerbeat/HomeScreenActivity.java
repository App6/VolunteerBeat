package com.codepath.app6.volunteerbeat;

import com.codepath.app6.volunteerbeat.activities.ProfileActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
    
    public void viewProfile(View v) {
    	Intent i = new Intent(this, ProfileActivity.class);
    	startActivity(i);
    }
}
