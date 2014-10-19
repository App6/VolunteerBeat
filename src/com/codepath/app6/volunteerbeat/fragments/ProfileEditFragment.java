package com.codepath.app6.volunteerbeat.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.models.UserProfile;

public class ProfileEditFragment extends Fragment {

	public final static int PICK_PHOTO_CODE = 1046;

	private UserProfile profile = new UserProfile();

	private EditText etName;
	private EditText etAddr;
	private EditText etPhone;
	private EditText etEmail;
	private EditText etAboutme;
	private EditText etHobbies;
	private ImageView ivProfileImage;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_profile_edit, container,
				false);

		initViews();

		return view;
	}

	private EditText initTextView(View view, int id, String text) {
		EditText etView = (EditText) view.findViewById(id);
		if (text != null && !text.isEmpty()) {
			etView.setText(text);
		}
		return etView;
	}

	public void saveUserProfile() {
		profile.setName(getEditText(etName));
		profile.setAddress(getEditText(etAddr));
		profile.setPhone(getEditText(etPhone));
		profile.setEmail(getEditText(etEmail));
		profile.setAboutMe(getEditText(etAboutme));
		profile.setHobbies(getEditText(etHobbies));
		// Check if any register related configuration changed?
		// register if needed
		// show whether registration went fine or not
		// preserve the register response data if any needed
		profile.writeToPreference(PreferenceManager
				.getDefaultSharedPreferences(getActivity()));
	}

	private String getEditText(EditText etView) {
		if (etView.getText() != null) {
			return etView.getText().toString();
		}
		return null;
	}

	public void updateProfileImage(Uri photoUri) {
		profile.setPhotoUri(photoUri.toString());
		ProfileReadonlyFragment.setProfileImage(ivProfileImage,
				photoUri.toString(), getActivity().getContentResolver());
	}

	public void initViews() {
		profile.readFromPreference(PreferenceManager
				.getDefaultSharedPreferences(getActivity()));

		etName = initTextView(view, R.id.tvName, profile.getName());
		etAddr = initTextView(view, R.id.tvAddr, profile.getAddress());
		etEmail = initTextView(view, R.id.tvEmail, profile.getEmail());
		etPhone = initTextView(view, R.id.tvPhone, profile.getPhone());
		etAboutme = initTextView(view, R.id.tvAboutMe, profile.getAboutMe());
		etHobbies = initTextView(view, R.id.tvHobbies, profile.getHobbies());

		ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
		ProfileReadonlyFragment.setProfileImage(ivProfileImage,
				profile.getPhotoUri(), getActivity().getContentResolver());
	}

}
