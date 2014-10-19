package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.activities.TaskDescriptionActivity;
import com.codepath.app6.volunteerbeat.adapters.TasksAdapter;
import com.codepath.app6.volunteerbeat.models.Task;

public class TasksListFragment extends Fragment {
	private ArrayList<Task> tasks;
	private TasksAdapter aTasks;
	private ListView lvTasks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tasks = new ArrayList<Task>();

		aTasks = new TasksAdapter(getActivity(), tasks);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tasks_list, container,
				false);

		lvTasks = (ListView) v.findViewById(R.id.lvTasks);

		lvTasks.setAdapter(aTasks);

		lvTasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(parent.getContext(),
						TaskDescriptionActivity.class);
				Log.d("OnItemClickListner", "Task count : " + tasks.size());
				Task task = aTasks.getItem(position);

				Bundle bundle = new Bundle();
				bundle.putParcelable("taskInfo", task);
				i.putExtras(bundle);

				startActivity(i);
			}

		});

		return v;
	}

	// Delegate the adding to the internal adapter
	public void addAll(ArrayList<Task> tasks) {
		aTasks.addAll(tasks);
	}
}
