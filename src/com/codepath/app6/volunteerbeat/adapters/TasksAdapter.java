package com.codepath.app6.volunteerbeat.adapters;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.activities.OrganizationActivity;
import com.codepath.app6.volunteerbeat.models.Organization;
import com.codepath.app6.volunteerbeat.models.Task;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public class TasksAdapter extends ArrayAdapter<Task> {

	public interface TasksAdapterListner {
		public abstract void onOrgLogoClick(Task t);

		public abstract void onItemSave(Task t);
	}

	private TasksAdapterListner mListner;

	public TasksAdapter(Context context, List<Task> tasks,
			TasksAdapterListner listner) {
		super(context, android.R.layout.simple_list_item_1, tasks);
		mListner = listner;
	}

	private static class ViewHolder implements Target {
		ImageView ivOrgImage;
		TextView tvNonProfitName;
		TextView tvTaskName;
		TextView tvTaskDesc;
		TextView tvDistance;
		TextView tvDueDate;
		ImageView ivSave;
		static int defaultHeight = 0;
		static int defaultWidth = 0;

		    @Override public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
		                //whatever algorithm here to compute size
				// Only set it on first Image, no need to set it afterwards.
				if (defaultHeight == 0) {
					defaultHeight = ivOrgImage.getHeight();
					defaultWidth = ivOrgImage.getWidth();
				}
		            float ratio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
//		            int targetHeight = ivOrgImage.getHeight();
		            
		            float targetWidth = ((float) defaultHeight) * ratio;

		            Log.e("ViewHolder onBitmapLoaded", "bitmap width : " + bitmap.getWidth() + " , height : " + bitmap.getHeight() + "; Img width : "
		            		+ defaultWidth + "; target width : " + targetWidth + ", height : " + defaultHeight);
		            final android.view.ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) ivOrgImage.getLayoutParams();

		            layoutParams.height = (int) defaultHeight;
		            layoutParams.width = (int) targetWidth;
		            if ((targetWidth + 30) < defaultWidth) {
		            layoutParams.leftMargin = 30;
		            }
		            ivOrgImage.setLayoutParams(layoutParams);
		            ivOrgImage.setImageBitmap(bitmap);
		   }


		    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
		    	ivOrgImage.setImageDrawable(placeHolderDrawable);
		   }

			@Override
			public void onBitmapFailed(Drawable arg0) {
		    	ivOrgImage.setImageDrawable(arg0);				
			}
		 
	}

	// Takes a data item at a position, converts it to a row in the listView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Take the data source at position
		// Get the data item
		Task task = getItem(position);
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
			// viewHolder.tvDistance = (TextView) convertView
			// .findViewById(R.id.tvDistance);
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
		// viewHolder.tvDistance.setText(task.getDistance());
		viewHolder.tvDueDate.setText("Due: " + task.getDueDate());

		refreshSaveIcon(task, viewHolder.ivSave);

		viewHolder.ivSave.setTag(task);
		viewHolder.ivSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Task task = (Task) v.getTag();
				mListner.onItemSave(task);

				refreshSaveIcon(task, viewHolder.ivSave);
			}
		});
		viewHolder.ivOrgImage.setTag(task);
		viewHolder.ivOrgImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Task task = (Task) v.getTag();
				mListner.onOrgLogoClick(task);
			}
		});



		Picasso.with(getContext()).load(task.getOrganization().getOrgLogoUri())
				.error(R.drawable.ic_launcher_vb_white).into(viewHolder);

		// Return the view for that data item
		return convertView;
	}

	private void refreshSaveIcon(Task task, ImageView ivSave) {
		if (task.isSavedTask()) {
			ivSave.setImageResource(R.drawable.ic_heart_fill_red);
		} else {
			ivSave.setImageResource(R.drawable.ic_heart_outline_grey);
		}
	}
}
