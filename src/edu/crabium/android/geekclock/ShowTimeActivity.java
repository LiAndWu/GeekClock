package edu.crabium.android.geekclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import edu.crabium.android.geekclock.R;
import edu.crabium.android.geekclock.TimeService.TimeServiceBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    }
    
    @Override
    protected void onStart(){
    	super.onStart();
    	Intent intent = new Intent(this, TimeService.class);
    	this.getApplicationContext().bindService(intent, timeServiceConnection, Context.BIND_AUTO_CREATE);
    	System.out.println("trying bind");
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    	if(timeServiceBound){
    		this.getApplicationContext().unbindService(timeServiceConnection);
    		timeServiceBound = false;
    	}
    }
    
    private ServiceConnection timeServiceConnection = new ServiceConnection(){
    	@Override
    	public void onServiceConnected(ComponentName className, IBinder service){
    		System.out.println("connected");
    		TimeServiceBinder binder = (TimeServiceBinder) service;
    		timeService = binder.getService();
    		timeServiceBound = true;
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName arg0){
    		timeServiceBound = false;
    	}
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
    
    private static boolean synchronizeInfoNotified = false;
    private Handler TimeHandler = new Handler() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
		String sysTime, sysDate;
		@Override
		public void handleMessage(Message msg) {
    		super.handleMessage(msg); 
    		
    		switch(msg.what) {
    		case TimeMessageNum1:
    			if(timeServiceBound && timeService.utcTimeSynchronized()){
    				long timeSeconds = timeService.getTimeSeconds();
    				draw(timeSeconds);
    				if(!synchronizeInfoNotified){
    					DisplayToast("时间已同步");
    					synchronizeInfoNotified = true;
    				}
    			}
    			else{
    				Date date = new Date();
    				long timeSeconds = date.getTime()/1000;
    				draw(timeSeconds);
    			}
    			break;
    		default:
    			break;
    		}
    	}
		
		private void draw(long timeSeconds){
			sysTime = timeFormat.format(new Date(timeSeconds * 1000));
			sysDate = dateFormat.format(new Date(timeSeconds * 1000));
		    showTime.setText(sysTime);  
		    showDate.setText(sysDate);
		    	    
		    ClockDrawer clockdrawer = new ClockDrawer(ShowTimeActivity.this);
		    clockdrawer.Draw(imageView, timeSeconds);
		}
    };

	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
    
}
