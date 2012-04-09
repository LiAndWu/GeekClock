package edu.crabium.android.geekclock;

import java.util.Date;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
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

	public class TimeServiceBinder extends Binder {
		TimeService getService() {
			return TimeService.this;
		}
	}

	@Override
	public void onCreate() {
		isSynchronized = false;
		longitude = 120.33820867538452;
		latitude = 30.31900699227783;

		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(serviceName);
		String provider = LocationManager.GPS_PROVIDER;

		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null){
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}

		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
	}

	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	public double getLatitude() {
		return 0;
	}

	public double getLongitude() {
		return 0;
	}

	public int getTimeZone() {
		return 0;
	}

	public String getPlaceName() {
		return "";
	}

	public long getTimeSeconds() {
		if (isSynchronized) {
			// TODO: modify these code to adapt to TimeService
			/*
			 * if(GetTimezone(latitude,longitude) == -1) TimezoneError = true;
			 * else TimezoneError = false; if(GetUTC() == -1) UTCError = true;
			 * else UTCError = false;
			 * 
			 * Synchronized = true; TimeOffset = ((long)UTC + LocalTimezone +
			 * (long)CurrentTimezone*60*60) - date.getTime()/1000;
			 * 
			 * return date.getTime()/1000 + TimeOffset;
			 */

			return 0;
		} else {
			Date date = new Date();
			return date.getTime() / 1000;
		}
	}

	public boolean isSynchronized() {
		return this.isSynchronized;
	}
}
