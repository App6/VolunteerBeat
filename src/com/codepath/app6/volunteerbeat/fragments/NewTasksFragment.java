package com.codepath.app6.volunteerbeat.fragments;

import android.os.Bundle;

public class NewTasksFragment extends TasksListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void populateData() {
		populateDataByAPI(true);
	}

	@Override
	public void refreshTasks() {
		// TODO Auto-generated method stub

	}

}
