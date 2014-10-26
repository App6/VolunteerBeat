package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.codepath.app6.volunteerbeat.models.Task;

public class SaveTasksFragment extends TasksListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public ArrayList<Task> onAddTasks(ArrayList<Task> tasks) {
		ArrayList<Task> savedTasks = new ArrayList<Task>();
		for (Task t : tasks) {
			if (t.isSavedTask()) {
				savedTasks.add(t);
			}
		}
		return savedTasks;
	}

	@Override
	public void refreshTasks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateData() {
		List<Task> savedTasks = getAllSavedTasksFromDB();
		deleteAll();
		addAll(savedTasks);
	}

}
