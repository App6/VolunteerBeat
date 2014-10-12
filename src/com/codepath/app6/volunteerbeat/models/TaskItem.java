package com.codepath.app6.volunteerbeat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskItem implements Parcelable {
	private String orgImageUrl;
	private String orgName;
	private float rating;
	private String taskName;
	private String taskShortDesc;
	private String distance;
	private String dueDate;
	private String dueTime;
	private String postedDate;
	private double gpsLatitude;
	private double gpsLongitude;


	public TaskItem() {

	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	public void setOrgImageUrl(String orgImageUrl) {
		this.orgImageUrl = orgImageUrl;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskShortDesc(String taskShortDesc) {
		this.taskShortDesc = taskShortDesc;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getOrgImageUrl() {
		return orgImageUrl;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskShortDesc() {
		return taskShortDesc;
	}

	public String getDistance() {
		return distance;
	}

	public String getDueDate() {
		return dueDate;
	}

	public double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public double getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(orgImageUrl);
		dest.writeString(orgName);
		dest.writeFloat(rating);
		dest.writeString(taskName);
		dest.writeString(taskShortDesc);
		dest.writeString(distance);
		dest.writeString(dueDate);
		dest.writeString(dueTime);
		dest.writeString(postedDate);
		dest.writeDouble(gpsLatitude);
		dest.writeDouble(gpsLongitude);
	}

	public static final Parcelable.Creator<TaskItem> CREATOR = new Parcelable.Creator<TaskItem>() {
		public TaskItem createFromParcel(Parcel in) {
			TaskItem task = new TaskItem();
			task.setOrgImageUrl(in.readString());
			task.setOrgName(in.readString());
			task.setRating(in.readFloat());
			task.setTaskName(in.readString());
			task.setTaskShortDesc(in.readString());
			task.setDistance(in.readString());
			task.setDueDate(in.readString());
			task.setDueTime(in.readString());
			task.setPostedDate(in.readString());
			task.setGpsLatitude(in.readDouble());
			task.setGpsLongitude(in.readDouble());

			return task;
		}

		public TaskItem[] newArray(int size) {
			return new TaskItem[size];
		}
	};
}
