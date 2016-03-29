package com.gaddiel.hawki;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.media.MediaPlayer;

public class GaddielHawkiSplashScreen extends Activity{
  //  public  MediaPlayer splashSounds;
 Handler mHandler;
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   requestWindowFeature(Window.FEATURE_NO_TITLE);
		   setContentView(R.layout.splashscreen);
          // splashSounds = MediaPlayer.create(GaddielHawkiSplashScreen.this, R.raw.intro);
         // splashSounds.start();
		   Thread splashThread = new Thread() {
		         @Override
		         public void run() {
		            try {
		               int waited = 0;
		               while (waited < 500) {
		                  sleep(30);
		                  waited += 30;
		               }
		            } catch (InterruptedException e) {
		               // do nothing
		            } finally {
		               finish();
		               Intent i = new Intent(GaddielHawkiSplashScreen.this,MainActivity.class);
		               startActivity(i);
		            }
		         }
		      };
		      splashThread.start();
		   
	    }

    @Override
    protected void onPause() {

        super.onPause();
      //  splashSounds.stop();
      //  splashSounds.release();
        finish();
    }
}

