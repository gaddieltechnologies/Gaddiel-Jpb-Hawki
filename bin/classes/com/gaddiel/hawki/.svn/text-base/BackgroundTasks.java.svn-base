package com.gaddiel.hawki;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

public class BackgroundTasks extends AsyncTask<String, Void, String> {
	// public class BackgroundTasks extends AsyncTask {
	private Context applicationContext;
	// Alarm pending Intent
	private PendingIntent pendingIntent;

	public BackgroundTasks(Context applicationContext) {
		super();
		this.applicationContext = applicationContext;
	}

	@Override
	protected String doInBackground(String... params) {
		Log.d("BackgroundTasks: doInBackground", "Start");
		try {

			setupDataSyncAlarm();

		} catch (Exception e) {
			Log.e("LongOperation", "Interrupted", e);
			return "Interrupted";
		}
		Log.d("BackgroundTasks: doInBackground", "End");
		return "Executed";

	}

	private void setupDataSyncAlarm() {
		Log.d("BackgroundTasks: setupDataSyncAlarm", "Start");
		
		Intent myIntentTest = new Intent(applicationContext,
				StartAlarmReceiver.class);

		boolean alarmUp = (PendingIntent.getBroadcast(applicationContext,
				0, myIntentTest, PendingIntent.FLAG_NO_CREATE) != null);

		boolean isServiceRunning = false;
		if (alarmUp) {
			Log.d("StartAlarmService", "StartAlarmService is already active");
			isServiceRunning = true;
		} else {
			Log.d("MainActivity: StartAlarmService",
					"StartAlarmService is NOT already active");
			isServiceRunning = false;
		}
		if (!isServiceRunning) {
			// Set the 1st alarm
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 1);
			calendar.set(Calendar.SECOND, 0);
	
			Log.d("BackgroundTasks: setupDataSyncAlarm", "Starting the StartAlarmService");
	
			Intent myIntent = new Intent(applicationContext,
					StartAlarmReceiver.class);
	
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					applicationContext, 0, myIntent, 0);
	
			AlarmManager alarmManager = (AlarmManager) applicationContext
					.getSystemService(Context.ALARM_SERVICE);
			
			// Force the StartAlarm service to run once -- to sync up DB and start EmpTrackScheduleService
			Log.d("BackgroundTasks: setupDataSyncAlarm", "Starting the StartAlarmService to run NOW");
			alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
			
			// Run the StartAlarmService at every midnight
			Intent myIntentMidNight = new Intent(applicationContext,
					StartAlarmReceiver.class);
			PendingIntent myPendingIntentMidNight = PendingIntent.getBroadcast(
					applicationContext, 0, myIntentMidNight, 0);
	
			Log.d("BackgroundTasks: setupDataSyncAlarm", "Starting the StartAlarmService to run at midnight, "+
						"in millis: "+calendar.getTimeInMillis());
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, myPendingIntentMidNight);
			
		}
		Log.d("BackgroundTasks: setupDataSyncAlarm", "End");
	}

	/*
	 * private void setupEmpTrackScheduleAlarm() {
	 * Log.d("StartAlarmService: startempTrackScheduleService", "Start");
	 * SimpleDateFormat sdf = new SimpleDateFormat("EEE"); Date sysDate = new
	 * Date(System.currentTimeMillis()); String dayOfWeek = sdf.format(sysDate);
	 * 
	 * String starttimekey = dayOfWeek + "_StartTime"; String endtimekey =
	 * dayOfWeek + "_EndTime"; String timeintervalkey = dayOfWeek +
	 * "_TimeInterval";
	 * 
	 * HashMap resultMap = sqloperation.getAllValues(); String startTime =
	 * (String) resultMap.get(starttimekey); String intervalTime = (String)
	 * resultMap.get(timeintervalkey); // Start time is of format HH:mi // Set
	 * the 2nd alarm Calendar calendar = Calendar.getInstance();
	 * calendar.setTimeInMillis(System.currentTimeMillis());
	 * calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeValues[0]));
	 * calendar.set(Calendar.MINUTE, Integer.parseInt(startTimeValues[1]));
	 * calendar.set(Calendar.SECOND, 0);
	 * 
	 * Log.d("StartAlarmService",
	 * "Moving to empTrackScheduleService Calendar Passed ");
	 * 
	 * Intent myIntent = new Intent(this.getApplicationContext(),
	 * EmpTrackScheduleReceiver.class); myIntent.putExtra("timekey",
	 * intervalTime); // pendingIntent = //
	 * PendingIntent.getBroadcast(this.getApplicationContext(), //
	 * Integer.parseInt(Emp_Id.toString()), myIntent,0);
	 * 
	 * pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
	 * myIntent, 0); Log.d("Moving to myreceiver", "timeintervalkeyValue=" +
	 * intervalTime); long num = Long.parseLong(intervalTime); timeval = num *
	 * 60000;// 1 Min = 60000 Milliseconds
	 * 
	 * AlarmManager alarmManager = (AlarmManager)
	 * getSystemService(ALARM_SERVICE);
	 * alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
	 * calendar.getTimeInMillis(), timeval, pendingIntent); }
	 */

	@Override
	protected void onPostExecute(String result) {
		// TextView txt = (TextView) findViewById(R.id.output);
		// txt.setText(result);
	}
}