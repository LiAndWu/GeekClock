package edu.crabium.android.geekclock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
	private boolean utcTimeSynchronized = false;
	private boolean localTimeZoneSynchronized = false;
	private boolean timeSynchronized = false;
	private String placeName;
	private double latitude;
	private double longitude;
	private long timeOffset;
	private double timeZone;
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
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("GeekClock", "Service stared");
		utcTimeSynchronized = false;
		longitude = 120.33820867538452;
		latitude = 30.31900699227783;
		timeOffset = 0;
		
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
		
		new Thread(new Runnable(){
			public void run(){
				int times = 10;
				while(times -- > 0){
					synchronizeTime();
					if(utcTimeSynchronized)
						break;
					else
						try {
							TimeUnit.MILLISECONDS.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}}).start();
		
		new Thread(new Runnable(){
			public void run(){
				int times = 10;
				while(times -- > 0){
					synchronizeTimeZone();
					if(localTimeZoneSynchronized)
						break;
					else
						try{
							TimeUnit.MILLISECONDS.sleep(1000);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}}).start();
		
		new Thread(new Runnable(){
			public void run(){
				int times = 20;
				while(times-- > 0)
					if(localTimeZoneSynchronized && utcTimeSynchronized){
						Date date = new Date();
						long currentTimezone = date.getTimezoneOffset()*60;
						
						//TODO: weird!!! 
						timeOffset = ((long) utc + currentTimezone +  (long) timeZone * 60 * 60) - (date.getTime() / 1000);
						timeSynchronized  = true;
						break;
					}
					else
						try {
							TimeUnit.MILLISECONDS.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			}}).start();
		
		return 0;
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
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	};

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void synchronizeTime(){
		try{
	        DatagramSocket socket = new DatagramSocket();
	        SettingProvider sp = SettingProvider.getInstance();
	        String ntpServerName = sp.getSetting(SettingProvider.CHOSEN_SREVER_ADDRESS);
	        Log.d("GeekClock", "NTP server: " + ntpServerName);
	        InetAddress address = InetAddress.getByName(ntpServerName);
	        byte[] buf = new NtpMessage().toByteArray();
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
	        //TODO: move this hard-coded timeout limit to database
	        socket.setSoTimeout(4*1000);
	        socket.send(packet);
	        Log.d("GeekClock", "NTP request sent.");
	        
	        // Get response
	        packet = new DatagramPacket(buf, buf.length);
	        socket.receive(packet);
			
	        Log.d("GeekClock", "NTP answer received.");
			NtpMessage msg = new NtpMessage(packet.getData());
	        utc = msg.transmitTimestamp - 2208988800.0;
			utcTimeSynchronized = true;
        }
        catch(Exception e){
        	timeOffset = 0;
        }        
	}
	@SuppressWarnings("deprecation")
	public void synchronizeTimeZone() {
		try {
			SettingProvider sp = SettingProvider.getInstance();
			
			WebService.setUserName(sp.getSetting(SettingProvider.GEONAMES_USER_NAME));
			//TODO: move this limit to database;
			WebService.setConnectTimeOut(5*1000);
			//TODO: change timezone to java.util.TimeZone

	        Log.d("GeekClock", "GeoNames timezone request sent");
			Timezone tmz = WebService.timezone(latitude, longitude);
			placeName = WebService.findNearbyPlaceName(latitude, longitude).iterator().next().getName();
	        Log.d("GeekClock", "GeoNames timezone answer received");
			timeZone =  tmz.getGmtOffset();
	        Log.d("GeekClock", "Timezone set to " + timeZone);
	        
			localTimeZoneSynchronized = true;
		}
		catch (Exception e) {
			Log.d("GeekClock", "Error: unexpected exception");
			e.printStackTrace();
			timeZone =  0;
		}
	}

	public String getPlaceName() {
		return placeName;
	}

	public long getTimeSeconds() {
		Date date = new Date();
		return timeSynchronized ? (date.getTime() / 1000 + timeOffset ):( date.getTime() / 1000);
	}
	
	public boolean timeSynchronized(){
		return this.timeSynchronized;
	}

	public int getTimeZone() {
		return (int)timeZone;
	}
}
