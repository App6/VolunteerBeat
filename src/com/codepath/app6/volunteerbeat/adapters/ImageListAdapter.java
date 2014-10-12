package com.codepath.app6.volunteerbeat.adapters;

import java.util.ArrayList;

import com.codepath.app6.volunteerbeat.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageListAdapter extends ArrayAdapter {

	public ImageListAdapter(Context context, ArrayList<String> imageUrls) {
		super(context, 0, imageUrls);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView ivImage;
		if (convertView == null) {
			LayoutInflater l = LayoutInflater.from(getContext());
			convertView = l.inflate(R.layout.image_item, parent, false);
			ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
			convertView.setTag(ivImage);
		}

		ivImage = (ImageView) convertView.getTag();
		String url = (String) getItem(position);
		Picasso.with(getContext()).load(url).into(ivImage);
		Toast.makeText(getContext(), "Loaded Messi", Toast.LENGTH_SHORT).show();
		return convertView;
	}

	
}
