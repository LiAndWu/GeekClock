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
	static boolean Synchronized = false;
	static private long TimeOffset = 0;
	static public String GeoNamesUserName  = "tikiet";
	static private boolean TimezoneError = true;
	static private boolean UTCError = true;
	Date date = new Date();
	
	public static String placeName = "China";
	private static String ServerAddress;
	private static String ServerName;
	static public void Reset(){
		Synchronized = false;
		CurrentTimezone=8.0;
		TimezoneError = true;
		UTCError = true;
	}
	
	static public int RefreshData(){
		return GetTimezone(latitude,longitude);
	}
	
	static public double GetLatitude(){
		return latitude;
	}
	
	static public double GetLongitude(){
		return longitude;
	}
	
	public boolean isTimezoneError(){
		return TimezoneError;
	} 
	
	static public void SetTimezone(int timezone){
		CurrentTimezone = timezone;
	}
	
	public boolean isUTCError(){
		return UTCError;
	}
	
	static public void ResetLocation(){
		longitude = 120.33820867538452;
		latitude = 30.31900699227783;
	}
	
	static public void SetLocation(double latitude, double longitude){
		TimeProvider.latitude = latitude;
		TimeProvider.longitude = longitude;
	}
	
	public long GetTimeSeconds(){
		long LocalTimezone;
		LocalTimezone = date.getTimezoneOffset()*60;
			
		if(Synchronized == true){
			return date.getTime()/1000 + TimeOffset;
		}
		else{
			if(GetTimezone(latitude,longitude) == -1) TimezoneError = true; else TimezoneError = false;	
			if(GetUTC() == -1) UTCError = true; else UTCError = false;
			
			Synchronized = true;
			TimeOffset = ((long)UTC + LocalTimezone + (long)CurrentTimezone*60*60) - date.getTime()/1000;
			
			return date.getTime()/1000 + TimeOffset;
		}		
	}
	
	@SuppressWarnings("deprecation")
	public static int GetTimezone(double latitude, double longitude){
		try {
    		WebService.setUserName(GeoNamesUserName);
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
	
	public static int GetTimezone(){
		return (int) CurrentTimezone;
	}
	
	public static void SetServerAddress(String s)
	{
		ServerAddress = s;
	}
	
	public static String GetServerAddres()
	{
		return ServerAddress;
	}
	
	public static String GetServerName()
	{
		return ServerName;
	}
	
	public static void SetServerName(String s)
	{
		ServerName = s;
	}
	public int GetUTC(){
		try{
	        DatagramSocket socket = new DatagramSocket();
	        //
	        InetAddress address = InetAddress.getByName(ServerAddress);
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
