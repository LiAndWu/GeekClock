package edu.crabium.android;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	//Ĭ��ʮ���Ӷ�ȡһ��
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
        final SimpleDateFormat m_YearDayFormat = new SimpleDateFormat("�����D��");
        
        TimeZone = "UTC"+ (TimeProvider.GetTimezone() > 0?
        		"+" + Integer.toString(TimeProvider.GetTimezone())
        		:Integer.toString(TimeProvider.GetTimezone()));

        Week = m_WeekFormat.format(new Date(timeprovider.GetTimeSeconds() * 1000));
        YearDay = m_YearDayFormat.format(new Date(timeprovider.GetTimeSeconds() * 1000));
        
        //ʱ��
        m_ShowTimeZone = (TextView) findViewById(R.id.showTimeZone);
        m_ShowTimeZone.setText(TimeZone);
        
        
        //����
        m_ShowCity = (TextView) findViewById(R.id.showCity);
        m_ShowCity.setText(TimeProvider.placeName);
        
        //����
        m_ShowWeek = (TextView) findViewById(R.id.showWeek);
        m_ShowWeek.setText(Week);
        
        //ʱ���
        m_ShowYearDay = (TextView) findViewById(R.id.ShowYearDay);
        m_ShowYearDay.setText(YearDay);
        
        //��ʾ����
        m_ShowLongitude = (TextView) findViewById(R.id.showLongitude);   
        m_ShowLongitude.setText(Double.toString(TimeProvider.GetLongitude()));
        
        //��ʾγ��
        m_ShowLatitude = (TextView) findViewById(R.id.showLatitude);
        m_ShowLatitude.setText(Double.toString(TimeProvider.GetLatitude()));
         
    }
    
}

