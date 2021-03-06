package com.gaddiel.hawki;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class EmpTrackScheduleReceiver extends BroadcastReceiver {

	DBController controller;
	Context context;
	String loc;

	// Use global variables for the start time and end time, because passing the values using
	// Intent Bundle is not always working
	public static String globalTrackStartTime = null;
	public static String globalTrackEndTime = null;
	
	@Override
	public void onReceive(Context context, Intent myIntent) {
		this.context = context;
		Log.d("EmpTrackScheduleReceiver: onReceive", "Start");
		controller = new DBController(context);
		
	
		// Change 1
		//Intent intents = new Intent(context, EmpTrackScheduleService.class);
				
		//HawkIUtils.dumpIntent(myIntent);
		
		 
	     
		 //dumpIntent(myIntent);
		/********* Remove because the passing of values using Intent is not working 
		*******/
	     
		 Bundle extras = myIntent.getExtras();
	     String trackStartTime = extras.getString("trackStartTime");
	      
		 String trackEndTime = extras.getString("trackEndTime");
		
		 Log.i("EmpTrackScheduleReceiver: onReceive", "trackStartTime: "+trackStartTime);
		 Log.i("EmpTrackScheduleReceiver: onReceive", "trackEndTime: "+trackEndTime);	
		
	     Log.i("EmpTrackScheduleReceiver: onReceive", "globalTrackStartTime: "+globalTrackStartTime);
		 Log.i("EmpTrackScheduleReceiver: onReceive", "globalTrackEndTime: "+globalTrackEndTime);
		 
		 if (globalTrackStartTime == null || globalTrackEndTime == null) {
			 Log.e("EmpTrackScheduleReceiver: onReceive", "Error: globalTrackStartTime or globalTrackEndTime is null");
			 Log.e("EmpTrackScheduleReceiver: onReceive", "Using the Bundle values"); 
		 } else {  // use the global trackstarttime and trackendtime
			 trackStartTime = globalTrackStartTime; 
			 trackEndTime = globalTrackEndTime;		 
		 }
		 
		 
		// Checking if the alarm needs to execute
		if (HawkIUtils.isTimeWithinBoundary(trackStartTime, trackEndTime)) {
			Log.d("EmpTrackScheduleReceiver: onReceive",
			" Current time within boundary, updating location information");
			
			doTask(trackStartTime, trackEndTime);
		} else if (HawkIUtils.isCurrentTimeBeforeStartTime(trackStartTime, trackEndTime)){ 
			Log.d("EmpTrackScheduleReceiver: onReceive",
					"Current time before tracker start time. Not tracking the location information");
			
		} else {
			Log.d("EmpTrackScheduleReceiver: onReceive",
			"Current time OUTSIDE boundary. Canceling the tracker alarm");
			
			Intent myIntentTest = new Intent(context,
					EmpTrackScheduleReceiver.class);
			PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(
					context, 100, myIntentTest,
					PendingIntent.FLAG_NO_CREATE);
			boolean alarmUp = pendingUpdateIntent != null;
			
			// New code
			//Bundle nullBundle = null;
			//myIntentTest.replaceExtras(nullBundle);		
			
			if (alarmUp) {
				try {
					AlarmManager alarmManager = (AlarmManager) context.getSystemService(android.app.Service.ALARM_SERVICE);
					alarmManager.cancel(pendingUpdateIntent);
					Log.d("EmpTrackScheduleReceiver", "Canceled the Alarm");

				} catch (Exception e) {
					Log.e("EmpTrackScheduleReceiver Error",
							"EmpTrackScheduleReceiver is not canceled. "
									+ e.toString());
				}
			} else {
				// Shouldnt come here
			}
			
		}
		
		Log.d("EmpTrackScheduleReceiver: onReceive", "End");

	}

	



	private void doTask(String trackStartTime, String trackEndTime) {
		Log.d("EmpTrackScheduleReceiver: doTask", "Start");
		
		Log.d("EmpTrackScheduleReceiver: doTask",
		" Current time within boundary, updating location information");
		Location location;
		LocationManager locationManager;
		String provider;
		ConnectionDetector cd;

		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		provider = locationManager.getBestProvider(criteria, true);
		location = locationManager.getLastKnownLocation(provider);
		locationManager.requestLocationUpdates(provider, 400, 1,
				locationListener);		
		

		addLocInEmpLocTracker(location);
		// Check if Internet present
		cd = new ConnectionDetector(context);
		if (cd.isConnectingToInternet()) {
			try {
				Log.d("EmpTrackScheduleReceiver: doTask",
				"Internet Found - Post the values to the server");

				SQLiteoperationCustomerVisit customerVisitSqlOperation = new SQLiteoperationCustomerVisit(
						context);

				controller.postEmpLocTrackerValue();
				customerVisitSqlOperation.putEmpCustVisitValue();
			} catch (Exception e) {
				Log.e("EmpTrackScheduleReceiver: doTask Exception Error",
						"server Not found  " + e.toString());
			}
		}

		Log.d("EmpTrackScheduleReceiver: doTask", "End");
		
	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider) {			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {			
		}
	};

	private String updateWithNewLocation(Location location) {
		// TextView myLocationText =
		// (TextView)findViewById(R.id.myLocationText);

		String latLongString;

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			// latLongString = "Location"+lat+","+lng;
			latLongString = Double.toString(lat) + "," + Double.toString(lng);

		} else {
			latLongString = "NULL,NULL";
		}
		// Log.i("Location",
		// latLongString);"No location found"+";"+"No Address found"

		// myLocationText.setText("My Current Position is:\n" + latLongString);
		return latLongString;
	}
	
	public void addLocInEmpLocTracker(Location location) {
		
		loc = updateWithNewLocation(location);
		Log.d("EmpTrackScheduleReceiver: addLocInEmpLocTracker", "Start");
		SQLiteoperationEmp sqloperationEmp = new SQLiteoperationEmp(context);

		HashMap mapEmp = sqloperationEmp.getEmpValue();
		String Employee_Id = (String) mapEmp.get("Emp_Id");

		HashMap<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("Track_LngLat", loc);
		queryValues.put("Emp_Id", Employee_Id);

		controller.insertEmpLocTracker(queryValues);
		Log.d("EmpTrackScheduleReceiver: addLocInEmpLocTracker",
				"Location LngLat insert " + loc);
		Log.d("EmpTrackScheduleReceiver: addLocInEmpLocTracker",
				"Location Emp insert " + Employee_Id);
		Log.d("EmpTrackScheduleReceiver: addLocInEmpLocTracker", "Done");
		
		
	}
}
