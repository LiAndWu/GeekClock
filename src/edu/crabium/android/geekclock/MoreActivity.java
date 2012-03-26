package edu.crabium.android.geekclock;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.crabium.android.geekclock.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MoreActivity extends Activity {
	
	static TextView m_ShowTimeZone,m_ShowLongitude, m_ShowLatitude;
	TextView  m_ShowCity, m_ShowWeek, m_ShowYearDay;
	@SuppressWarnings("unused")
	private static final int TimeMessageNum2 = 2;
	public static int tm;
	public static int ReadFrequencyHour = 0;
	public static int ReadFrequencyMinute = 10;
	//默认十分钟读取一次
	public int ReadFrequency ;
	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.more);
   
        ReadFrequency = 1000 * (60 * ReadFrequencyMinute +  3600 * ReadFrequencyHour);
        
        final  String TimeZone, Week, YearDay; 
        
        TimeProvider timeprovider = new TimeProvider();
        
        final SimpleDateFormat m_WeekFormat = new SimpleDateFormat("E");
        final SimpleDateFormat m_YearDayFormat = new SimpleDateFormat("今年第D天");
        
        TimeZone = "UTC"+ (TimeProvider.GetTimezone() > 0?
        		"+" + Integer.toString(TimeProvider.GetTimezone())
        		:Integer.toString(TimeProvider.GetTimezone()));

        Week = m_WeekFormat.format(new Date(timeprovider.GetTimeSeconds() * 1000));
        YearDay = m_YearDayFormat.format(new Date(timeprovider.GetTimeSeconds() * 1000));
        
        //时区
        m_ShowTimeZone = (TextView) findViewById(R.id.showTimeZone);
        m_ShowTimeZone.setText(TimeZone);
        
        
        //城市
        m_ShowCity = (TextView) findViewById(R.id.showCity);
        m_ShowCity.setText(TimeProvider.placeName);
        
        //星期
        m_ShowWeek = (TextView) findViewById(R.id.showWeek);
        m_ShowWeek.setText(Week);
        
        //时光机
        m_ShowYearDay = (TextView) findViewById(R.id.ShowYearDay);
        m_ShowYearDay.setText(YearDay);
        
        //显示经度
        m_ShowLongitude = (TextView) findViewById(R.id.showLongitude);   
        m_ShowLongitude.setText(Double.toString(TimeProvider.GetLongitude()));
        
        //显示纬度
        m_ShowLatitude = (TextView) findViewById(R.id.showLatitude);
        m_ShowLatitude.setText(Double.toString(TimeProvider.GetLatitude()));
         
    }
    
}

