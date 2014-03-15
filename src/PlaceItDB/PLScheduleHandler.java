/**
 * 
 * This class implements iPLScheduleModel to handle placeit schedules
 * It also links to the database and updates each modification
 *
 */

package PlaceItDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Models.PLSchedule;
import Models.PlaceIt;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PLScheduleHandler extends SQLiteOpenHelper implements
		iPLScheduleModel {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 11;

	// Database Name
	private static final String DATABASE_NAME = "CSE110";

	// Contacts table name
	private static final String TABLE_PLSCHEDULE = "PLSchedule";

	// Contacts Table Columns names
	int _id;
	int placeitID; // this is a foreign key to placeIt
	int day;
	int week;
	PLSchedule schedule = null;
//	List<Integer> schedules;
	Boolean debug;

	private static final String KEY_ID = "id";
	private static final String KEY_PLACEITID = "placeItID";
	private static final String KEY_PLACEITSTARTWEEK = "startweek";
	private static final String KEY_PLACEITDAY = "day";
	private static final String KEY_PLACEITWEEK = "week";

	
	//// constructor ////
	public PLScheduleHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	//// methods ////
	
	@Override
	/* */
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PLACEITS_TABLE =

		"CREATE TABLE " + TABLE_PLSCHEDULE + "(" 
				+ KEY_ID + " INTEGER PRIMARY KEY, " 
				+ KEY_PLACEITID + " INTEGER, "
				+ KEY_PLACEITSTARTWEEK + " INTEGER, "
				+ KEY_PLACEITDAY + " INTEGER, " 
				+ KEY_PLACEITWEEK + " INTEGER, " + "FOREIGN KEY("
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

	/* 
	 * Called from the PlaceItScheduler's addSchedule method.
	 * Adds the given schedule to the SQLiteDatabase.
	 */
	@Override
	public PlaceIt addSchedule(PlaceIt placeit, int day, int week) {
		int startweek = Calendar.WEEK_OF_YEAR;
		
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_PLACEITID, placeit.getID());
			values.put(KEY_PLACEITSTARTWEEK, Integer.toString(startweek));;
			values.put(KEY_PLACEITDAY, Integer.toString(day));
			values.put(KEY_PLACEITWEEK, Integer.toString(week));
			db.insert(TABLE_PLSCHEDULE, null, values);
			
			return placeit;

		} catch (Exception e) {
			try {
				onCreate(this.getWritableDatabase());
				return this.addSchedule(placeit, day, week);
			} catch (Exception e2) {
				return null;
			}
		}
	}

	@Override
	public PlaceIt removeSchedule(PlaceIt placeit, int day, int week) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			
			db.delete(
					TABLE_PLSCHEDULE,
					KEY_PLACEITID + " = ? AND " + KEY_PLACEITDAY + " = ? AND " + KEY_PLACEITWEEK + " = ?", 
					new String[] { String.valueOf(placeit.getID()), String.valueOf(day), String.valueOf(week) }
					);
			db.close();
			
		} catch (Exception e) {
			try {
				onCreate(this.getWritableDatabase());
				return this.removeSchedule(placeit, day, week);
			} catch (Exception e2) {
				return null;
			}
		}
		return null;
	}

	/*
	 * Finds the entry in the SQLiteDatabase that contains the schedule of the given Place-It.
	 * Takes the Place-It's scheduled day and week, and creates a PLSchedule item to contain them.
	 * Returns that PLSchedule item.
	 */
	@Override
	public PLSchedule getSchedule(PlaceIt placeit) {
		try {
			int startweek;
			int day;
			int week;
			PLSchedule schedule = null;
			String selectQuery = "SELECT " + KEY_PLACEITDAY + " FROM "
					+ TABLE_PLSCHEDULE + " WHERE " + KEY_PLACEITID + " = "
					+ String.valueOf(placeit.getID());

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			
			// If a schedule is found, return it
			if (cursor.moveToFirst()) {
				do {
					startweek = Integer.valueOf(cursor.getString(0));
					day = Integer.valueOf(cursor.getString(1));
					week = Integer.valueOf(cursor.getString(2));
					schedule = new PLSchedule(0, startweek, day, week);

				} while (cursor.moveToNext());
			}
			
			return schedule;
		} catch (Exception e) {
			try {
				onCreate(this.getWritableDatabase());
				return this.getSchedule(placeit);
			} catch (Exception e2) {
				return null;
			}
		}
	}

}
