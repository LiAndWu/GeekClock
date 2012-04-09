package edu.crabium.android.geekclock;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TimeService extends Service {
	private boolean isSynchronized = false;
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
	}

	public double getLatitude(){
		return 0;
	}
	
	public double getLongitude(){
		return 0;
	}
}
