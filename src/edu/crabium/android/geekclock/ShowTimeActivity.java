package edu.crabium.android.geekclock;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import org.geonames.WebService;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.crabium.android.geekclock.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowTimeActivity extends Activity { 
    TextView m_ShowTime, m_ShowDate;
    Calendar c; 
	private static final int TimeMessageNum1 = 1; 

	ImageView imageview = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.showclock);
        
      //载入初始化信息
        SAXBuilder saxBuilder = new SAXBuilder();
        try 
        {
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
			Document document = saxBuilder.build(fileInputStream);
		    fileInputStream.close();
			Element root = document.getRootElement();
			Element NTPServer = root.getChild("NTPServer");
			
			//获得选中服务器的信息
			Element server = NTPServer.getChild("Chosen");
			TimeProvider.SetServerName(server.getChild("Name").getValue());
		    TimeProvider.SetServerAddress(server.getChild("Address").getValue());
		    
		    //获得刷新时间
		    Element GPSConfig = root.getChild("GPSConfig");
		    Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
		    MoreActivity.ReadFrequencyHour = Integer.valueOf(RefreshFrequency.getChild("Hour").getValue());
		    MoreActivity.ReadFrequencyMinute = Integer.valueOf(RefreshFrequency.getChild("Minute").getValue());	
		    
		    //获得Geonames用户名
		    Element ele_GeoNamesConfig = root.getChild("GeoNames");
		    Element ele_GeoNamesUserName = ele_GeoNamesConfig.getChild("UserName");
		    WebService.setUserName(ele_GeoNamesUserName.getValue());
		    TimeProvider.GeoNamesUserName = ele_GeoNamesUserName.getValue();

		} 
        
        catch (IOException e1) 
		{
			e1.printStackTrace();
		} 
        catch (JDOMException e) 
		{
			e.printStackTrace();
		}
        
        //显示时钟
        imageview = (ImageView) findViewById(R.id.imageView);
        
        //显示时间
        m_ShowTime = (TextView) findViewById(R.id.showTime);   
        //启动一号线程
        new TimeThread().start();	
        //显示日期
        m_ShowDate = (TextView) findViewById(R.id.showDate);    

        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceName);
        String provider = LocationManager.GPS_PROVIDER;
        
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null)
        	TimeProvider.SetLocation(location.getLatitude(), location.getLongitude());
        else
        	TimeProvider.ResetLocation();
        
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);

    }
    
    private final LocationListener locationListener = new LocationListener() {
        @Override
		public void onLocationChanged(Location location) {
        	if(location != null){
            	TimeProvider.SetLocation(location.getLatitude(), location.getLongitude());
            	//------------------------------------------------

                MoreActivity.m_ShowLongitude.setText(Double.toString(TimeProvider.GetLongitude()));
                MoreActivity.m_ShowLatitude.setText(Double.toString(TimeProvider.GetLatitude()));
                MoreActivity.m_ShowTimeZone.setText("UTC"+ (TimeProvider.GetTimezone() > 0?
        		"+" + Integer.toString(TimeProvider.GetTimezone())
        		:Integer.toString(TimeProvider.GetTimezone())));
        	}
        }
        @Override
		public void onProviderDisabled(String provider){
        }
        @Override
		public void onProviderEnabled(String provider){ }
        @Override
		public void onStatusChanged(String provider, int status,
        Bundle extras){ }
};
    
    /*    
     * http://hi.baidu.com/foreverpains/blog/item/a84f681c4b477570f624e40b.html
     * 1、handler 可以使用 Message来传输数据，
     * 2、Message 把参数压入到消息队列中，然后handler会处理消息队列中消息
     * 3、首先要用Handler的对象handler（名字随意）来获得Message类的对象，Message msg = handler.obtainMessage();
     * 4、Message有两个参数，arg1，arg2,他们是int，这是最简单的使用,对参数赋值 Msg.arg1=100;
     * 5、然后用handler.sendMessage(msg),把消息对象发送到消息队列
     * 6、使用handler的handleMessage(Message msg)来处理消息，可以在定义Handler对象时同时用匿名类的方式重写该方法
     * 
    */
    private class TimeThread extends Thread {
    	@Override
		public void run() {
    		while (true) {
	    		try {
					Message m_TimeMessage = new Message();
					m_TimeMessage.what = TimeMessageNum1;
					TimeHandler.sendMessage(m_TimeMessage);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}//while
    		
    	}//run
    }//Thread
    
    private Handler TimeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) { 
			final TimeProvider timeprovider = new TimeProvider(); 
			final SimpleDateFormat m_TimeFormat = new SimpleDateFormat("HH:mm:ss");
			final SimpleDateFormat m_DateFormat = new SimpleDateFormat("yyyy年M月d日");
			final String sysTime, sysDate;
    		super.handleMessage(msg); 
    		switch(msg.what) {
    		case TimeMessageNum1:
    			sysTime = m_TimeFormat.format(new Date(timeprovider.GetTimeSeconds() * 1000));
    			sysDate = m_DateFormat.format(new Date(timeprovider.GetTimeSeconds() * 1000));
    		    m_ShowTime.setText(sysTime);  
    		    m_ShowDate.setText(sysDate);
    		    	    
    		    ClockDrawer clockdrawer = new ClockDrawer(ShowTimeActivity.this);
    		    clockdrawer.Draw(imageview, timeprovider.GetTimeSeconds());
    		    
    			break;
    		default:
    			break;
    		}
    	}
    };
    
    
}
