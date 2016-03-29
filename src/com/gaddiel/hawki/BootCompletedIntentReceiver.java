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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
 
public class BootCompletedIntentReceiver extends BroadcastReceiver {
	 @Override
	 public void onReceive(Context context, Intent intent) {
	  if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
		  BackgroundTasks bg = new BackgroundTasks(context);
		  bg.execute();
	  }
	 }
}