package com.keepworks.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.keepworks.eventcontents.TrackEvents;
import com.keepworks.utils.DataStorage;

public class TrackEventDatabase extends SQLiteOpenHelper {

	public final static String DB_NAME = "EVENT_TRACKER";
	public final static int DB_VERSION = 1;
	public final String TABLE_NAME = "EVENT_TABLE";
	public String EVENT_NAME = "EVENT_NAME";
	public String EVENT_PLACE = "EVENT_PLACE";
	public String EVENT_ENTRY = "EVENT_ENTRY";
	public String EVENT_IMAGE_RES_ID = "EVENT_IMAGE_RES_ID";
	public String USER_NAME = "USER_NAME";
	Context ctx;

	public TrackEventDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		ctx = context;
	}

	/**
	 * creating table in database
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		String DB_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + EVENT_NAME + " TEXT, " + EVENT_IMAGE_RES_ID
				+ " INTEGER, " + EVENT_PLACE + " TEXT, " + EVENT_ENTRY + " TEXT, " + USER_NAME + " TEXT " + " ) ";
		database.execSQL(DB_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * adding each event in database
	 */
	public void addEvent(TrackEvents trackEvents) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(EVENT_NAME, trackEvents.getTrackedEventName());
		cv.put(EVENT_PLACE, trackEvents.getTrackedEventPlace());
		cv.put(EVENT_ENTRY, trackEvents.getTrackedEventEntry());
		cv.put(EVENT_IMAGE_RES_ID, trackEvents.getTrackedEventImageResId());
		cv.put(USER_NAME, trackEvents.getUserName());
		database.insert(TABLE_NAME, null, cv);
		database.close();
	}

	/**
	 * to get list of events present in the database
	 */
	public ArrayList<TrackEvents> getListOfEvents() {
		ArrayList<TrackEvents> trackedEventList = new ArrayList<TrackEvents>();
		SQLiteDatabase database = this.getWritableDatabase();

		String query = " SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = '" + DataStorage.getUserName(ctx)
				+ "'";

		Cursor cursor = database.rawQuery(query, null);

		if (cursor.moveToFirst()) {

			do {
				TrackEvents trackEvents = new TrackEvents();
				trackEvents.setTrackedEventName(cursor.getString(0));
				trackEvents.setTrackedEventImageResId(cursor.getInt(1));
				trackEvents.setTrackedEventPlace(cursor.getString(2));
				trackEvents.setTrackedEventEntry(cursor.getString(3));
				trackEvents.setUserName(cursor.getString(4));
				trackedEventList.add(trackEvents);
			} while (cursor.moveToNext());
		}

		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}

		database.close();

		return trackedEventList;
	}

	/**
	 * to delete selected event from database
	 */
	public void deleteTrackedEvent(String name) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(TABLE_NAME, EVENT_NAME + " = ?", new String[] { name });
		if (database != null) {
			database.close();
		}
	}
}
