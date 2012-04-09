package edu.crabium.android.geekclock;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TimeService extends Service {
	private boolean isSynchronized = false;
	private double latitude;
	private double longitude;
	private final IBinder timeServiceBinder = new TimeServiceBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return timeServiceBinder;
	}
	
	public class TimeServiceBinder extends Binder{
		TimeService getService(){
			return TimeService.this;
		}
	}
	
	@Override
	public void onCreate(){
		isSynchronized = false;
		longitude = 120.33820867538452;
		latitude = 30.31900699227783;
	}

	public double getLatitude(){
		return 0;
	}
	
	public double getLongitude(){
		return 0;
	}
	
	public int getTimeZone(){
		return 0;
	}
	
	public String getPlaceName(){
		return "";
	}
	
	public long getTimeSeconds(){
		if(isSynchronized){
			return 0;
		}
		else{
			Date date = new Date();
			return date.getTime()/ 1000;
		}
	}
	
	public boolean isSynchronized(){
		return this.isSynchronized;
	}
}
