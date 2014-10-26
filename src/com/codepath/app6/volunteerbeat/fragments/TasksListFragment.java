package com.codepath.app6.volunteerbeat.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.Toast;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.activities.LoginActivity;
import com.codepath.app6.volunteerbeat.activities.OrganizationActivity;
import com.codepath.app6.volunteerbeat.activities.TaskDescriptionActivity;
import com.codepath.app6.volunteerbeat.adapters.TasksAdapter;
import com.codepath.app6.volunteerbeat.adapters.TasksAdapter.TasksAdapterListner;
import com.codepath.app6.volunteerbeat.models.Task;
import com.codepath.app6.volunteerbeat.models.UserProfile;
import com.codepath.app6.volunteerbeat.utils.MySQLiteHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TasksListFragment extends Fragment implements
		TasksAdapterListner {
	private static final int TASK_DESCRIPTION_ACTIVITY_CODE = 100;

	public abstract void refreshTasks();

	private ArrayList<Task> tasks;
	private TasksAdapter aTasks;
	private ListView lvTasks;
	private UserProfile profile;
	private MySQLiteHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tasks = new ArrayList<Task>();
		aTasks = new TasksAdapter(getActivity(), tasks, this);
		profile = UserProfile.getCurrentUser();
		dbHelper = new MySQLiteHelper(getActivity());

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

				startActivityForResult(i, TASK_DESCRIPTION_ACTIVITY_CODE);
			}

		});

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TASK_DESCRIPTION_ACTIVITY_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					long id = data.getLongExtra("taskId", -1);
					boolean volunteered = data.getBooleanExtra("volunteered", false);
					dbHelper.updateVolunteeredState(id, volunteered);
					populateData();
				}
			}
		}
	}

	public abstract void populateData();

	@Override
	public void onResume() {
		// Toast.makeText(getActivity(), "onResume fragment",
		// Toast.LENGTH_SHORT).show();
		super.onResume();
		populateData();
	}

	public ArrayList<Task> onAddTasks(ArrayList<Task> tasks) {
		return tasks;
	}

	public void populateDataByAPI(final boolean refresh) {
		String tasksUrl = "http://api.volunteerbeat.com/tasks";
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Accept", "application/vnd.volunteerbeat-v1+json");
		client.addHeader("Content-Type", "application/json");

		client.get(tasksUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				Log.d("onSuccess", "Success");
				try {
					tasks = Task.fromJsonArray(response.getJSONArray("items"));
					for (Task task : tasks) {
						if (profile.isSavedTask(task.getTaskId())) {
							task.setSavedTask(true);
						}
						if (profile.isVolunteeredTask(task.getTaskId())) {
							task.setVolunteeredTask(true);
						}
					}
					tasks = onAddTasks(tasks);

					if (refresh == true) {
						dbHelper.deleteAllTasks();
						deleteAll();
					}

					for (Task task : tasks) {
						dbHelper.createOrganizationEntry(task.getOrganization());
						dbHelper.createTaskEntry(task);
					}

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
			public void onFailure(Throwable throwable,
					org.json.JSONArray errorResponse) {
				throwable.printStackTrace();
				Toast.makeText(getActivity(),
						"Failed 2: " + throwable.toString(), Toast.LENGTH_SHORT)
						.show();

				super.onFailure(throwable, errorResponse);
			}

			@Override
			public void onFailure(Throwable throwable, String errorResponse) {
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

	public void addAll(List<Task> tasks) {
		aTasks.addAll(tasks);
	}

	// Delegate the adding to the internal adapter
	public void deleteAll() {
		aTasks.clear();
	}

	public List<Task> getAllSavedTasksFromDB() {
		return dbHelper.getAllSavedTasks();
	}

	public List<Task> getAllTimelineTasksFromDB() {
		return dbHelper.getAllTimelineTasks();
	}

	@Override
	public void onOrgLogoClick(Task task) {
		Intent i = new Intent(getActivity(), OrganizationActivity.class);
		i.putExtra("organization", task.getOrganization());
		getActivity().startActivity(i);
	}

	@Override
	public void onItemSave(Task t) {
		// Don't allow save if User is not logged in
		UserProfile p = UserProfile.getCurrentUser();
		if (!p.isLoggedIn()) {
			Intent i = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivity(i);
			return;
		}
		if (!t.isSavedTask()) {
			p.addSavedTask(t.getTaskId());
			t.setSavedTask(true);
			p.writeToPreference();
		} else {
			p.removeSavedTask(t.getTaskId());
			t.setSavedTask(false);
			p.writeToPreference();
		}
		dbHelper.updateSavedState(t.getTaskId(), t.isSavedTask());
		populateData();

		// Refresh current tab.
		// deleteAll();
		// tasks = onAddTasks(tasks);
		// addAll(tasks);
	}
}
