package com.codepath.app6.volunteerbeat.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.fragments.ProfileEditFragment;
import com.codepath.app6.volunteerbeat.fragments.ProfileReadonlyFragment;

public class ProfileActivity extends FragmentActivity {
	private boolean 				editMode = false;
	private	ProfileEditFragment		editFragment = null;
	private	ProfileReadonlyFragment	readonlyFragment = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		editFragment = new ProfileEditFragment();
		readonlyFragment = new ProfileReadonlyFragment();
		
		editMode = false;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, readonlyFragment);
        ft.commit();       
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.profile, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	          case R.id.action_edit:
	        	  switchEditMode();
	        	  if (editMode) {
	        		  item.setIcon(R.drawable.ic_action_save_profile);
	        	  } else {
	        		  item.setIcon(R.drawable.ic_action_edit_profile);
	        	  }
	              return true;

	          default:
	              return super.onOptionsItemSelected(item);
	      }
	  }
	
	private void switchEditMode() {
		editMode = !editMode;
		
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (editMode) {
        	ft.replace(R.id.flContainer, editFragment);   	
        } else {
        	editFragment.saveUserProfile();
        	ft.replace(R.id.flContainer, readonlyFragment); 
        }
        ft.commit();    
        
	}

}
