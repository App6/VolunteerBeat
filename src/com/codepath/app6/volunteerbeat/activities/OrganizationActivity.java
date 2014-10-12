package com.codepath.app6.volunteerbeat.activities;


import java.util.ArrayList;
import java.util.List;

import com.codepath.app6.adapters.ImageListAdapter;
import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.utils.HorizontialListView;
import com.codepath.app6.volunteerbeat.utils.ProfileActionBar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class OrganizationActivity extends ActionBarActivity {
		
		ImageListAdapter mAdapter;
	   @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.activity_organization_details);
			ProfileActionBar aBar = new ProfileActionBar(this);
			aBar.show();
			
	        HorizontialListView listview = (HorizontialListView) findViewById(R.id.hlvImages);
//			ListView listview = (ListView) findViewById(R.id.hlvImages);
			ArrayList<String> aList = new ArrayList<String>();
			for (int i=0; i < 100; i++ ){
				aList.add("http://i.forbesimg.com/media/lists/people/lionel-messi_416x416.jpg");
			}
	        mAdapter = new ImageListAdapter(this, aList);
	        listview.setAdapter(mAdapter);
	        
	    }
}
