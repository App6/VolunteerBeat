package com.codepath.app6.volunteerbeat.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.models.UserProfile;

public class ProfileReadonlyFragment extends Fragment {
	
	private UserProfile profile = new UserProfile();
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.fragment_profile_readonly, container, false);
		profile.readFromPreference(PreferenceManager.getDefaultSharedPreferences(getActivity()));
    	
		setTextView(view, R.id.tvName, profile.getName());
		setTextView(view, R.id.tvAddr, profile.getAddress());
		setTextView(view, R.id.tvEmail, profile.getEmail());
		setTextView(view, R.id.tvPhone, profile.getPhone());
		setTextView(view, R.id.tvAboutMe, profile.getAboutMe());
		setTextView(view, R.id.tvHobbies, profile.getHobbies());

		return view;
    }
	
	
	private void setTextView(View view, int id, String text) {
		if (text != null && !text.isEmpty()) {
			((TextView)view.findViewById(id)).setText(text);	
		}
	}
}
