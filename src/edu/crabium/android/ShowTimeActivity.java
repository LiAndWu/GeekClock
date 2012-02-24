package edu.crabium.android;

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

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.showclock);
        
      //�����ʼ����Ϣ
        SAXBuilder saxBuilder = new SAXBuilder();
        try 
        {
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
			Document document = saxBuilder.build(fileInputStream);
		    fileInputStream.close();
			Element root = document.getRootElement();
			Element NTPServer = root.getChild("NTPServer");
			
			//���ѡ�з���������Ϣ
			Element server = NTPServer.getChild("Chosen");
			TimeProvider.SetServerName(server.getChild("Name").getValue());
		    TimeProvider.SetServerAddress(server.getChild("Address").getValue());
		    
		    //���ˢ��ʱ��
		    Element GPSConfig = root.getChild("GPSConfig");
		    Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
		    MoreActivity.ReadFrequencyHour = Integer.valueOf(RefreshFrequency.getChild("Hour").getValue());
		    MoreActivity.ReadFrequencyMinute = Integer.valueOf(RefreshFrequency.getChild("Minute").getValue());	
		    
		    //���Geonames�û���
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
        
        //��ʾʱ��
        imageview = (ImageView) findViewById(R.id.imageView);
        
        //��ʾʱ��
        m_ShowTime = (TextView) findViewById(R.id.showTime);   
        //����һ���߳�
        new TimeThread().start();	
        //��ʾ����
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
     * 1��handler ����ʹ�� Message���������ݣ�
     * 2��Message �Ѳ���ѹ�뵽��Ϣ�����У�Ȼ��handler�ᴦ����Ϣ��������Ϣ
     * 3������Ҫ��Handler�Ķ���handler���������⣩�����Message��Ķ���Message msg = handler.obtainMessage();
     * 4��Message������������arg1��arg2,������int��������򵥵�ʹ��,�Բ�����ֵ Msg.arg1=100;
     * 5��Ȼ����handler.sendMessage(msg),����Ϣ�����͵���Ϣ����
     * 6��ʹ��handler��handleMessage(Message msg)��������Ϣ�������ڶ���Handler����ʱͬʱ��������ķ�ʽ��д�÷���
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
			final SimpleDateFormat m_DateFormat = new SimpleDateFormat("yyyy��M��d��");
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
