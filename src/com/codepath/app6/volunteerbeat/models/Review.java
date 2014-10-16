package com.codepath.app6.volunteerbeat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable{
	private String userName;
	private float  rating;
	private String reviewDate;
	private String reviewText;
	private String userImgUri;
	public String getUserName() {
		return userName;
	}
	public float getRating() {
		return rating;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public String getReviewText() {
		return reviewText;
	}
	public String getUserImgUri() {
		return userImgUri;
	}
	
	public Review() {
		super();
		this.userName = "sador P.";
		this.rating = 3;
		this.reviewDate = "01/30/13";
		this.reviewText = "Under former executive director Jane Przybysz, this museum was passionate and eclectic. The new E.D., Christine Jeffers, seems more about money than art, and it SJMQT has lost a bit of its soul. The passionate volunteers keep the place running, but it seems like there's never anyone in there. This would be a good destination museum in a suburban location, but a big downtown facility for quilt enthusiasts seems a waste of a key location, where a big draw, truly urban arts group could make a home. Just doesn't seem urban enough for where San Jose is going. Occasionally, an interesting and edgy exhibit works its way into the stitchery.";
		this.userImgUri = "";
	}
	
	public Review(Parcel in) {
		super();
		this.userName = in.readString();
		this.rating = in.readFloat();
		this.reviewDate = in.readString();
		this.reviewText = in.readString();
		this.userImgUri = in.readString();		
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(userName);
		dest.writeFloat(rating);
		dest.writeString(reviewDate);
		dest.writeString(reviewText);
		dest.writeString(userImgUri);
	}

	public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
		public Review createFromParcel(Parcel in) {
			Review review = new Review(in);
			return review;
		}

		public Review[] newArray(int size) {
			return new Review[size];
		}
	};
	
}

