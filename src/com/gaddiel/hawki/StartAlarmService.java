package com.gaddiel.hawki;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class StartAlarmService extends Service {

	public int onStartCommand(Intent intents, int flags, int startId) {
		super.onStartCommand(intents, flags, startId);

		Log.d("StartAlarmService: onStartCommand", "Start");
		// Permission StrictMode
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

		if (cd.isConnectingToInternet()) {
			Log.d("StartAlarmService: onStartCommand", "Internet Found, syncing database");
			HawkIUtils.synchSQLiteScheduleFromDB(getApplicationContext());
		}

		startempTrackScheduleService();

		Log.d("StartAlarmService: onStartCommand", "End");
		return START_REDELIVER_INTENT;
	}// onStartCommand


	private void startempTrackScheduleService() {

		Log.d("StartAlarmService: startempTrackScheduleService", "Start");
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		Date sysDate = new Date(System.currentTimeMillis());
		String dayOfWeek = sdf.format(sysDate);

		String starttimekey = dayOfWeek + "_StartTime";
		String endtimekey = dayOfWeek + "_EndTime";
		String timeintervalkey = dayOfWeek + "_TimeInterval";
		SQLiteOperations sqloperation = new SQLiteOperations(getApplicationContext());
		HashMap resultMap = sqloperation.getAllValues();
		String startTime = (String) resultMap.get(starttimekey);
		String intervalTime = (String) resultMap.get(timeintervalkey);
		// Start time is of format HH:mi
		Log.d("StartAlarmService: startempTrackScheduleService", starttimekey);
		Log.d("StartAlarmService: startempTrackScheduleService", endtimekey);
		Log.d("StartAlarmService: startempTrackScheduleService", timeintervalkey);

		Log.d("StartAlarmService: startempTrackScheduleService", startTime);
		Log.d("StartAlarmService: startempTrackScheduleService", intervalTime);
		String[] startTimeValues = startTime.split(":");
		Log.d("StartAlarmService: startempTrackScheduleService", "Hours: "+startTimeValues[0]);
		Log.d("StartAlarmService: startempTrackScheduleService", "Minutes: "+startTimeValues[1]);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeValues[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(startTimeValues[1]));
		calendar.set(Calendar.SECOND, 0);
		
		//Log.d("StartAlarmService: startempTrackScheduleService", "Removing the old Bundle");
		
		// Debug code 
		// HawkIUtils.dumpIntent(myIntentTest);
		
		// New code
		//Bundle nullBundle = null;
		//myIntentTest.replaceExtras(nullBundle);		
		
		// Cancel the alarm always before starting another one
		Intent myIntentTest = new Intent(this.getApplicationContext(), EmpTrackScheduleReceiver.class);
		PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 100, myIntentTest, PendingIntent.FLAG_NO_CREATE);
				
		Log.d("StartAlarmService: startempTrackScheduleService", "Canceling the EmpTrackScheduleService alarm");
		try {
			AlarmManager alarmManager = (AlarmManager) this.getApplicationContext().getSystemService(android.app.Service.ALARM_SERVICE);
			alarmManager.cancel(pendingUpdateIntent);
			
			Log.d("StartAlarmService: startempTrackScheduleService", "Canceled the EmpTrackScheduleService Alarm");
		} catch (Exception e) {
			Log.e("StartAlarmService: startempTrackScheduleService Error",
					"EmpTrackScheduleReceiver is not canceled. "
							+ e.toString());
		}
						
		Log.d("StartAlarmService: startempTrackScheduleService", "EmpTrackScheduleService alarm is canceled; Creating it");
		
		// Fill the relevant values in the intent
		String trackStartTime = HawkIUtils.getTimeAsLongString((String) resultMap
				.get(starttimekey));
		String trackEndTime = HawkIUtils.getTimeAsLongString((String) resultMap
				.get(endtimekey));
		
		//HawkIUtils.dumpIntent(myIntentTest);
		
		Log.i("StartAlarmService: startempTrackScheduleService", "trackStartTime: "+trackStartTime);
		Log.i("StartAlarmService: startempTrackScheduleService", "trackEndTime: "+trackEndTime);
		
		myIntentTest.putExtra("trackStartTime", trackStartTime);
		myIntentTest.putExtra("trackEndTime", trackEndTime);
		
		//HawkIUtils.dumpIntent(myIntentTest);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100,
				myIntentTest, 0);

		long num = Long.parseLong(intervalTime);
		long timeval = num * 60000;//1 Min = 60000 Milliseconds
		Log.d("StartAlarmService: startempTrackScheduleService", "interval timeval=" + timeval);

		// Setting the trackStartTime and trackEndTime as global variables since 
		// passing values using Intent is not working
		EmpTrackScheduleReceiver.globalTrackStartTime = trackStartTime;
		EmpTrackScheduleReceiver.globalTrackEndTime = trackEndTime;
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 
				timeval, pendingIntent);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
