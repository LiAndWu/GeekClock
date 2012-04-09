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
import android.widget.TextView;

public class MoreActivity extends Activity {
	private static TextView showTimeZone;
	private static TextView showLongitude;
	private static TextView showLatitude;
	private static TextView showCity;
	private static TextView showWeek;
	private static TextView showYearDay;
	
	private TimeService timeService;
	private boolean timeServiceBound;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.more);

        showTimeZone = (TextView) findViewById(R.id.showTimeZone);
        showCity = (TextView) findViewById(R.id.showCity);
        showWeek = (TextView) findViewById(R.id.showWeek);
        showYearDay = (TextView) findViewById(R.id.ShowYearDay);
        showLongitude = (TextView) findViewById(R.id.showLongitude);  
        showLatitude = (TextView) findViewById(R.id.showLatitude);
    }

    @Override
    protected void onResume(){
    	super.onResume();
    	if(timeServiceBound){
    		showInfo();
    	}else{
    		showTimeZone.setText(R.string.time_synchronizing);
            showCity.setText(R.string.time_synchronizing);
            showWeek.setText(R.string.time_synchronizing);
            showYearDay.setText(R.string.time_synchronizing);
            showLongitude.setText(R.string.time_synchronizing);
            showLatitude.setText(R.string.time_synchronizing);
    	}
    }
    
    private void showInfo(){
        String timeZone, week, yearDay; 
        SimpleDateFormat weekFormat = new SimpleDateFormat("E");
        SimpleDateFormat yearDayFormat = new SimpleDateFormat("今年第D天");
        
        int _timeZone = 8;//(int)timeService.getTimeZone();
        timeZone = "UTC" + ((_timeZone > 0) ? "+" : "") + _timeZone;

        long timeSeconds = timeService.getTimeSeconds();
        week = weekFormat.format(new Date(timeSeconds * 1000));
        yearDay = yearDayFormat.format(new Date(timeSeconds * 1000));
        
        showTimeZone.setText(timeZone);
        showCity.setText(timeService.getPlaceName());
        showWeek.setText(week);
        showYearDay.setText(yearDay);
        showLongitude.setText(Double.toString(timeService.getLongitude()));
        showLatitude.setText(Double.toString(timeService.getLatitude()));
    	
    }
    @Override
    protected void onStart(){
    	super.onStart();
    	Intent intent = new Intent(this, TimeService.class);
    	this.getApplicationContext().bindService(intent, timeServiceConnection, Context.BIND_AUTO_CREATE);
    	
    	new Thread(
    			new Runnable(){
    				public void run(){
    					int times = 10;
    					while(times -- > 0){
    						if(!timeServiceBound){
    							try {
									TimeUnit.MILLISECONDS.sleep(200);
									System.out.println("trying");
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
    						}
    						else{
    							runOnUiThread(new Runnable(){
    								public void run(){
    	    							showInfo();
    									System.out.println("gocha");
    								}
    							});
    							return;
    						}
    					}
    				}
    			}
    			).start();
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

