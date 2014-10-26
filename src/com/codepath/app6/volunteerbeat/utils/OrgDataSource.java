package com.codepath.app6.volunteerbeat.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

import com.codepath.app6.volunteerbeat.models.Organization;
import com.codepath.app6.volunteerbeat.models.Review;

public class OrgDataSource {
	public static final String TABLE_ORGS = "Orgs";

	private static final String KEY_ID = "_id";
	public static final String COLUMN_ORG_ID = "oid";
	private static final String COLUMN_ORG_NAME = "name";
	private static final String COLUMN_ORG_LOGOURI = "logo_uri";
	private static final String COLUMN_ORG_RATING = "rating";
	private static final String COLUMN_ORG_DESC = "desc";
	private static final String COLUMN_ORG_LOCATION = "location";
	private static final String COLUMN_ORG_PIC_URIS = "orgPicUris";
	private static final String COLUMN_ORG_REVIEWS = "orgReviews";

	// Table Create Statement
	public static final String DATABASE_CREATE = "create table " + TABLE_ORGS
			+ "(" + KEY_ID + " integer primary key autoincrement, "
			+ COLUMN_ORG_ID + " integer, " + COLUMN_ORG_NAME + " text, "
			+ COLUMN_ORG_LOGOURI + " text, " + COLUMN_ORG_RATING + " real, "
			+ COLUMN_ORG_DESC + " integer, " + COLUMN_ORG_LOCATION + " text, "
			+ COLUMN_ORG_PIC_URIS + " text, " + COLUMN_ORG_REVIEWS + " text);";

	public static ContentValues populateValues(Organization org) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ORG_ID, org.getOrgId());
		values.put(COLUMN_ORG_NAME, org.getOrgName());
		values.put(COLUMN_ORG_LOGOURI, org.getOrgLogoUri());
		values.put(COLUMN_ORG_RATING, org.getOrgRating());
		values.put(COLUMN_ORG_DESC, org.getOrgDescription());
		values.put(COLUMN_ORG_LOCATION, org.getOrgLocation());

		JSONObject obj = new JSONObject();
		try {
			obj.put("picUris", new JSONArray(org.getOrgPicUris()));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String arrayList = obj.toString();
		values.put(COLUMN_ORG_PIC_URIS, arrayList);

		JSONArray ja = new JSONArray();
		for (Review r : org.getOrgReviews()) {
			JSONObject json = new JSONObject();
			try {
				json.put("userName", r.getUserName());
				json.put("rating", r.getRating());
				json.put("reviewDate", r.getReviewDate());
				json.put("reviewText", r.getReviewText());
				json.put("userImgUri", r.getUserImgUri());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ja.put(json);
		}
		String reviewArrayList = ja.toString();
		values.put(COLUMN_ORG_REVIEWS, reviewArrayList);

		return values;
	}

	public static Organization cursorToOrg(Cursor c) {
		Organization org = new Organization();

		org.setOrgId(c.getLong(c.getColumnIndex(COLUMN_ORG_ID)));
		org.setOrgName(c.getString(c.getColumnIndex(COLUMN_ORG_NAME)));
		org.setOrgLogoUri(c.getString(c.getColumnIndex(COLUMN_ORG_LOGOURI)));
		org.setOrgRating(c.getFloat(c.getColumnIndex(COLUMN_ORG_RATING)));
		org.setOrgDescription(c.getString(c.getColumnIndex(COLUMN_ORG_DESC)));
		org.setOrgLocation(c.getString(c.getColumnIndex(COLUMN_ORG_LOCATION)));

		String picUris = c.getString(c.getColumnIndex(COLUMN_ORG_PIC_URIS));
		ArrayList<String> orgPicUris = new ArrayList<String>();
		JSONArray ja;
		try {
			ja = new JSONArray(picUris);
			for (int i = 0; i < ja.length(); i++) {
				String s = ja.getJSONObject(i).toString();
				orgPicUris.add(s);
			}
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		org.setOrgPicUris(orgPicUris);

		String reviews = c.getString(c.getColumnIndex(COLUMN_ORG_REVIEWS));
		ArrayList<Review> orgReviews = new ArrayList<Review>();
		JSONArray jArray;
		try {
			jArray = new JSONArray(reviews);
			orgReviews = Review.fromJSONArray(jArray);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		org.setOrgReviews(orgReviews);

		return org;
	}

}
