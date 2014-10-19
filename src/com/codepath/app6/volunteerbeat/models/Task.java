package com.codepath.app6.volunteerbeat.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

	private String taskName;
	private long taskId;
	private String taskStatus;
	private int peopleNeeded;
	private String taskShortDesc;
	private int duration;

	// Todo : Add category.

	private String distance;
	private String dueDate;
	private String dueTime;
	private String postedDate;
	private double gpsLatitude;
	private double gpsLongitude;
	private Organization organization;

	public Task() {
		organization = new Organization();
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public void setPeopleNeeded(int peopleNeeded) {
		this.peopleNeeded = peopleNeeded;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public void setOrganization(Organization org) {
		organization = org;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(taskName);
		dest.writeLong(taskId);
		dest.writeString(taskStatus);
		dest.writeInt(peopleNeeded);
		dest.writeString(taskShortDesc);
		dest.writeInt(duration);

		dest.writeString(distance);
		dest.writeString(dueDate);
		dest.writeString(dueTime);
		dest.writeString(postedDate);
		dest.writeDouble(gpsLatitude);
		dest.writeDouble(gpsLongitude);
		dest.writeParcelable(organization, flags);
	}

	public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
		public Task createFromParcel(Parcel in) {
			Task task = new Task(in);
			return task;
		}

		public Task[] newArray(int size) {
			return new Task[size];
		}
	};

	public Task(Parcel in) {
		setTaskName(in.readString());
		taskId = in.readLong();
		taskStatus = in.readString();
		peopleNeeded = in.readInt();
		setTaskShortDesc(in.readString());
		duration = in.readInt();

		setDistance(in.readString());
		setDueDate(in.readString());
		setDueTime(in.readString());
		setPostedDate(in.readString());
		setGpsLatitude(in.readDouble());
		setGpsLongitude(in.readDouble());
		setOrganization((Organization) in.readParcelable(Organization.class
				.getClassLoader()));
	}

	public long getTaskId() {
		return taskId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public int getPeopleNeeded() {
		return peopleNeeded;
	}

	public int getDuration() {
		return duration;
	}

	public Task(JSONObject json) {
		super();
		try {
			this.taskName = json.getJSONObject("category").getString("name");

			this.taskId = json.getLong("id");

			this.taskStatus = json.getString("status");
			this.peopleNeeded = json.getInt("people_needed");
			this.taskShortDesc = json.getString("description");
			this.duration = json.getInt("duration");
			this.distance = "0";
			String due = json.getString("starts_at");
			due = due.replace("T", " ");
			due = due.replace("Z", "");
			Date d;
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(due);
				d.setMonth(11);
			} catch (ParseException e) {
				d = new Date();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

			this.dueDate = dateFormat.format(d);

			DateFormat timeFormat = new SimpleDateFormat("hh:mm a");

			this.dueTime = timeFormat.format(d);

			Date date = new Date();
			this.postedDate = dateFormat.format(date);

			this.gpsLatitude = Double.valueOf(json.getString("latitude"));
			this.gpsLongitude = Double.valueOf(json.getString("longitude"));
			this.organization = new Organization(
					json.getJSONObject("organization"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<Task> fromJsonArray(JSONArray jarray) {
		ArrayList<Task> atasks = new ArrayList<Task>();
		for (int i = 0; i < jarray.length(); i++) {
			try {
				Task t = new Task(jarray.getJSONObject(i));
				atasks.add(t);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return atasks;
	}
}
