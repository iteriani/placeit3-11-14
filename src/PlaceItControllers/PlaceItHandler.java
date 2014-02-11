package PlaceItControllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import PlaceItDB.PlaceIt;
import PlaceItDB.iPlaceItModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaceItHandler extends SQLiteOpenHelper implements iPlaceItModel {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 6;

	// Database Name
	private static final String DATABASE_NAME = "CSE110";

	// Contacts table name
	private static final String TABLE_PLACEITS = "placeIts";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_ACTIVEDATE ="activeDate";

	public PlaceItHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PLACEITS_TABLE = "CREATE TABLE " + TABLE_PLACEITS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE
				+ " VARCHAR(255), " + KEY_DESCRIPTION + " TEXT ,"  
				+ KEY_LONGITUDE + " DOUBLE, " + KEY_LATITUDE + " DOUBLE ," + KEY_ACTIVEDATE + 
				" DOUBLE" +")";

		db.execSQL(CREATE_PLACEITS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEITS);

		// Create tables again
		onCreate(db);
	}

	@Override
	public void addPlaceIt(PlaceIt placeIt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, placeIt.getTitle()); 
		values.put(KEY_DESCRIPTION, placeIt.getDescription());
		values.put(KEY_LONGITUDE, placeIt.getLongitude());
		values.put(KEY_LATITUDE, placeIt.getLatitude());
        Calendar cal = Calendar.getInstance();
        cal.setTime(placeIt.getActiveDate());
        cal.add(Calendar.MINUTE, 45);
		values.put(KEY_ACTIVEDATE, cal.getTime().getTime());
		// Inserting Row
		db.insert(TABLE_PLACEITS, null, values);
		db.close(); // Closing database connection

	}

	@Override
	public PlaceIt getPlaceIt(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PLACEITS, new String[] { KEY_ID,
				KEY_TITLE, KEY_DESCRIPTION }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		PlaceIt placeit = new PlaceIt(
				cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)), 
				Double.parseDouble(cursor.getString(4)), Long.valueOf(cursor.getString(5)));
		// return contact
		return placeit;
	}

	@Override
	public List<PlaceIt> getAllPlaceIts() {
		List<PlaceIt> placeItList = new ArrayList<PlaceIt>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PLACEITS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				PlaceIt contact = new PlaceIt();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setTitle(cursor.getString(1));
				contact.setDescription(cursor.getString(2));
				contact.setLatitude(Double.valueOf(cursor.getString(3)));
				contact.setLongitude(Double.valueOf(cursor.getString(4)));
				double ds = Double.parseDouble(cursor.getString(5));
				long sd = (long) ds;
				contact.setActiveDate(sd);
				// Adding placeit to listr
				placeItList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return placeItList;
	}

	@Override
	public int getPlaceItsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PLACEITS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	@Override
	public int updatePlaceIt(PlaceIt placeit) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, placeit.getTitle());
		values.put(KEY_DESCRIPTION, placeit.getDescription());
		values.put(KEY_ACTIVEDATE, placeit.getActiveDate().getTime());
		// updating row
		return db.update(TABLE_PLACEITS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(placeit.getID()) });
	}
	
	@Override
	public void deleteAll(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEITS);
		onCreate(db);
	}

	@Override
	public void deletePlaceIt(PlaceIt placeit) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PLACEITS, KEY_ID + " = ?",
				new String[] { String.valueOf(placeit.getID()) });
		db.close();
	}

	@Override
	public int repostPlaceit(PlaceIt placeit) {
		
		java.util.Date date = placeit.getActiveDate();
	    Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(date); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
	    java.util.Date newDate = cal.getTime(); // returns new date object, one hour in the future
	    
	    placeit.setActiveDate(newDate.getTime());
		return this.updatePlaceIt(placeit);
	}

	@Override
	public void deactivatePlaceit(PlaceIt placeit) {
		placeit.setActiveDate(0); /* maybe...*/
		this.updatePlaceIt(placeit);
		
	}

}
