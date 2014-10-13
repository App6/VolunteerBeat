package com.codepath.app6.volunteerbeat.adapters;

import java.util.ArrayList;

import com.codepath.app6.volunteerbeat.R;
import com.codepath.app6.volunteerbeat.models.Review;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewListAdapter extends ArrayAdapter {

	private class ViewHolder {
		TextView tvName;
		RatingBar rbRating;
		TextView tvReviewDate;
		TextView tvReviewText;
	}
	public ReviewListAdapter(Context context, ArrayList<Review> reviews) {
		super(context, 0, reviews);
		Toast.makeText(getContext(), "Review count : "+ reviews.size(), Toast.LENGTH_SHORT).show();

		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			LayoutInflater l = LayoutInflater.from(getContext());
			convertView = l.inflate(R.layout.item_review, parent, false);
			vh = new ViewHolder();
			vh.tvName = (TextView) convertView.findViewById(R.id.tvReviewUserName);
			vh.tvReviewDate = (TextView) convertView.findViewById(R.id.tvReviewdate);
			vh.tvReviewText = (TextView) convertView.findViewById(R.id.tvReviewText);
			vh.rbRating = (RatingBar) convertView.findViewById(R.id.rbReviewRating);
			convertView.setTag(vh);
		}

		vh = (ViewHolder) convertView.getTag();
		Review r = (Review) getItem(position);
		
		vh.tvName.setText(r.getUserName());
		vh.tvReviewDate.setText(r.getReviewDate());
		vh.tvReviewText.setText(r.getReviewText());
		vh.rbRating.setRating(r.getRating());
		return convertView;
	}

	
}
