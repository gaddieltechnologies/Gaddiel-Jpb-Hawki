package com.gaddiel.hawki;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteoperationCustomerVisit extends SQLiteOpenHelper{
	//JSONParser 
	private JSONParser jsonParser=new JSONParser();
	
	public SQLiteoperationCustomerVisit(Context applicationcontext) {
        super(applicationcontext, "hawkicvhsqlite.db", null, 1);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		  String createcustTable="CREATE TABLE EMP_CLIENT_VISIT_REP(" +
		  		"Id INTEGER PRIMARY KEY AUTOINCREMENT," +
		  		"Emp_Id INTEGER," +
		  		"Visit_Date TEXT," +
		  		"Visit_Time TEXT," +
		  		"Client_Id INTEGER," +
		  		"Purpose_Id INTEGER," +
		  		"Expense_Amt REAL DEFAULT 0," +
		  		"Expense_Ref TEXT," +
		  		"Comment TEXT," +
		  		"Visit_Activity TEXT," +
		  		"Visit_Rep_LngLat TEXT," +
		  		"followupActive TEXT," +		  	
		  		"Process_Ind TEXT" +
		  		")";
		  
	      db.execSQL(createcustTable);
	     // Log.d("Create Table",""+createcustTable);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String dropquery;
		dropquery = "DROP TABLE IF EXISTS EMP_CLIENT_VISIT_REP";
        db.execSQL(dropquery);
        onCreate(db);
	}
	
	public void insertCustomer(HashMap<String, String> customerQueryVal) {
		Log.d("SQLiteoperationCustomerVisit ---- insertcustomer Start", "Customer Inserted");
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Id", customerQueryVal.get("Id"));		
		values.put("Emp_Id", customerQueryVal.get("Emp_Id"));		
		values.put("Visit_Date", customerQueryVal.get("Visit_Date"));		
		values.put("Visit_Time", customerQueryVal.get("Visit_Time"));	
		values.put("Client_Id", customerQueryVal.get("Client_Id"));		
		values.put("Purpose_Id", customerQueryVal.get("Purpose_Id"));		
		values.put("Visit_Activity", customerQueryVal.get("Visit_Activity"));		
		values.put("Expense_Amt", customerQueryVal.get("Expense_Amt"));
		values.put("Expense_Ref", customerQueryVal.get("Expense_Ref"));
		values.put("Comment", customerQueryVal.get("Comment"));		
		values.put("Visit_Rep_LngLat", customerQueryVal.get("Visit_Rep_LngLat"));		
		values.put("followupActive", customerQueryVal.get("followupActive"));		
		values.put("Process_Ind", "N");
		
		//values.put("followupdesc", customerQueryVal.get("followupdesc"));
		// values.put("Loc_Address", customerQueryVal.get("Loc_Address"));
	
		database.insert("EMP_CLIENT_VISIT_REP", null, values);
		Log.d("SQLiteoperationCustomerVisit ---- insertcustomer End", "Customer Inserted");
		database.close();
	}
	
	/*public HashMap<String, String> getCustomerValue() {
		HashMap<String, String>values = new HashMap<String, String>();
		SQLiteDatabase database = this.getWritableDatabase();
		String selectQuery = "SELECT " +
				"Id," +
				"Employee_Id," +
				"Visit_Date," +
				"Visit_Time," +
				"Customer_Id," +
				"Purpose_Id," +
				"Activity," +
				"TravelAmount,"+
				"Comment," +
				"LngLat,"+
				"Loc_Address,"+
				"Process_Ind," +
				" from CUSTOMER_VISIT WHERE Process_Ind = 'N' ";
		Cursor cursor = database.rawQuery(selectQuery, null);
		int count=0;
		if (cursor.moveToFirst()) {
			do {				
				values.put("Id", cursor.getString(0));
				values.put("Employee_Id", cursor.getString(1));
				values.put("Visit_Date", cursor.getString(2));
				values.put("Visit_Time", cursor.getString(3));
				values.put("Customer_Id", cursor.getString(4));
				values.put("Purpose_Id", cursor.getString(5));
				values.put("Activity", cursor.getString(6));
				values.put("TravelAmount", cursor.getString(7));
				values.put("Comment", cursor.getString(8));
				values.put("LngLat", cursor.getString(9));
				values.put("Loc_Address", cursor.getString(10));
				
				Log.d("getcustValue","Customer="+cursor.getString(1));						
				
				count++;
				Log.d("Count","num"+ count++);
			} while (cursor.moveToNext());
		}
		database.close();
		
		return values;
		
	}*/
	
	//public HashMap<String, String> putEmpCustVisitValue() {
	public void putEmpCustVisitValue() {
		HashMap<String, String>values = new HashMap<String, String>();
		//Log.d("EmpCustVisitValue ","Before Update");	
		
		SQLiteDatabase database = this.getWritableDatabase();
			String query;
			query = "BEGIN";
	        database.execSQL(query);
			query = "Update EMP_CLIENT_VISIT_REP SET Process_Ind='X'";
	        database.execSQL(query);
	        Log.d("EmpCustVisitValue ","After Update");	
		 query = "SELECT " +
		 		"Id," +
		 		"Emp_Id," +
		 		"Visit_Date," +
				"Visit_Time," +
				"Client_Id," +
				"Purpose_Id," +
				"Visit_Activity," +
				"Expense_Amt," +
				"Expense_Ref," +
				"Comment," +
				"Visit_Rep_LngLat,"+
				"followupActive " +
		  		//"followupdesc" +
				//"Loc_Address "+
		 		"FROM EMP_CLIENT_VISIT_REP where Process_Ind = 'X'";
		// query = "SELECT Id, Employee_Id, LngLat, Process_Ind FROM Emp_Loc_Tracker";
		Cursor cursor = database.rawQuery(query, null);
		int count=0;
		if (cursor.moveToFirst()) {
			do {			
				values.put("Id", cursor.getString(0));
				values.put("Emp_Id", cursor.getString(1));				
				values.put("Visit_Date", cursor.getString(2));
				values.put("Visit_Time", cursor.getString(3));
				values.put("Client_Id", cursor.getString(4));
				values.put("Purpose_Id", cursor.getString(5));
				values.put("Visit_Activity", cursor.getString(6));
				values.put("Expense_Amt", cursor.getString(7));
				values.put("Expense_Ref", cursor.getString(8));
				values.put("Comment", cursor.getString(9));
				values.put("Visit_Rep_LngLat", cursor.getString(10));
				values.put("followupActive", cursor.getString(11));
				//values.put("followupdesc", cursor.getString(11));
				
				//values.put("Loc_Address", cursor.getString(10));
													
				Log.d("EmpCustVisitValue","id="+cursor.getString(0));
				Log.d("EmpCustVisitValue","EmpId="+cursor.getString(1));
				Log.d("EmpCustVisitValue","Visit_Date="+cursor.getString(2));
				Log.d("EmpCustVisitValue","Visit_Time="+cursor.getString(3));
				Log.d("EmpCustVisitValue","Client_Id="+cursor.getString(4));
				Log.d("EmpCustVisitValue","Purpose_Id="+cursor.getString(5));
				Log.d("EmpCustVisitValue","VisitActivity="+cursor.getString(6));
				Log.d("EmpCustVisitValue","ExpenseAmt="+cursor.getString(7));
				Log.d("EmpCustVisitValue","Expense_Ref="+cursor.getString(8));
				Log.d("EmpCustVisitValue","Comment="+cursor.getString(9));
				Log.d("EmpCustVisitValue","LngLat="+cursor.getString(10));
				Log.d("EmpCustVisitValue","followupActive="+cursor.getString(11));
				//Log.d("EmpCustVisitValue","followupdesc="+cursor.getString(11));
				//Log.d("EmpCustVisitValue","Loc_Address="+cursor.getString(10));
				
				//PHP Insert 				  
			    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();      	
			   //nameValuePairs.add(new BasicNameValuePair("Id",cursor.getString(0)));			    
			    nameValuePairs.add(new BasicNameValuePair("Emp_Id", cursor.getString(1)));				
				nameValuePairs.add(new BasicNameValuePair("Visit_Date", cursor.getString(2)));
				nameValuePairs.add(new BasicNameValuePair("Visit_Time", cursor.getString(3)));
				nameValuePairs.add(new BasicNameValuePair("Client_Id", cursor.getString(4)));
				nameValuePairs.add(new BasicNameValuePair("Purpose_Id", cursor.getString(5)));
				nameValuePairs.add(new BasicNameValuePair("Visit_Activity", cursor.getString(6)));
				nameValuePairs.add(new BasicNameValuePair("Expense_Amt", cursor.getString(7)));
				nameValuePairs.add(new BasicNameValuePair("Expense_Ref", cursor.getString(8)));
				nameValuePairs.add(new BasicNameValuePair("Comment", cursor.getString(9)));
				nameValuePairs.add(new BasicNameValuePair("Visit_Rep_LngLat", cursor.getString(10)));
				nameValuePairs.add(new BasicNameValuePair("followupActive", cursor.getString(11)));
				//nameValuePairs.add(new BasicNameValuePair("followupdesc", cursor.getString(11)));
			//	nameValuePairs.add(new BasicNameValuePair("Loc_Address", cursor.getString(10)));
				
				
				String insertEmpCustVisitValueUrl="http://hawki-beta.gaddieltech.com/androidphp/insertEmpClientVisitRep.php";
				String json =jsonParser.makeHttpRequest(insertEmpCustVisitValueUrl, "POST",nameValuePairs);
				Log.d("EmpCustVisitValue PHP: ", ">>>" + json);
			   // Log.d("EmpCustVisitValue", "EmpCustVisitValue insert From Url ");
				
				
				query = "Update EMP_CLIENT_VISIT_REP SET Process_Ind='Y' WHERE Id=" +cursor.getString(0) ;
		        database.execSQL(query);
				
				
				count++;
				Log.d("Count","num"+ count);
			} while (cursor.moveToNext());
		}
		//Delete
		query = "Delete from EMP_CLIENT_VISIT_REP WHERE Process_Ind='Y'";
        database.execSQL(query);
        query = "COMMIT";
        database.execSQL(query);
		database.close();
		
		//return values;
		
	}
	
}
