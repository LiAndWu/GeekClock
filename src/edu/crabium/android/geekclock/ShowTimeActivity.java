package edu.crabium.android.geekclock;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import org.geonames.WebService;

import edu.crabium.android.geekclock.R;
import edu.crabium.android.geekclock.TimeService.TimeServiceBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowTimeActivity extends Activity { 
    private TextView showTime;
    private TextView showDate;
	private static final int TimeMessageNum1 = 1; 

	ImageView imageView = null;
	private TimeService timeService;
	private boolean timeServiceBound = false;
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.showclock);
	    
        //显示时钟
        imageView = (ImageView) findViewById(R.id.imageView);
        
        //显示时间
        showTime = (TextView) findViewById(R.id.showTime);   
        //启动一号线程
        new TimeThread().start();	
        //显示日期
        showDate = (TextView) findViewById(R.id.showDate);    

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
    
    @Override
    protected void onStart(){
    	super.onStart();
    	Intent intent = new Intent(this, TimeService.class);
    	bindService(intent, timeServiceConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    	if(timeServiceBound){
    		unbindService(timeServiceConnection);
    		timeServiceBound = false;
    	}
    }
    
    private ServiceConnection timeServiceConnection = new ServiceConnection(){
    	@Override
    	public void onServiceConnected(ComponentName className, IBinder service){
    		TimeServiceBinder binder = (TimeServiceBinder) service;
    		timeService = binder.getService();
    		timeServiceBound = true;
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName arg0){
    		timeServiceBound = false;
    	}
    };
    
    private final LocationListener locationListener = new LocationListener() {
        @Override
		public void onLocationChanged(Location location) {
        	if(location != null){
            	TimeProvider.SetLocation(location.getLatitude(), location.getLongitude());
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
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
			String sysTime, sysDate;
    		super.handleMessage(msg); 
    		
    		switch(msg.what) {
    		case TimeMessageNum1:
    			if(timeServiceBound && timeService.isSynchronized()){
    				long timeSeconds = timeService.getTimeSeconds();
    				sysTime = timeFormat.format(new Date(timeSeconds * 1000));
        			sysDate = dateFormat.format(new Date(timeSeconds * 1000));
        		    showTime.setText(sysTime);  
        		    showDate.setText(sysDate);
        		    	    
        		    ClockDrawer clockdrawer = new ClockDrawer(ShowTimeActivity.this);
        		    clockdrawer.Draw(imageView, timeSeconds);
    			}
    			break;
    		default:
    			break;
    		}
    	}
    };
    
    
}
