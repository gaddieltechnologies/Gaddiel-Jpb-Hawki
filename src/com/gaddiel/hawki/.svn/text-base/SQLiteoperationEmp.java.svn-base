package com.gaddiel.hawki;

import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteoperationEmp extends SQLiteOpenHelper{
	
	public SQLiteoperationEmp(Context applicationcontext) {
        super(applicationcontext, "hawkisqlite.db", null, 1);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		  String createEmpTable="CREATE TABLE EMPLOYEE(Emp_Id TEXT,Active TEXT)";
	      db.execSQL(createEmpTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String dropquery;
		dropquery = "DROP TABLE IF EXISTS EMPLOYEE";
        db.execSQL(dropquery);
        onCreate(db);
	}
	
	public void insertEmp(HashMap<String, String> queryVal) {
		String Active = null;
		Log.d("insertEmp", "Employee Inserted");
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Emp_Id", queryVal.get("Emp_Id"));
		values.put("Active", queryVal.get("Active"));
		database.insert("EMPLOYEE", null, values);
		database.close();
	}
	
	public HashMap<String, String> getEmpValue() {
		HashMap<String, String>values = new HashMap<String, String>();
		SQLiteDatabase database = this.getWritableDatabase();
		String selectQuery = "SELECT Emp_Id,Active from EMPLOYEE";
		Cursor cursor = database.rawQuery(selectQuery, null);
		int count=0;
		if (cursor.moveToFirst()) {
			do {				
				values.put("Emp_Id", cursor.getString(0));
				values.put("Active", cursor.getString(1));
				Log.d("getEmpValue","EMPLOYEE="+cursor.getString(0));						
				
				count++;
				Log.d("Count","num"+ count++);
			} while (cursor.moveToNext());
		}
		database.close();
		
		return values;
		
	}
}
