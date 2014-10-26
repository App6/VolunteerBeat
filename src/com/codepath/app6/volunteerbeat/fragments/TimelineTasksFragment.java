package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.codepath.app6.volunteerbeat.models.Task;

public class TimelineTasksFragment extends TasksListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public ArrayList<Task> onAddTasks(ArrayList<Task> tasks) {
		ArrayList<Task> volunteeredTasks = new ArrayList<Task>();
		for (Task t : tasks) {
			if (t.isVolunteeredTask()) {
				volunteeredTasks.add(t);
			}
		}
		return volunteeredTasks;
	}

	@Override
	public void populateData() {
		List<Task> timelineTasks = getAllTimelineTasksFromDB();
		deleteAll();
		addAll(timelineTasks);
	}

	@Override
	public void refreshTasks() {

	}
}
