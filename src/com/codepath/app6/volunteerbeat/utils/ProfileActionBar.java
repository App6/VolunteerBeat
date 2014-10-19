package com.codepath.app6.volunteerbeat.utils;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.activities.HomeScreenActivity;
import com.codepath.app6.volunteerbeat.activities.LoginActivity;
import com.codepath.app6.volunteerbeat.activities.ProfileActivity;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class ProfileActionBar {
	public static final int ACTIONBAR_LOGIN_ACTIVITY_CODE =300;
	
	private ActionBarActivity activity;

	public ProfileActionBar(ActionBarActivity a) {
		activity = a;
	}

	public void show() {
		final ActionBar actionBar = activity.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_profile_view);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		
		/* Replace this with the custom Image location on disk */
		String url = "http://i.forbesimg.com/media/lists/people/lionel-messi_416x416.jpg";
		CircularImageView ivactionbarLogo = (CircularImageView) actionBar
				.getCustomView().findViewById(R.id.actionBarLogo);


		UserProfileHelper.setProfileImage(ivactionbarLogo, activity);

		ivactionbarLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Profile Click", Toast.LENGTH_SHORT)
						.show();
				showProfile();
			}
		});
	}

	public void showProfile() {
		UserProfile profile = UserProfile.getInstance(activity);
		if (profile.isLoggedIn()) {
			Intent i = new Intent(activity, ProfileActivity.class);
			i.putExtra("mode", false);
			activity.startActivity(i);
		} else {
			Intent i = new Intent(activity, LoginActivity.class);
			activity.startActivityForResult(i, ACTIONBAR_LOGIN_ACTIVITY_CODE);
		}
	}
}
