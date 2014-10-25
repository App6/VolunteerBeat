package com.codepath.app6.volunteerbeat.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.fragments.ProfileEditFragment;
import com.codepath.app6.volunteerbeat.fragments.ProfileReadonlyFragment;

public class ProfileActivity extends Activity {

	public final static int PICK_PHOTO_CODE = 1046;
	private static final int REGISTER_ACTIVITY_CODE = 100;

	private boolean editMode = false;
	private ProfileEditFragment editFragment = null;
	private ProfileReadonlyFragment readonlyFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_profile);

		editFragment = new ProfileEditFragment();
		readonlyFragment = new ProfileReadonlyFragment();

		editMode = getIntent().getBooleanExtra("mode", false);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (editMode == false)
			ft.replace(R.id.flContainer, readonlyFragment);
		else
			ft.replace(R.id.flContainer, editFragment);
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

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (editMode) {
			ft.setCustomAnimations(
                    R.animator.profile_flip_right_in, R.animator.profile_flip_right_out,
                    R.animator.profile_flip_left_in, R.animator.profile_flip_left_out).replace(R.id.flContainer, editFragment);
		} else {
			editFragment.saveUserProfile();
			ft.setCustomAnimations(
                    R.animator.profile_flip_left_in, R.animator.profile_flip_left_out,
                    R.animator.profile_flip_right_in, R.animator.profile_flip_right_out).replace(R.id.flContainer, readonlyFragment);
		}
		ft.commit();

	}

	// Trigger gallery selection for a photo
	public void onPickPhoto(View view) {
		// Create intent for picking a photo from the gallery
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// Bring up gallery to select a photo
		startActivityForResult(intent, PICK_PHOTO_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_PHOTO_CODE) {
			if (resultCode == RESULT_OK) {
				if (data != null) {
					Uri photoUri = data.getData();
					this.editFragment.updateProfileImage(photoUri);
				}
			}
		} else if (requestCode == REGISTER_ACTIVITY_CODE) {
			if (resultCode == RESULT_OK) {
				if (editMode) {
					editFragment.initViews();
				} else {
					readonlyFragment.updateAll();
				}
			}
		}
	}

}
