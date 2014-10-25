package com.codepath.app6.volunteerbeat.fragments;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.codepath.app6.volunteerbeat.utils.UserProfileHelper;

public class ProfileReadonlyFragment extends Fragment {

	private UserProfile profile = null;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_profile_readonly, container,
				false);

		updateAll();

		return view;
	}

	private void setTextView(View view, int id, String text) {
		TextView tv = (TextView) view.findViewById(id);
		if (text != null && !text.isEmpty()) {
			tv.setText(text);
		} else {
			tv.setTextColor(Color.LTGRAY);
		}
	}

	public static void setProfileImage(ImageView ivPhoto, String uriStr,
			ContentResolver contentResolver) {
		if (uriStr == null || uriStr.isEmpty()) {
			ivPhoto.setImageResource(R.drawable.default_photo);
		} else {
			try {
				Uri photoUri = Uri.parse(uriStr);
				Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
						contentResolver, photoUri);
				// Load the selected image into a preview
				ivPhoto.setImageBitmap(selectedImage);
			} catch (Exception e) {
				ivPhoto.setImageResource(R.drawable.default_photo);
			}
		}

	}

	public void updateAll() {
		profile = UserProfile.getCurrentUser();

		setTextView(view, R.id.tvName, profile.getName());
		setTextView(view, R.id.tvAddr, profile.getAddress());
		setTextView(view, R.id.tvEmail, profile.getEmail());
		setTextView(view, R.id.tvPhone, profile.getPhone());
		setTextView(view, R.id.tvAboutMe, profile.getAboutMe());

		ImageView ivProfileImage = (ImageView) view
				.findViewById(R.id.ivProfileImage);

		UserProfileHelper.setProfileImage(ivProfileImage, getActivity());
	}
}
