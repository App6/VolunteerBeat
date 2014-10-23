package com.codepath.app6.volunteerbeat.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Organization implements Parcelable {
	private String orgName;
	private String orgLogoUri;
	private float orgRating;
	private String orgDescription;
	private String orgLocation;
	private ArrayList<String> orgPicUris;
	private ArrayList<Review> orgReviews;

	public String getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getOrgLogoUri() {
		return orgLogoUri;
	}

	public float getOrgRating() {
		return orgRating;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public ArrayList<String> getOrgPicUris() {
		return orgPicUris;
	}

	public ArrayList<Review> getOrgReviews() {
		return orgReviews;
	}

	public Organization() {
		super();
		this.orgName = "San Jose Museum of Quilts & Textiles";
		this.orgLogoUri = "";
		this.orgRating = 3;
		this.orgDescription = "The Museum supports the belief that textile art transcends cultural, ethnic, age and gender boundaries and encompasses traditional as well as contemporary forms. The Museum provides a serious venue for all artists working with textiles. Its exhibits and programs promote the appreciation of quilts and textiles as art and provide an understanding of their role in the lives of their makers, in cultural traditions, and as historical documents.";
		this.orgLocation = "San Jose, CA";
		this.orgPicUris = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			orgPicUris
					.add("http://i.forbesimg.com/media/lists/people/lionel-messi_416x416.jpg");
		}

		this.orgReviews = new ArrayList<Review>();
		for (int i = 0; i < 10; i++) {
			orgReviews.add(new Review());
		}
	}

	public Organization(Parcel in) {
		super();
		this.orgName = in.readString();
		this.orgLogoUri = in.readString();
		this.orgRating = in.readFloat();
		this.orgDescription = in.readString();
		this.orgLocation = in.readString();
		orgPicUris = new ArrayList<String>();
		in.readList(orgPicUris, null);
		orgReviews = new ArrayList<Review>();
		in.readTypedList(orgReviews, Review.CREATOR);

	}

	public Organization(JSONObject json) {
		super();
		try {
			this.orgName = json.getString("title");

			this.orgLogoUri = "http://cdn.greatnonprofits.org"
					+ json.getJSONObject("displayImage").getString("thumb");
			this.orgRating = (float) json.getJSONObject("reviews").getDouble(
					"reviewAverage");
			this.orgDescription = json.getJSONObject("details").getString(
					"mission");
			this.orgLocation = json.getJSONObject("details")
					.getJSONObject("location").getString("city")
					+ ", "
					+ json.getJSONObject("details").getJSONObject("location")
							.getString("state");
			orgPicUris = new ArrayList<String>();
			JSONArray picsjson = json.getJSONObject("details")
					.getJSONObject("media").getJSONArray("images");
			for (int i = 0; i < picsjson.length(); i++) {
				orgPicUris.add("http://cdn.greatnonprofits.org"
						+ picsjson.getJSONObject(i).getString("image"));
			}

			orgReviews = Review.fromJSONArray(json.getJSONObject("reviews")
					.getJSONArray("featuredReview"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(orgName);
		dest.writeString(orgLogoUri);
		dest.writeFloat(orgRating);
		dest.writeString(orgDescription);
		dest.writeString(orgLocation);
		dest.writeList(orgPicUris);
		dest.writeTypedList(orgReviews);

	}

	public static final Parcelable.Creator<Organization> CREATOR = new Parcelable.Creator<Organization>() {
		public Organization createFromParcel(Parcel in) {
			Organization org = new Organization(in);
			return org;
		}

		public Organization[] newArray(int size) {
			return new Organization[size];
		}
	};

}
