package com.codepath.app6.volunteerbeat.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codepath.app6.volunteerbeat.models.Organization;
import com.codepath.app6.volunteerbeat.models.Task;

public class MySQLiteHelper extends SQLiteOpenHelper {
	// Logcat tag
	private static final String TAG = "MySQLiteHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "vbeat_tasks.db";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(TasksDataSource.DATABASE_CREATE);
		db.execSQL(OrgDataSource.DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + OrgDataSource.TABLE_ORGS);
		db.execSQL("DROP TABLE IF EXISTS " + TasksDataSource.TABLE_TASKS);

		// create new tables
		onCreate(db);

	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void createTaskEntry(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = TasksDataSource.populateValues(task);

		db.insert(TasksDataSource.TABLE_TASKS, null, values);
	}

	public void createOrganizationEntry(Organization org) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = OrgDataSource.populateValues(org);

		db.insert(OrgDataSource.TABLE_ORGS, null, values);
	}

	public Task getTaskById(long uid) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TasksDataSource.TABLE_TASKS
				+ " WHERE " + TasksDataSource.COLUMN_TASK_ID + " = " + uid;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Task task = TasksDataSource.cursorToTask(c);

		return task;
	}

	public Organization getOrganizationById(long uid) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + OrgDataSource.TABLE_ORGS
				+ " WHERE " + OrgDataSource.COLUMN_ORG_ID + " = " + uid;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Organization org = OrgDataSource.cursorToOrg(c);

		return org;
	}

	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<Task>();
		String selectQuery = "SELECT  * FROM " + TasksDataSource.TABLE_TASKS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Task task = TasksDataSource.cursorToTask(c);

				Organization org = getOrganizationById(c.getLong(c
						.getColumnIndex(TasksDataSource.COLUMN_TASK_ORG_ID)));
				task.setOrganization(org);

				// adding to list
				tasks.add(task);
			} while (c.moveToNext());
		}

		return tasks;
	}

	public List<Task> getAllSavedTasks() {
		List<Task> tasks = new ArrayList<Task>();
		// TODO: query the right data
		String selectQuery = "SELECT  * FROM " + TasksDataSource.TABLE_TASKS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Task task = TasksDataSource.cursorToTask(c);

				Organization org = getOrganizationById(c.getLong(c
						.getColumnIndex(TasksDataSource.COLUMN_TASK_ORG_ID)));
				task.setOrganization(org);

				// adding to list
				if (task.isSavedTask())
					tasks.add(task);
			} while (c.moveToNext());
		}

		return tasks;
	}

	public List<Task> getAllTimelineTasks() {
		List<Task> tasks = new ArrayList<Task>();
		// TODO: query the right data
		String selectQuery = "SELECT  * FROM " + TasksDataSource.TABLE_TASKS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Task task = TasksDataSource.cursorToTask(c);

				Organization org = getOrganizationById(c.getLong(c
						.getColumnIndex(TasksDataSource.COLUMN_TASK_ORG_ID)));
				task.setOrganization(org);

				// adding to list
				if (task.isVolunteeredTask())
					tasks.add(task);
			} while (c.moveToNext());
		}

		return tasks;
	}

	public boolean updateSavedState(long id, boolean val) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(TasksDataSource.COLUMN_TASK_SAVED, val);
		return db.update(TasksDataSource.TABLE_TASKS, args, TasksDataSource.COLUMN_TASK_ID + "=" + id, null) > 0;
	}

	public boolean updateVolunteeredState(long id, boolean val) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(TasksDataSource.COLUMN_TASK_VOLUNTEERED, val);
		return db.update(TasksDataSource.TABLE_TASKS, args, TasksDataSource.COLUMN_TASK_ID + "=" + id, null) > 0;
	}
	
	public void deleteAllTasks() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("delete from " + OrgDataSource.TABLE_ORGS);
		db.execSQL("delete from " + TasksDataSource.TABLE_TASKS);
	}

}
