package com.codepath.app6.volunteerbeat.activities;


import java.util.ArrayList;
import java.util.List;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.adapters.ImageListAdapter;
import com.codepath.app6.volunteerbeat.adapters.ReviewListAdapter;
import com.codepath.app6.volunteerbeat.models.Organization;
import com.codepath.app6.volunteerbeat.utils.HorizontialListView;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class OrganizationActivity extends ActionBarActivity {
		
		private ImageListAdapter mAdapter;
		private Organization org;
		
	   @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.activity_organization_details);
			ProfileActionBar aBar = new ProfileActionBar(this);
			aBar.show();
			
			// Replace this with real organization passed to you.
			org = (Organization)getIntent().getParcelableExtra("organization");
			
			TextView tvOrgName = (TextView) findViewById(R.id.tvOrgActOrgName);
			TextView tvOrgDesc = (TextView) findViewById(R.id.tvOrgActOrgDescription);
			RatingBar rbOrgRating = (RatingBar) findViewById(R.id.rbOrgActNonProfitOrgRating);
			ImageView ivOrgLogo = (ImageView) findViewById(R.id.ivOrgActOrgLogo);
			Picasso.with(getApplicationContext()).load(org.getOrgLogoUri()).into(ivOrgLogo);
			tvOrgName.setText(org.getOrgName());
			tvOrgDesc.setText(org.getOrgDescription());
			rbOrgRating.setRating(org.getOrgRating());

	        HorizontialListView listview = (HorizontialListView) findViewById(R.id.hlvImages);

	        mAdapter = new ImageListAdapter(this, org.getOrgPicUris());
	        listview.setAdapter(mAdapter);
	        
	        ListView lvReviews = (ListView) findViewById(R.id.lvOrgActReviews);
	        ReviewListAdapter rAdapter = new ReviewListAdapter(this, org.getOrgReviews());
	        lvReviews.setAdapter(rAdapter);
	        
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
