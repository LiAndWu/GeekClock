package edu.crabium.android.geekclock;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import org.geonames.Timezone;
import org.geonames.WebService;


public class TimeProvider{
	static private double CurrentTimezone=8.0;
	static private double latitude =   30.3190069922;
	static private double longitude = 120.3382086753;
	
	private double UTC;
	private static boolean Synchronized = false;
	static private long TimeOffset = 0;
	static private boolean TimezoneError = true;
	static private boolean UTCError = true;
	Date date = new Date();
	
	public static String placeName = "China";
	static public void Reset(){
		Synchronized = false;
		CurrentTimezone=8.0;
		TimezoneError = true;
		UTCError = true;
	}
	
	static public int RefreshData(){
		return GetTimezone(latitude,longitude);
	}
	
	public boolean isTimezoneError(){
		return TimezoneError;
	} 

	//TODO: delete
	static public void SetTimezone(int timezone){
		CurrentTimezone = timezone;
	}
	
	public boolean isUTCError(){
		return UTCError;
	}

	//TODO: delete
	static public void ResetLocation(){
		longitude = 120.33820867538452;
		latitude = 30.31900699227783;
	}
	
	static public void SetLocation(double latitude, double longitude){
		TimeProvider.latitude = latitude;
		TimeProvider.longitude = longitude;
	}
	
	@SuppressWarnings("deprecation")
	public static int GetTimezone(double latitude, double longitude){
		try {
			SettingProvider sp = SettingProvider.getInstance();
			
    		WebService.setUserName(sp.getSetting(SettingProvider.GEONAMES_USER_NAME));
			Timezone tmz = WebService.timezone(latitude, longitude);
			placeName = WebService.findNearbyPlaceName(latitude, longitude).iterator().next().getName();
			CurrentTimezone =  tmz.getGmtOffset();
			return 0;
		}
    	catch (Exception e) {
    		CurrentTimezone = 8.0;
    		return -1;
    	}
	}
	
	public int GetUTC(){
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
	        
			UTC = msg.toUTC();
			return 0;
        }
        catch(Exception e){
        	Date date = new Date();
        	UTC = date.getTime()/1000;
        	return -1;
        }        
	}
}
