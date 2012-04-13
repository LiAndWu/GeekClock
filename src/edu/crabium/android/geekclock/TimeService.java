package edu.crabium.android.geekclock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.geonames.WebService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class TimeService extends Service {
	private boolean utcTimeSynchronized = false;
	private boolean localTimeZoneSynchronized = false;
	private boolean timeSynchronized = false;
	private boolean locationDetected = false;
	private boolean locationIsSynchronized = false;

	private int installLocationListener  = 1;
	private int removeLocationListener = 2;
	
	private String placeName;
	private double latitude;
	private double longitude;
	private long timeOffset;
	private double timeZone;
	private double utc;
	private final IBinder timeServiceBinder = new TimeServiceBinder();

	LocationManager locationManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		return timeServiceBinder;
	}

	public class TimeServiceBinder extends Binder {
		TimeService getService() {
			return TimeService.this;
		}
	}
	
	private class UTCTimeSynchronizationStatusListener implements Runnable{
		public void run(){
			while(true){
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
		}
	}
	
	private class LocalTimezoneSynchronizationStatusListener implements Runnable{
		public void run(){
			while(true){
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
		}
	}
	
	private class TimeSynchronizationStatusListener implements Runnable{
		public void run(){
			while(true)
			{
				if(locationDetected && localTimeZoneSynchronized && utcTimeSynchronized){
					calculateTimeOffset();
					timeSynchronized  = true;
					break;
				}
				else
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}

		private void calculateTimeOffset() {
			Date date = new Date();
			
			//TODO: weird!!! 
			timeOffset = ((long) utc + getCurrentTimezone() +  (long) timeZone * 60 * 60) - (date.getTime() / 1000);
		}
		
		private long getCurrentTimezone(){
			Date date = new Date();
			return date.getTimezoneOffset()*60;
		}
	}
	
	Handler locationStatusHandler = new Handler(){
		@Override
		public void handleMessage(Message message){
			super.handleMessage(message);
			toggleUpdateStatus(message);
		}

		private void toggleUpdateStatus(Message message) {
			LocationManager locationManager = (LocationManager)message.obj;
			if(message.what == installLocationListener){
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
				startSynchronizing();
			}
			else{
				locationManager.removeUpdates(locationListener);
			}
		}
	};
	
	private void startSynchronizing(){
		new Thread(new UTCTimeSynchronizationStatusListener()).start();
		new Thread(new LocalTimezoneSynchronizationStatusListener()).start();
		new Thread(new TimeSynchronizationStatusListener()).start();
	}
	
	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				locationDetected = true;
				locationIsSynchronized = true;
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
	
	private class LocationDetectionStatusListener implements Runnable{
		boolean firstRun = true;
		public void run(){
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			while(true){
				System.out.println("Start locating");
				if(firstRun){
					Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					
					Location location = null;
					if(gpsLocation != null)
						location = gpsLocation;
					else if(networkLocation != null)
						location = networkLocation;

					if (location != null){
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						locationDetected = true;
					}
					firstRun = false;
				}
				else{
					locationIsSynchronized = false;
					Message message = new Message();
					message.what = installLocationListener;
					message.obj = locationManager;
					locationStatusHandler.sendMessage(message);
					new Thread(new LocationUpdatesStatusListener()).start();
				};
				
				try {
					SettingProvider sp = SettingProvider.getInstance();
					TimeUnit.SECONDS.sleep(Integer.valueOf(sp.getSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		private class LocationUpdatesStatusListener implements Runnable{
			@Override
			public void run(){
				while(true){
					if(locationIsSynchronized){
						Message message = new Message();
						message.what = removeLocationListener;
						message.obj = locationManager;
						locationStatusHandler.sendMessage(message);
						break;
					}
					else{
						try {
							TimeUnit.MILLISECONDS.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("GeekClock", "Service stared");
		startSynchronizing();
		new Thread(new LocationDetectionStatusListener()).start();
		return 0;
	}
	
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
	        byte[] buf = new NtpMessage().toByteArray();
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ntpServerName), 123);
	        //TODO: move this hard-coded timeout limit to database
	        socket.setSoTimeout(4*1000);
	        socket.send(packet);
	        Log.d("GeekClock", "NTP request sent.");
	        
	        // Get response
	        packet = new DatagramPacket(buf, buf.length);
	        socket.receive(packet);
			
	        Log.d("GeekClock", "NTP answer received.");
	        utc = new NtpMessage(packet.getData()).transmitTimestamp - 2208988800.0;
			utcTimeSynchronized = true;
        }
        catch(Exception e){
        }        
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * private, it will suspend if location isn't detected
	 */
	private void synchronizeTimeZone() {
		try {
			SettingProvider sp = SettingProvider.getInstance();
			
			WebService.setUserName(sp.getSetting(SettingProvider.GEONAMES_USER_NAME));
			//TODO: move this limit to database;
			WebService.setConnectTimeOut(10*1000);
			//TODO: change timezone to java.util.TimeZone

			while(!locationDetected){
				TimeUnit.MILLISECONDS.sleep(1000);
			}
			
	        Log.d("GeekClock", "GeoNames timezone request sent");
			placeName = WebService.findNearbyPlaceName(latitude, longitude).iterator().next().getName();
	        Log.d("GeekClock", "GeoNames timezone answer received");
			timeZone =  WebService.timezone(latitude, longitude).getGmtOffset();
	        Log.d("GeekClock", "Timezone set to " + timeZone);
	        
			localTimeZoneSynchronized = true;
		}
		catch (Exception e) {
			Log.d("GeekClock", "Error: unexpected exception");
			e.printStackTrace();
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
