package com.codepath.app6.volunteerbeat.activities;


import java.util.ArrayList;
import java.util.List;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.adapters.ImageListAdapter;
import com.codepath.app6.volunteerbeat.adapters.ReviewListAdapter;
import com.codepath.app6.volunteerbeat.models.Organization;
import com.codepath.app6.volunteerbeat.utils.HorizontialListView;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.ListView;

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
			
	        HorizontialListView listview = (HorizontialListView) findViewById(R.id.hlvImages);

	        mAdapter = new ImageListAdapter(this, org.getOrgPicUris());
	        listview.setAdapter(mAdapter);
	        
	        ListView lvReviews = (ListView) findViewById(R.id.lvOrgActReviews);
	        ReviewListAdapter rAdapter = new ReviewListAdapter(this, org.getOrgReviews());
	        lvReviews.setAdapter(rAdapter);
	        
	    }
}
