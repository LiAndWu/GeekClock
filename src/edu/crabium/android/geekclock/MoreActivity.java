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
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;

public class MoreActivity extends Activity {
	private TextView showTimeZone;
	private TextView showLongitude;
	private TextView showLatitude;
	private TextView showCity;
	private TextView showWeek;
	private TextView showYearDay;
	
	private TimeService timeService;
	private boolean timeServiceBound;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.more);

        showTimeZone = 	(TextView) findViewById(R.id.showTimeZone);
        showCity = 	(TextView) findViewById(R.id.showCity);
        showWeek = 	(TextView) findViewById(R.id.showWeek);
        showYearDay = 	(TextView) findViewById(R.id.ShowYearDay);
        showLongitude = 	(TextView) findViewById(R.id.showLongitude);  
        showLatitude = 	(TextView) findViewById(R.id.showLatitude);
    }

    @Override
    protected void onResume(){
    	super.onResume();
    	
    	new Thread(new informationUpdate()).start();
    }

    private int timeSynchronized = 1;
    private int timeNotSynchronized = 2;
    
    private class informationUpdate implements Runnable{
    	public void run(){
			while(true){
				if(timeServiceBound && timeService.timeSynchronized()){
					Message message = new Message();
					message.what = timeSynchronized;
					informationUpdateHandler.sendMessage(message);
				}
				else{
					Message message = new Message();
					message.what = timeNotSynchronized;
					informationUpdateHandler.sendMessage(message);
				}
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
    }

    private Handler informationUpdateHandler = new Handler(){
    	public void handleMessage(Message message){
    		super.handleMessage(message);
    		
    		if(message.what == timeSynchronized){
	            int _timeZone = timeService.getTimeZone();
	            String timeZone = "UTC" + ((_timeZone > 0) ? "+" : "") + _timeZone;
	            
	            showTimeZone.setText(timeZone);
	            showCity.setText(timeService.getPlaceName());
	            showWeek.setText(new SimpleDateFormat("E").format(new Date(timeService.getTimeSeconds() * 1000)));
	            showYearDay.setText(new SimpleDateFormat("今年第D天").format(new Date(timeService.getTimeSeconds() * 1000)));
	            showLongitude.setText(Double.toString(timeService.getLongitude()));
	            showLatitude.setText(Double.toString(timeService.getLatitude()));
	    	}
	    	else{
	    		showTimeZone.setText(R.string.time_synchronizing);
	            showCity.setText(R.string.time_synchronizing);
	            showWeek.setText(R.string.time_synchronizing);
	            showYearDay.setText(R.string.time_synchronizing);
	            showLongitude.setText(R.string.time_synchronizing);
	            showLatitude.setText(R.string.time_synchronizing);
	    	}
    	}
    };
    	
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
}

