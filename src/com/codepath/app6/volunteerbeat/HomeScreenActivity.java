package com.codepath.app6.volunteerbeat;


import com.codepath.app6.volunteerbeat.utils.CircularImageView;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
//import com.codepath.app6.volunteerbeat.activities.ProfileActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeScreenActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setCustomView(R.layout.action_profile_view);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		/*Replace this with the custom Image location on disk*/
		String url = "http://i.forbesimg.com/media/lists/people/lionel-messi_416x416.jpg";
		CircularImageView ivactionbarLogo = (CircularImageView) actionBar.getCustomView().findViewById(R.id.actionBarLogo);

		Picasso.with(this)
				.load(url)
//				.resize(30, 30)
				.into(ivactionbarLogo);
	
		ivactionbarLogo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeScreenActivity.this, "Profile Click", Toast.LENGTH_SHORT).show();
				showProfile();
			}
		});
	}
    
    public void showProfile() {
//   	Intent i = new Intent(this, ProfileActivity.class);
 //   	startActivity(i);  	
    }
}
