package com.gaddiel.hawki;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;



public class DBController  extends SQLiteOpenHelper {
	private Context applicationcontext;
	ProgressDialog prgDialog;
//	public DBController(Context applicationcontext) {
//		this.applicationcontext = applicationcontext;
//        super(applicationcontext, "androidsqlite.db", null, 1);
//    }
	
	public DBController(Context applicationcontext) {		
        super(applicationcontext, "androidsqlite.db", null, 1);
	}
	
	
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
	//	query = "CREATE TABLE Emp_Loc_Tracker ( Id INTEGER PRIMARY KEY AUTOINCREMENT,Employee_Id INTEGER, LngLat TEXT, Tracker_Timestamp TEXT, Process_Ind TEXT)";
		query = "CREATE TABLE EMP_TRACKED_LOC (Id INTEGER PRIMARY KEY AUTOINCREMENT, Emp_Id INTEGER, Track_LngLat TEXT,Track_Date TEXT, Track_Time TEXT, Process_Ind TEXT)";
        database.execSQL(query);
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS EMP_TRACKED_LOC";
		database.execSQL(query);
        onCreate(database);
 
	}
	
	
	/**
	 * Inserts User into SQLite DB
	 * @param queryValues
	 */
	public void insertEmpLocTracker(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
	    Date date = new Date();
		String datet = dateFormat.format(date);
		String[] dateTimeValues = datet.split("-");
		values.put("Emp_Id", queryValues.get("Emp_Id"));
		values.put("Track_LngLat", queryValues.get("Track_LngLat"));			
		values.put("Track_Date", dateTimeValues[0]);
		values.put("Track_Time", dateTimeValues[1]);
		values.put("Process_Ind", "N");	
		
		database.insert("EMP_TRACKED_LOC", null, values);
		database.close();
	}
	
	/**
	 * Get list of Emp_Loc_Tracker from SQLite DB as Array List
	 * @return
	 */

	/*
	public ArrayList<HashMap<String, String>> getAllEventData() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT Id, Emp_Id, Track_LngLat, Track_TimeStamp, Process_Ind FROM EMP_TRACKED_LOC";
		SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("Id", cursor.getString(0));
	        	map.put("LngLat", cursor.getString(1));
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
	    return wordList;
	}
	*/
	

	

	
/*
	public void updateSyncStatus(String id, String status){
		SQLiteDatabase database = this.getWritableDatabase();	 
		String updateQuery = "Update Emp_Loc_Tracker set Process_Ind = '"+ status +"' where Id="+"'"+ id +"'";
		Log.d("query",updateQuery);		
		database.execSQL(updateQuery);
		database.close();
	}
	
	public int deleteEntry(){	
		    int count = 0;
		    SQLiteDatabase database = this.getWritableDatabase();    
		    String selectQuery = "DELETE FROM Emp_Loc_Tracker ";
		    Cursor cursor = database.rawQuery(selectQuery, null);          
            Log.d("delete ",selectQuery);	
            count = cursor.getCount();
    	    database.close();
    		return count;
           
    }
    */
	
	public void postEmpLocTrackerValue() {
		HashMap<String, String>values = new HashMap<String, String>();
		
		Log.d("DBController: postEmpLocTrackerValue", "Start");
		
		SQLiteDatabase database = this.getWritableDatabase();
		String query;
		query = "BEGIN";
		database.execSQL(query);
		query = "Update EMP_TRACKED_LOC SET Process_Ind='X'";
		database.execSQL(query);
		Log.d("getEmpLocTrackerValue ","1After Update"+query);
		query = "Commit";
		database.execSQL(query);
		Log.d("getEmpLocTrackerValue ","2After Commit"+query);
		
	
		
		query = "SELECT Id, Emp_Id, Track_LngLat,Track_Date,Track_Time FROM EMP_TRACKED_LOC where Process_Ind = 'X'";
		// query = "SELECT Id, Employee_Id, LngLat, Process_Ind FROM Emp_Loc_Tracker";
		Cursor cursor = database.rawQuery(query, null);
		int count=0;
		Log.d("DBController: postEmpLocTrackerValue", "No. of records fetched with Process_Ind='X': "+cursor.getCount());
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {			
				values.put("Id", cursor.getString(0));
				values.put("Emp_Id", cursor.getString(1));				
				values.put("Track_LngLat", cursor.getString(2));
				values.put("Track_Date", cursor.getString(3));
				values.put("Track_Time", cursor.getString(4));

				Log.d("DBController: postEmpLocTrackerValue", "Uploading To MySQL; id="+cursor.getString(0));
				Log.d("DBController: postEmpLocTrackerValue", "Uploading To MySQL; EmpId="+cursor.getString(1));
				Log.d("DBController: postEmpLocTrackerValue", "Uploading To MySQL; Track_LngLat="+cursor.getString(2));
				Log.d("DBController: postEmpLocTrackerValue", "Uploading To MySQL; Tracker_Date="+cursor.getString(3));
				Log.d("DBController: postEmpLocTrackerValue", "Uploading To MySQL; Tracker_Time="+cursor.getString(4));
				// Permission StrictMode
				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				//PHP Insert 				  
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();      	
				//	nameValuePairs.add(new BasicNameValuePair("Id",cursor.getString(0)));
				nameValuePairs.add(new BasicNameValuePair("Emp_Id",cursor.getString(1)));
				nameValuePairs.add(new BasicNameValuePair("Track_LngLat",cursor.getString(2)));
				nameValuePairs.add(new BasicNameValuePair("Track_Date",cursor.getString(3)));
				nameValuePairs.add(new BasicNameValuePair("Track_Time",cursor.getString(4)));

				String insertEmpLocTrackerUrl="http://hawki-beta.gaddieltech.com/androidphp/insertEmpTrackedLoc.php";
				try{
					JSONParser jsonParser=new JSONParser();
					String json =jsonParser.makeHttpRequest(insertEmpLocTrackerUrl, "POST",nameValuePairs);
					Log.d("DBController: postEmpLocTrackerValue", " Response to the HTTPRequest: <"+json+">");
					
					if (json != null && json.length()>0 && json.charAt(0) == '1') {
						
					    query = "BEGIN";
						database.execSQL(query);
						
						/*query = "Update EMP_TRACKED_LOC SET Process_Ind='Y' WHERE Process_Ind='X' AND Id=" +cursor.getString(0) ;
						database.execSQL(query);
						Log.d("getEmpLocTrackerValue ","3After Update to Y"+query);*/
						
						query = "Delete from EMP_TRACKED_LOC WHERE Process_Ind='X' AND Id=" +cursor.getString(0);
						database.execSQL(query);
						Log.d("getEmpLocTrackerValue ","4After Delete"+query);
						
						query = "COMMIT";
						database.execSQL(query);
						Log.d("getEmpLocTrackerValue ","5After Commit"+query);
						
					} else {
						Log.d("DBController: postEmpLocTrackerValue", "Error: Insert into server database failed");
					}
				}
				catch (Exception e){
					Log.e("DBController: postEmpLocTrackerValue", "Json PHP Error "+e.toString());
					e.printStackTrace();
				}

				count++;
				Log.d("DBController: postEmpLocTrackerValue", "Record Count "+ count);
			} while (cursor.moveToNext());
		}
		if (cursor != null) { cursor.close(); }
		
		
		database.close();

	}
	

/*	
	public void syncSQLiteMySQLDB(){
		 SQLiteDatabase database = this.getWritableDatabase(); 
		String query;
		query = "Update Emp_Loc_Tracker SET Process_Ind='X'";
        database.execSQL(query);
        
        
        //Create AsycHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		ArrayList<HashMap<String, String>> eventData =  getAllEventData();
		if(eventData.size()!=0){
			if(dbSyncCount() != 0){
				prgDialog.show();
				params.put("usersJSON", composeJSONfromSQLite());
				//client.post("http://gaddieltech.com/absynstest/insertuser.php",params ,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						System.out.println(response);
						prgDialog.hide();
						try {
							JSONArray arr = new JSONArray(response);
							System.out.println(arr.length());
							for(int i=0; i<arr.length();i++){
								JSONObject obj = (JSONObject)arr.get(i);
								System.out.println(obj.get("Id"));
								System.out.println(obj.get("LngLat"));
								System.out.println(obj.get("Employee_Id"));
								updateSyncStatus(obj.get("Id").toString(),obj.get("LngLat").toString(),obj.get("Employee_Id").toString());
								deleteEntry();
							}
												    
						     	
						    	  
							//Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							//Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
					}
		    
					@Override
					public void onFailure(int statusCode, Throwable error,
						String content) {
						// TODO Auto-generated method stub
						prgDialog.hide();
						if(statusCode == 404){
							//Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
						}else if(statusCode == 500){
							//Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
						}else{
							//Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
						}
					}
				});
			}else{
				//Toast.makeText(getApplicationContext(), "SQLite and Remote MySQL DBs are in Sync!", Toast.LENGTH_LONG).show();
			}
		}else{
				//Toast.makeText(getApplicationContext(), "No data in SQLite DB, please do enter User name to perform Sync action", Toast.LENGTH_LONG).show();
		}
	}
   */
}
