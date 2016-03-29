package com.gaddiel.hawki;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HawkIUtils {
	
	public static final SimpleDateFormat sdfw = new SimpleDateFormat("HHmm");

	public static void synchSQLiteScheduleFromDB(Context applicationContext) {
		Log.d("StartAlarmService: synchSQLiteScheduleFromDB", "Start");
		JSONParser jsonParser = new JSONParser();

		SQLiteoperationEmp sqloperationEmp = new SQLiteoperationEmp(
				applicationContext);

		HashMap<String, String> mapEmp = sqloperationEmp.getEmpValue();
		String Emp_Id = (String) mapEmp.get("Emp_Id");
		String getEmpTrackerScheduleUrl = "http://hawki-beta.gaddieltech.com/androidphp/getEmpTrackerSch.php";
		final ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("Emp_Id", Emp_Id));
			Log.d("Empnum: ", ">>>" + Emp_Id);
			String json = jsonParser.makeHttpRequest(getEmpTrackerScheduleUrl,
					"POST", nameValuePairs);
			Log.d("Response: ", ">>>" + json);

			HashMap<String, String> map;

			JSONObject jsonResponse = new JSONObject(json);
			JSONArray jsonMainNode = jsonResponse.getJSONArray("ListOfArray");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject c = jsonMainNode.getJSONObject(i);

				map = new HashMap<String, String>();
				map.put("Sun_StartTime", c.getString("SUN_STARTTIME"));
				map.put("Sun_EndTime", c.getString("SUN_ENDTIME"));
				map.put("Sun_TimeInterval", c.getString("SUN_TIMEINTERVAL"));
				map.put("Mon_StartTime", c.getString("MON_STARTTIME"));
				map.put("Mon_EndTime", c.getString("MON_ENDTIME"));
				map.put("Mon_TimeInterval", c.getString("MON_TIMEINTERVAL"));
				map.put("Tue_StartTime", c.getString("TUE_STARTTIME"));
				map.put("Tue_EndTime", c.getString("TUE_ENDTIME"));
				map.put("Tue_TimeInterval", c.getString("TUE_TIMEINTERVAL"));
				map.put("Wed_StartTime", c.getString("WED_STARTTIME"));
				map.put("Wed_EndTime", c.getString("WED_ENDTIME"));
				map.put("Wed_TimeInterval", c.getString("WED_TIMEINTERVAL"));
				map.put("Thu_StartTime", c.getString("THU_STARTTIME"));
				map.put("Thu_EndTime", c.getString("THU_ENDTIME"));
				map.put("Thu_TimeInterval", c.getString("THU_TIMEINTERVAL"));
				map.put("Fri_StartTime", c.getString("FRI_STARTTIME"));
				map.put("Fri_EndTime", c.getString("FRI_ENDTIME"));
				map.put("Fri_TimeInterval", c.getString("FRI_TIMEINTERVAL"));
				map.put("Sat_StartTime", c.getString("SAT_STARTTIME"));
				map.put("Sat_EndTime", c.getString("SAT_ENDTIME"));
				map.put("Sat_TimeInterval", c.getString("SAT_TIMEINTERVAL"));

				myArrList.add(map);

			}
		}// try
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("Exception", "Exception is: " + e.getMessage());
			e.printStackTrace();
		}// catch

		// Insert in Sqlite from myArrList (Calling Method)
		Log.d("InsertTo DB", "insertToDB");
		insertToDB(applicationContext, myArrList);

	}

	// Insert in Sqlite from myArrList (Method)
	public static void insertToDB(Context applicationContext,
			ArrayList myArrList) {

		SQLiteOperations sqloperation = new SQLiteOperations(applicationContext);

		// myArrList will always be of size 1
		if (myArrList.size() != 1) {
			// Error
			Log.d("myArrList", "Null");
			return;
		}
		HashMap<String, String> dataMap = (HashMap) myArrList.get(0);
		sqloperation.insert(dataMap);

	}
	
	public static boolean isTimeWithinBoundary(String trackStartTime,
					String trackEndTime) {
		Log.d("HawkIUtils: isTimeWithinBoundary", "Start");
		Date sysDatew = new Date(System.currentTimeMillis());
		String currentTime = sdfw.format(sysDatew);
		Log.d("HawkIUtils: isTimeWithinBoundary", "currentTime:"
				+ currentTime + ", trackStartTime:" + trackStartTime
				+ ", trackEndTime:" + trackEndTime);
		if (Long.parseLong(currentTime) >= Long.parseLong(trackStartTime)
				&& Long.parseLong(currentTime) <= Long.parseLong(trackEndTime)) {
			Log.d("HawkIUtils: isTimeWithinBoundary", "Returning true");
			return true;
		}
		Log.d("HawkIUtils: isTimeWithinBoundary", "Returning false");
		Log.d("HawkIUtils: isTimeWithinBoundary", "Done");
		return false;
	}
	
	public static boolean isCurrentTimeBeforeStartTime(String trackStartTime,
			String trackEndTime) {
		Log.d("HawkIUtils: isCurrentTimeBeforeStartTime", "Start");
		Date sysDatew = new Date(System.currentTimeMillis());
		String currentTime = sdfw.format(sysDatew);
		Log.d("HawkIUtils: isCurrentTimeBeforeStartTime", "currentTime:"
				+ currentTime + ", trackStartTime:" + trackStartTime
				+ ", trackEndTime:" + trackEndTime);
		if (Long.parseLong(currentTime) <= Long.parseLong(trackStartTime)) {
			Log.d("HawkIUtils: isCurrentTimeBeforeStartTime", "Returning true");
			return true;
		}
		Log.d("HawkIUtils: isCurrentTimeBeforeStartTime", "Returning false");
		Log.d("HawkIUtils: isCurrentTimeBeforeStartTime", "Done");
		return false;
	}
	
	/**
	 * This method returns an input String of type "21:30" to "2130" 
	 * @param timeVal
	 * @return
	 */
	public static String getTimeAsLongString(String timeVal) {
		String[] startTimeValues = timeVal.split(":");
		return (startTimeValues[0] + startTimeValues[1]);
	}

	/**
	 * This method can be used to dump intent values
	 * @param i
	 */
	public static void dumpIntent(Intent i){
	    Bundle bundle = i.getExtras();
	    if (bundle != null) {
	        Set<String> keys = bundle.keySet();
	        Iterator<String> it = keys.iterator();
	        Log.d("HawkIUtils: dumpIntent","Dumping Intent start");
	        while (it.hasNext()) {
	            String key = it.next();
	            Log.d("HawkIUtils: dumpIntent","[" + key + "=" + bundle.get(key)+"]");
	        }
	        Log.d("HawkIUtils: dumpIntent","Dumping Intent end");
	    }
	}

	
}