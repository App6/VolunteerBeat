package com.codepath.app6.volunteerbeat.models;

import java.util.ArrayList;

import com.codepath.app6.volunteerbeat.R;

public class Organization {
	private String orgName;
	private String orgLogoUri;
	private float	orgRating;
	private	String orgDescription;
	private ArrayList<String> orgPicUris;
	private ArrayList<Review> orgReviews;
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
		this.orgPicUris = new ArrayList<String>();
		for (int i=0; i < 10; i++ ){
			orgPicUris.add("http://i.forbesimg.com/media/lists/people/lionel-messi_416x416.jpg");
		}
		
		this.orgReviews = new ArrayList<Review>();
		for (int i=0; i < 10; i++ ){
			orgReviews.add(new Review());
		}		
	}
	
	
}
