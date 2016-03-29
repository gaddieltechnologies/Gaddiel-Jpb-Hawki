package com.gaddiel.hawki;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartAlarmReceiver extends BroadcastReceiver{
	
	@Override
	 public void onReceive(Context context, Intent myIntent)
	{
	   Log.d("StartAlarmReceiver: onReceive", "Start");      
	   Intent intents = new Intent(context, StartAlarmService.class);
	   
	   
	   context.startService(intents);
	   Log.d("StartAlarmReceiver: onReceive", "End");
	 }

}
