package PlaceItDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import Models.PlaceIt;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PLScheduleHandler extends SQLiteOpenHelper implements
		iPLScheduleModel {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 7;

	// Database Name
	private static final String DATABASE_NAME = "CSE110";

	// Contacts table name
	private static final String TABLE_PLSCHEDULE = "PLSchedule";

	// Contacts Table Columns names
	int _id;
	int placeitID; // this is a foreign key to placeIt
	List<Integer> schedules;
	Boolean debug;

	private static final String KEY_ID = "id";
	private static final String KEY_PLACEITID = "placeItID";
	private static final String KEY_PLACEITDAY = "day";

	public PLScheduleHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	/* */
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PLACEITS_TABLE =

		"CREATE TABLE " + TABLE_PLSCHEDULE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY, " + KEY_PLACEITID
				+ " INTEGER, " + KEY_PLACEITDAY
				+ " INTEGER, " + "FOREIGN KEY(" + KEY_PLACEITID
				+ ") REFERENCES placeIts(id) ON DELETE CASCADE)";

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
	public PlaceIt addSchedule(PlaceIt placeit, List<Integer> days) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (Integer day : days) {
			ContentValues values = new ContentValues();
			values.put(KEY_PLACEITID, Integer.toString(placeit.getID()));
			values.put(KEY_PLACEITDAY, Integer.toString(day));
			db.insert(TABLE_PLSCHEDULE, null, values);
		}
		return placeit;
	}

	@Override
	public PlaceIt removeSchedule(PlaceIt placeit, List<Integer> days) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (Integer day : days) {
			db.delete(
					TABLE_PLSCHEDULE,
					KEY_PLACEITID + " = ? AND " + KEY_PLACEITDAY + " = ?",
					new String[] { String.valueOf(placeit.getID()),
							String.valueOf(day) });
			db.close();
		}

		return null;
	}

	@Override
	public List<Integer> getSchedule(PlaceIt placeit) {
		List<Integer> schedules = new Vector<Integer>();
		String selectQuery = "SELECT " + KEY_PLACEITDAY + " FROM " + TABLE_PLSCHEDULE + " WHERE "
				+ KEY_PLACEITID + " = " + String.valueOf(placeit.getID());

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				schedules.add(Integer.parseInt(cursor.getString(0)));
				
			} while (cursor.moveToNext());
		}
		return schedules;
	}

}
