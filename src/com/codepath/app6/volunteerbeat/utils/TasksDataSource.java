package com.codepath.app6.volunteerbeat.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.codepath.app6.volunteerbeat.models.Task;

public class TasksDataSource {
	public static final String TABLE_TASKS = "Tasks";

	private static final String KEY_ID = "_id";
	public static final String COLUMN_TASK_ID = "tid";
	private static final String COLUMN_TASK_NAME = "name";
	private static final String COLUMN_TASK_STATUS = "status";
	private static final String COLUMN_TASK_PEOPLE_NEEDED = "people";
	private static final String COLUMN_TASK_DESC = "desc";
	private static final String COLUMN_TASK_DURATION = "duration";
	private static final String COLUMN_TASK_DUEDATE = "duedate";
	private static final String COLUMN_TASK_DUETIME = "duetime";
	private static final String COLUMN_TASK_POSTEDON = "postdate";
	private static final String COLUMN_TASK_LATITUDE = "latitude";
	private static final String COLUMN_TASK_LONGITUDE = "longitude";
	public static final String COLUMN_TASK_SAVED = "saved";
	public static final String COLUMN_TASK_VOLUNTEERED = "volunteered";
	public static final String COLUMN_TASK_ORG_ID = "org_id";

	// Table Create Statement
	public static final String DATABASE_CREATE = "create table " + TABLE_TASKS
			+ "(" + KEY_ID + " integer primary key autoincrement, "
			+ COLUMN_TASK_ID + " integer, " + COLUMN_TASK_NAME + " text, "
			+ COLUMN_TASK_STATUS + " text, " + COLUMN_TASK_DESC + " text, "
			+ COLUMN_TASK_DURATION + " integer, " + COLUMN_TASK_DUEDATE
			+ " text, " + COLUMN_TASK_DUETIME + " text, "
			+ COLUMN_TASK_POSTEDON + " text, " + COLUMN_TASK_LATITUDE
			+ " real, " + COLUMN_TASK_LONGITUDE + " real, "
			+ COLUMN_TASK_SAVED + " integer, "
			+ COLUMN_TASK_VOLUNTEERED + " integer, "
			+ COLUMN_TASK_PEOPLE_NEEDED + " integer, "
			+ COLUMN_TASK_ORG_ID + " integer);";

	public static ContentValues populateValues(Task task) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_TASK_ID, task.getTaskId());
		values.put(COLUMN_TASK_NAME, task.getTaskName());
		values.put(COLUMN_TASK_STATUS, task.getTaskStatus());
		values.put(COLUMN_TASK_PEOPLE_NEEDED, task.getPeopleNeeded());
		values.put(COLUMN_TASK_DESC, task.getTaskShortDesc());
		values.put(COLUMN_TASK_DURATION, task.getDuration());
		values.put(COLUMN_TASK_DUEDATE, task.getDueDate());
		values.put(COLUMN_TASK_DUETIME, task.getDueTime());
		values.put(COLUMN_TASK_POSTEDON, task.getPostedDate());
		values.put(COLUMN_TASK_LATITUDE, task.getGpsLatitude());
		values.put(COLUMN_TASK_LONGITUDE, task.getGpsLongitude());
		values.put(COLUMN_TASK_SAVED, task.isSavedTask());
		values.put(COLUMN_TASK_VOLUNTEERED, task.isVolunteeredTask());
		values.put(COLUMN_TASK_ORG_ID, task.getOrganization().getOrgId());
		
		return values;
	}

	public static Task cursorToTask(Cursor c) {
		Task task = new Task();

		task.setTaskId(c.getLong(c.getColumnIndex(COLUMN_TASK_ID)));
		task.setTaskName(c.getString(c.getColumnIndex(COLUMN_TASK_NAME)));
		task.setTaskStatus(c.getString(c.getColumnIndex(COLUMN_TASK_STATUS)));
		task.setPeopleNeeded(c.getInt(c.getColumnIndex(COLUMN_TASK_PEOPLE_NEEDED)));
		task.setTaskShortDesc(c.getString(c.getColumnIndex(COLUMN_TASK_DESC)));
		task.setDuration(c.getInt(c.getColumnIndex(COLUMN_TASK_DURATION)));
		task.setDueDate(c.getString(c.getColumnIndex(COLUMN_TASK_DUEDATE)));
		task.setDueTime(c.getString(c.getColumnIndex(COLUMN_TASK_DUETIME)));
		task.setPostedDate(c.getString(c.getColumnIndex(COLUMN_TASK_POSTEDON)));
		task.setGpsLatitude(c.getDouble(c.getColumnIndex(COLUMN_TASK_LATITUDE)));
		task.setGpsLongitude(c.getDouble(c.getColumnIndex(COLUMN_TASK_LONGITUDE)));
		task.setSavedTask(c.getInt(c.getColumnIndex(COLUMN_TASK_SAVED)) == 0 ? false : true);
		task.setVolunteeredTask(c.getInt(c.getColumnIndex(COLUMN_TASK_VOLUNTEERED)) == 0 ? false : true);
		
		return task;
	}

}
