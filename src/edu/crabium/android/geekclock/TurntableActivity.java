package edu.crabium.android.geekclock;

import edu.crabium.android.geekclock.wheel.NumericWheelAdapter;
import edu.crabium.android.geekclock.wheel.OnWheelChangedListener;
import edu.crabium.android.geekclock.wheel.OnWheelScrollListener;
import edu.crabium.android.geekclock.wheel.WheelView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class TurntableActivity extends Activity {
	private TextView showFrequencyTextView;
	private boolean timeChanged = false;
	private boolean timeScrolled = false;
	private int frequencyHour;
	private int frequencyMinute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.turntable);
		
		showFrequencyTextView = (TextView)findViewById(R.id.showFrequencyTextView);
		showFrequencyTextView.setTextColor(Color.BLACK);
		
		SettingProvider sp = SettingProvider.getInstance();
		int frequency = Integer.valueOf(sp.getSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS));
		showFrequencyTextView.setText("现在的刷新频率是：每" + frequency/3600 + "小时" + frequency%3600/60 + "分钟一次" );
		
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				TurntableActivity.this.finish();
			}
		});
		
		Button confirmButton = (Button)findViewById(R.id.confirmButton);
		confirmButton.setTextColor(Color.WHITE);
		confirmButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingProvider sp = SettingProvider.getInstance();
				int seconds = frequencyHour*3600 + frequencyMinute * 60;
				sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(seconds));
				TurntableActivity.this.finish();
			}
		});
		
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setAdapter(new NumericWheelAdapter(0, 9));
		hours.setLabel("小时");
	
		final WheelView minutes = (WheelView) findViewById(R.id.mins);
		minutes.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		minutes.setLabel("分钟");
		minutes.setCyclic(true);
	
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					//timeChanged = true;
					frequencyHour = hours.getCurrentItem();
					frequencyMinute = minutes.getCurrentItem();
					showFrequencyTextView.setText("现在的刷新频率是：每" + frequencyHour+ "小时" + frequencyMinute + "分钟一次" );
					//timeChanged = false;
				}
			}
		};

		hours.addChangingListener(wheelListener);
		minutes.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				//timeChanged = true;
				frequencyHour = hours.getCurrentItem();
				frequencyMinute = minutes.getCurrentItem();
				showFrequencyTextView.setText("现在的刷新频率是：每" + frequencyHour+ "小时" + frequencyMinute + "分钟一次" );
				//timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		minutes.addScrollingListener(scrollListener);	
	}
	
}
