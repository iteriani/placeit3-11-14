package com.classproj.placeit;

import java.util.List;

import PlaceItDB.PlaceIt;
import PlaceItDB.Schedule;
import PlaceItDB.iPLScheduleModel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaceItScheduler extends SQLiteOpenHelper implements
		iPLScheduleModel {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 6;

	// Database Name
	private static final String DATABASE_NAME = "CSE110";

	// Contacts table name
	private static final String TABLE_PLSCHEDULE = "PLSchedule";

	// Contacts Table Columns names
	int _id;
	int placeitID; // this is a foreign key to placeIt
	List<Schedule> schedules;
	Boolean debug;

	private static final String KEY_ID = "id";
	private static final String KEY_PLACEITID = "placeItID";
	private static final String KEY_PLACEITDAY = "day";

	public PlaceItScheduler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	/* */
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PLACEITS_TABLE =

		"CREATE TABLE " + TABLE_PLSCHEDULE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY, " + KEY_PLACEITID
				+ " INTEGER REFERENCES placeIts.id , " + KEY_PLACEITDAY
				+ " VARCHAR(10), " + "FOREIGN KEY("
				+ KEY_PLACEITID + ") REFERENCES placeIts(id) ON DELETE CASCADE)";

		db.execSQL(CREATE_PLACEITS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLSCHEDULE);

		// Create tables again
		onCreate(db);
	}

	@Override
	public void setUpSchedules() {
		// TODO Auto-generated method stub

	}

	@Override
	public PlaceIt initializeSchedule(PlaceIt placeit, List<Schedule> schedules) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceIt addSchedule(PlaceIt placeit, Schedule day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceIt removeSchedule(PlaceIt placeit, Schedule day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceIt scheduleNextActivation(PlaceIt placeit) {
		// TODO Auto-generated method stub
		return null;
	}
}
