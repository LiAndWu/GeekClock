package edu.crabium.android.geekclock;


import edu.crabium.android.geekclock.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class RefreshFrequencyActivity extends Activity{
	private Button backButton;
	private Button fiveMinutesButton;
	private Button tenMinutesButton;
	private Button halfHourButton;
	private Button oneHourButton;
	private Button customButton;
	private static TextView readFrequencyTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.selection_frequency);
		
		backButton = (Button)findViewById(R.id.backButton);
		fiveMinutesButton = (Button)findViewById(R.id.fiveMinutesButton);
		tenMinutesButton = (Button)findViewById(R.id.tenMinutesButton);
		halfHourButton = (Button)findViewById(R.id.halfHourButton);
		oneHourButton = (Button)findViewById(R.id.oneHourButton);
		customButton = (Button)findViewById(R.id.customButton);
		readFrequencyTextView = (TextView)findViewById(R.id.readFrequencytextView);
		
		backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				RefreshFrequencyActivity.this.finish();
			}
		});
		
		fiveMinutesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeFrequencyAndSetText(5);
			}
		});
		
		tenMinutesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeFrequencyAndSetText(10);
			}
		});
		
		halfHourButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeFrequencyAndSetText(30);
			}
		}); 
		
		oneHourButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				writeFrequencyAndSetText(60);
			}
		});
		
		customButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RefreshFrequencyActivity.this, TurntableActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		readFrequencyTextView.setTextSize(17);
		SettingProvider sp = SettingProvider.getInstance();
		int frequency = Integer.valueOf(sp.getSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS));
		
		readFrequencyTextView.setText("现在的刷新频率是：\n每" + frequency/3600 + "小时"  + frequency%3600/60 + "分钟一次");
		
	}
	
	private void writeFrequencyAndSetText(int minute){
		readFrequencyTextView.setText("现在的刷新频率是：\n每" + minute + "分钟一次");	
		SettingProvider sp = SettingProvider.getInstance();
		sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(minute*60));
	}
}
		