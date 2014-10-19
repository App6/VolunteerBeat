package com.codepath.app6.volunteerbeat.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.activities.OrganizationActivity;
import com.codepath.app6.volunteerbeat.models.Organization;
import com.codepath.app6.volunteerbeat.models.TaskItem;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class TasksAdapter extends ArrayAdapter<TaskItem> {

	public TasksAdapter(Context context, List<TaskItem> tasks) {
		super(context, android.R.layout.simple_list_item_1, tasks);
	}

	private static class ViewHolder {
		ImageView ivOrgImage;
		TextView tvNonProfitName;
		TextView tvTaskName;
		TextView tvTaskDesc;
		TextView tvDistance;
		TextView tvDueDate;
		ImageView ivSave;
	}

	// Takes a data item at a position, converts it to a row in the listView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Take the data source at position
		// Get the data item
		TaskItem task = getItem(position);
		// Check if we are using a recycled view
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_task, parent, false);
			viewHolder.ivOrgImage = (ImageView) convertView
					.findViewById(R.id.ivOrgImage);
			viewHolder.tvNonProfitName = (TextView) convertView
					.findViewById(R.id.tvNonProfitName);
			viewHolder.tvTaskName = (TextView) convertView
					.findViewById(R.id.tvTaskName);
			viewHolder.tvTaskDesc = (TextView) convertView
					.findViewById(R.id.tvTaskDesc);
			viewHolder.tvDistance = (TextView) convertView
					.findViewById(R.id.tvDistance);
			viewHolder.tvDueDate = (TextView) convertView
					.findViewById(R.id.tvDueDate);
			viewHolder.ivSave = (ImageView) convertView
					.findViewById(R.id.ivSave);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// populate the subviews (textfiled, imageview) with the correct data
		viewHolder.tvNonProfitName.setText(task.getOrganization().getOrgName());
		viewHolder.tvTaskName.setText(task.getTaskName());
		viewHolder.tvTaskDesc.setText(task.getTaskShortDesc());
		viewHolder.tvDistance.setText(task.getDistance());
		viewHolder.tvDueDate.setText(task.getDueDate());
		
		Picasso.with(getContext()).load(task.getOrganization().getOrgLogoUri()).error(R.drawable.ic_launcher_vb_white).resize(75, 75).into(viewHolder.ivOrgImage);	


		viewHolder.ivOrgImage.setTag(task);
		viewHolder.ivOrgImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 TaskItem task = (TaskItem) v.getTag();
				 Intent i = new Intent(getContext(), OrganizationActivity.class);
				 i.putExtra("organization", task.getOrganization());
				 getContext().startActivity(i);
			}
		});
		// Return the view for that data item
		return convertView;
	}

}
