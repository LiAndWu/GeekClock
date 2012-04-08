package edu.crabium.android.geekclock;


import edu.crabium.android.geekclock.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class ReadFrequencyActivity extends Activity{
	private Button m_BackButton;
	private Button m_FiveMinutesButton, m_TenMinutesButton,
	m_HalfHourButton, m_OneHourButton, m_CustomButton;
	private static TextView m_ReadFrequencyTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection_frequency);
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_FiveMinutesButton = (Button)findViewById(R.id.fiveMinutesButton);
		m_TenMinutesButton = (Button)findViewById(R.id.tenMinutesButton);
		m_HalfHourButton = (Button)findViewById(R.id.halfHourButton);
		m_OneHourButton = (Button)findViewById(R.id.oneHourButton);
		m_CustomButton = (Button)findViewById(R.id.customButton);
		m_ReadFrequencyTextView = (TextView)findViewById(R.id.readFrequencytextView);
		
		m_ReadFrequencyTextView.setTextSize(18);
		if (MoreActivity.ReadFrequencyHour == 0) {
			m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每" + MoreActivity.ReadFrequencyMinute + "分钟一次");
		} else if (MoreActivity.ReadFrequencyMinute == 0) {
			m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每" + MoreActivity.ReadFrequencyHour + "小时一次");
		} else {
			m_ReadFrequencyTextView.setTextSize(17);
			m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每" + MoreActivity.ReadFrequencyHour + 
					"小时" + MoreActivity.ReadFrequencyMinute + "分钟一次");
		}
		
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ReadFrequencyActivity.this, SetActivity.class);
				startActivity(intent);
				ReadFrequencyActivity.this.finish();
			}
		});
		
		m_FiveMinutesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 0;
				MoreActivity.ReadFrequencyMinute = 5;
				m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每5分钟一次");	

				SettingProvider sp = SettingProvider.getInstance();
				int hour = Integer.valueOf(MoreActivity.ReadFrequencyHour);
				int minute = Integer.valueOf(MoreActivity.ReadFrequencyMinute);
				int seconds = hour*3600 + minute * 60;
				sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(seconds));
			}
		});
		
		m_TenMinutesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 0;
				MoreActivity.ReadFrequencyMinute = 10;
				m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每10分钟一次");	
				
				SettingProvider sp = SettingProvider.getInstance();
				int hour = Integer.valueOf(MoreActivity.ReadFrequencyHour);
				int minute = Integer.valueOf(MoreActivity.ReadFrequencyMinute);
				int seconds = hour*3600 + minute * 60;
				sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(seconds));
			}
		});
		
		m_HalfHourButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 0;
				MoreActivity.ReadFrequencyMinute = 30;
				m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每30分钟一次");	

				SettingProvider sp = SettingProvider.getInstance();
				int hour = Integer.valueOf(MoreActivity.ReadFrequencyHour);
				int minute = Integer.valueOf(MoreActivity.ReadFrequencyMinute);
				int seconds = hour*3600 + minute * 60;
				sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(seconds));
			}
		}); 
		
		m_OneHourButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 1;
				MoreActivity.ReadFrequencyMinute = 0;
				m_ReadFrequencyTextView.setText("现在的刷新频率是：\n每1小时一次");	

				SettingProvider sp = SettingProvider.getInstance();
				int hour = Integer.valueOf(MoreActivity.ReadFrequencyHour);
				int minute = Integer.valueOf(MoreActivity.ReadFrequencyMinute);
				int seconds = hour*3600 + minute * 60;
				sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(seconds));
			}
		});
		
		m_CustomButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ReadFrequencyActivity.this, TurntableActivity.class);
				startActivity(intent);
				ReadFrequencyActivity.this.finish();
			}
		});
	}
}
		