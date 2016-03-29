package com.gaddiel.hawki;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLiteOperations extends SQLiteOpenHelper {
	
	Date sysDate;
	String dayOfWeek;
	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE");
	
	public SQLiteOperations(Context applicationcontext) {
        super(applicationcontext, "hawisqlite.db", null, 1);
	}
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		
		String createquery;		 
				createquery = "CREATE TABLE EMP_TRACKER_SCH("+
				  "Sun_StartTime TEXT, "+
				  "Sun_EndTime TEXT, "+
				  "Sun_TimeInterval INTEGER, "+
				  "Mon_StartTime TEXT, "+
				  "Mon_EndTime TEXT, "+
				  "Mon_TimeInterval INTEGER, "+
				  "Tue_StartTime TEXT, "+
				  "Tue_EndTime TEXT, "+
				  "Tue_TimeInterval INTEGER,"+
				  "Wed_StartTime TEXT, "+
				  "Wed_EndTime TEXT, "+
				  "Wed_TimeInterval INTEGER, "+
				  "Thu_StartTime TEXT, "+
				  "Thu_EndTime TEXT, "+
				  "Thu_TimeInterval INTEGER, "+
				  "Fri_StartTime TEXT, "+
				  "Fri_EndTime TEXT, "+
				  "Fri_TimeInterval INTEGER, "+
                  "Sat_StartTime TEXT, " +
                  "Sat_EndTime TEXT, "+
                  "Sat_TimeInterval INTEGER) ";
        database.execSQL(createquery);
        
      
	}
 
	//Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String dropquery;
		dropquery = "DROP TABLE IF EXISTS EMP_TRACKER_SCH";
        database.execSQL(dropquery);
        onCreate(database);
	}
	
	

	public void insert(HashMap<String, String>queryValues) {
		
		// Delete the records before insert
		
		SQLiteDatabase database = this.getWritableDatabase();
		
		String sqlstr;
		sqlstr = "DELETE FROM EMP_TRACKER_SCH";
        database.execSQL(sqlstr);
        Log.d("Delete done", sqlstr);
		
		
		Log.d("insert", "In insert method");
		
		ContentValues values = new ContentValues();
		
		values.put("Sun_StartTime", queryValues.get("Sun_StartTime"));
		values.put("Sun_EndTime", queryValues.get("Sun_EndTime"));
		values.put("Sun_TimeInterval", queryValues.get("Sun_TimeInterval"));
		
		values.put("Mon_StartTime", queryValues.get("Mon_StartTime"));
		values.put("Mon_EndTime", queryValues.get("Mon_EndTime"));
		values.put("Mon_TimeInterval", queryValues.get("Mon_TimeInterval"));
		
		values.put("Tue_StartTime", queryValues.get("Tue_StartTime"));
		values.put("Tue_EndTime", queryValues.get("Tue_EndTime"));
		values.put("Tue_TimeInterval", queryValues.get("Tue_TimeInterval"));
		
		values.put("Wed_StartTime", queryValues.get("Wed_StartTime"));
		values.put("Wed_EndTime", queryValues.get("Wed_EndTime"));
		values.put("Wed_TimeInterval", queryValues.get("Wed_TimeInterval"));
		
		values.put("Thu_StartTime", queryValues.get("Thu_StartTime"));
		values.put("Thu_EndTime", queryValues.get("Thu_EndTime"));
		values.put("Thu_TimeInterval", queryValues.get("Thu_TimeInterval"));
		
		values.put("Fri_StartTime", queryValues.get("Fri_StartTime"));
		values.put("Fri_EndTime", queryValues.get("Fri_EndTime"));
		values.put("Fri_TimeInterval", queryValues.get("Fri_TimeInterval"));
		
		values.put("Sat_StartTime", queryValues.get("Sat_StartTime"));
		values.put("Sat_EndTime", queryValues.get("Sat_EndTime"));
		values.put("Sat_TimeInterval", queryValues.get("Sat_TimeInterval"));
		
		
		database.insert("EMP_TRACKER_SCH", null, values);
		Log.d("Insert data", "Sucess");
		database.close();
		
	}
	
	public HashMap<String, String> getAllValues() {
		HashMap<String, String>values = new HashMap<String, String>();
		SQLiteDatabase database = this.getWritableDatabase();
		sysDate = new Date(System.currentTimeMillis());
		dayOfWeek = sdf.format(sysDate);
		Log.d("day_of_week", dayOfWeek);
		String selectQuery = "SELECT "+dayOfWeek+"_StartTime, "+
				dayOfWeek+"_EndTime, "+
				dayOfWeek+"_TimeInterval"+
				" from EMP_TRACKER_SCH";
		Cursor cursor = database.rawQuery(selectQuery, null);
		int count=0;
		if (cursor.moveToFirst()) {
			do {
				
				values.put(dayOfWeek+"_StartTime", cursor.getString(0));
				values.put(dayOfWeek+"_EndTime", cursor.getString(1));
				values.put(dayOfWeek+"_TimeInterval", cursor.getString(2));				
								
				Log.d("GetAllValus",
						dayOfWeek+"_StartTime="+cursor.getString(0)+","+
								dayOfWeek+"_EndTime="+cursor.getString(1)+","+
								dayOfWeek+"_TimeInterval="+cursor.getString(2));
				count++;
				Log.d("Count","num"+ count++);
			} while (cursor.moveToNext());
		}
		database.close();
		
		return values;
		
	}
}
