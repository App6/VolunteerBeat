package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.activities.OrganizationActivity;
import com.codepath.app6.volunteerbeat.activities.TaskDescriptionActivity;
import com.codepath.app6.volunteerbeat.adapters.TasksAdapter;
import com.codepath.app6.volunteerbeat.adapters.TasksAdapter.TasksAdapterListner;
import com.codepath.app6.volunteerbeat.models.Task;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TasksListFragment extends Fragment implements TasksAdapterListner {

	public abstract void refreshTasks();

	private ArrayList<Task> tasks;
	private TasksAdapter aTasks;
	private ListView lvTasks;
	private UserProfile profile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tasks = new ArrayList<Task>();
		aTasks = new TasksAdapter(getActivity(), tasks, this);
		profile = UserProfile.getCurrentUser(getActivity());

		populateData(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tasks_list, container,
				false);

		lvTasks = (ListView) v.findViewById(R.id.lvTasks);

		lvTasks.setAdapter(aTasks);

		refreshTasks();

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

	public ArrayList<Task> onAddTasks(ArrayList<Task> tasks) {
		return tasks;
	}
	
	private void populateData(final boolean refresh) {
		String tasksUrl = "http://api.volunteerbeat.com/tasks";
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
		client.addHeader("Content-Type", "application/json");

		client.get(tasksUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				Log.d("onSuccess", "Success");
				try {
					ArrayList<Task> tasks = Task.fromJsonArray(response
							.getJSONArray("items"));
					for (Task task : tasks) {
						if (profile.isSavedTask(task.getTaskId())) {
							task.setSavedTask(true);
						}
						if (profile.isVolunteeredTask(task.getTaskId())) {
							task.setVolunteeredTask(true);
						}
					}
					tasks = onAddTasks(tasks);

					if (refresh == true)
						deleteAll();

					addAll(tasks);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				super.onSuccess(statusCode, response);
			}

			@Override
			public void onFailure(Throwable throwable, JSONObject errorResponse) {
				throwable.printStackTrace();
				Toast.makeText(getActivity(),
						"Failed 2: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(throwable, errorResponse);
			}
			
			@Override
			 public void onFailure(Throwable throwable, org.json.JSONArray errorResponse){
				throwable.printStackTrace();
				Toast.makeText(getActivity(),
						"Failed 2: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(throwable, errorResponse);				
			}
			
			@Override
			 public void onFailure(Throwable throwable, String errorResponse){
				throwable.printStackTrace();
				Toast.makeText(getActivity(),
						"Failed 2: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(throwable, errorResponse);				
			}

		});

	}
	// Delegate the adding to the internal adapter
	public void addAll(ArrayList<Task> tasks) {
		aTasks.addAll(tasks);
	}

	// Delegate the adding to the internal adapter
	public void deleteAll() {
		aTasks.clear();
	}
	
	@Override
	public void onOrgLogoClick(Task task) {
		 Intent i = new Intent(getActivity(), OrganizationActivity.class);
		 i.putExtra("organization", task.getOrganization());
		 getActivity().startActivity(i);
	}

	@Override
	public void onItemSave(Task t) {
		UserProfile p = UserProfile.getCurrentUser(getActivity());
		p.addSavedTask(t.getTaskId());
		p.writeToPreference();
	}
}
