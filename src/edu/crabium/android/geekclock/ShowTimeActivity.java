package edu.crabium.android.geekclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.crabium.android.geekclock.R;
import edu.crabium.android.geekclock.TimeService.TimeServiceBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTimeActivity extends Activity { 
    private TextView timeTextView;
    private TextView dateTextView;
	private ImageView clockImageView = null;
	private static TimeService timeService;
	private static boolean timeServiceBound = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.showclock);

        clockImageView = (ImageView) findViewById(R.id.imageView);
        timeTextView = (TextView) findViewById(R.id.showTime);  
        dateTextView = (TextView) findViewById(R.id.showDate);   
        
        new ClockRepaintThread().start();	 
    }
    
    @Override
    protected void onStart(){
    	super.onStart();
    	Intent intent = new Intent(this, TimeService.class);
    	this.getApplicationContext().bindService(intent, timeServiceConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    	if(timeServiceBound){
    		this.getApplicationContext().unbindService(timeServiceConnection);
    		timeServiceBound = false;
    	}
    }
    
    private ServiceConnection timeServiceConnection = new ServiceConnection(){
    	@Override
    	public void onServiceConnected(ComponentName className, IBinder service){
    		TimeServiceBinder binder = (TimeServiceBinder) service;
    		timeService = binder.getService();
    		timeServiceBound = true;
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName arg0){
    		timeServiceBound = false;
    	}
    };

    private class ClockRepaintThread extends Thread {
    	@Override
		public void run() {
    	    boolean synchronizeInfoNotified = false;
    		while (true) {
	    		try {
	    			drawClock();
	    			if(!synchronizeInfoNotified && timeServiceBound && timeService.timeSynchronized()){
	    					runOnUiThread(new Runnable(){
	    						public void run(){
	    							Toast.makeText(ShowTimeActivity.this, "时间已同步", Toast.LENGTH_SHORT).show();
	    						}
	    					});
	    					synchronizeInfoNotified = true;
	    			}
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    	private void drawClock(){
		    runOnUiThread(new Runnable(){
		    	public void run(){
		    		Date date = new Date();
		    		long timeSeconds = timeServiceBound ? timeService.getTimeSeconds() : date.getTime()/1000;
		    		
				    timeTextView.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(timeSeconds * 1000)));  
				    dateTextView.setText(new SimpleDateFormat("yyyy年M月d日").format(new Date(timeSeconds * 1000)));
		    		ClockDrawer clockdrawer = new ClockDrawer(ShowTimeActivity.this);
		    		clockdrawer.Draw(clockImageView, timeSeconds );
		    	}
		    });
		}
    }
}
