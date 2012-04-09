package edu.crabium.android.geekclock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import org.geonames.Timezone;
import org.geonames.WebService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class TimeService extends Service {
	private boolean isSynchronized = false;
	private double latitude;
	private double longitude;
	private String placeName;
	private double utc;
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

	public void syncTime(){
		try{
	        DatagramSocket socket = new DatagramSocket();
	        SettingProvider sp = SettingProvider.getInstance();
	        InetAddress address = InetAddress.getByName(sp.getSetting(SettingProvider.CHOSEN_SREVER_ADDRESS));
	        byte[] buf = new NtpMessage().toByteArray();
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
	        socket.send(packet);
	         
	        // Get response
	        socket.receive(packet);
			NtpMessage msg = new NtpMessage(packet.getData());
	        
			utc = msg.toUTC();
        }
        catch(Exception e){
        	Date date = new Date();
        	utc = date.getTime()/1000;
        }        
	}
	@SuppressWarnings("deprecation")
	public double getTimeZone() {
		try {
			SettingProvider sp = SettingProvider.getInstance();
			
			WebService.setUserName(sp.getSetting(SettingProvider.GEONAMES_USER_NAME));
			//TODO: change timezone to java.util.TimeZone
			Timezone tmz = WebService.timezone(latitude, longitude);
			placeName = WebService.findNearbyPlaceName(latitude, longitude).iterator().next().getName();
			return tmz.getGmtOffset();
		}
		catch (Exception e) {
			Log.d("GeekClock", "Error: unexpected exception");
			e.printStackTrace();
			return 8.0;
		}
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
