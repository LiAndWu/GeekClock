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
	private Button m_BackButton, m_ConfirmButton;
	
	private TextView m_ShowFrequencyTextView;
	// Time changed flag
	private boolean timeChanged = false;
	
	// Time scroll flag
	private boolean timeScrolled = false;
	
	private int frequencyHour;
	private int frequencyMinute;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.turntable);
		
		m_ShowFrequencyTextView = (TextView)findViewById(R.id.showFrequencyTextView);
		m_ShowFrequencyTextView.setTextColor(Color.BLACK);
		
		SettingProvider sp = SettingProvider.getInstance();
		int frequency = Integer.valueOf(sp.getSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS));
		m_ShowFrequencyTextView.setText("现在的刷新频率是：每" + frequency/3600 + "小时" +
				frequency%3600/60 + "分钟一次" );
		
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TurntableActivity.this, ReadFrequencyActivity.class);
				startActivity(intent);
				TurntableActivity.this.finish();
			}
		});
		
		m_ConfirmButton = (Button)findViewById(R.id.confirmButton);
		m_ConfirmButton.setTextColor(Color.WHITE);
		m_ConfirmButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingProvider sp = SettingProvider.getInstance();
				int seconds = frequencyHour*3600 + frequencyMinute * 60;
				sp.addSetting(SettingProvider.REFRESH_FREQUENCY_SECONDS, String.valueOf(seconds));

				Intent intent = new Intent(TurntableActivity.this, ReadFrequencyActivity.class);
				startActivity(intent);
				TurntableActivity.this.finish();
			}
		});
		
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setAdapter(new NumericWheelAdapter(0, 9));
		hours.setLabel("小时");
	
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		mins.setLabel("分钟");
		mins.setCyclic(true);
	
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					frequencyHour = hours.getCurrentItem();
					frequencyMinute = mins.getCurrentItem();
					m_ShowFrequencyTextView.setText("现在的刷新频率是：每" + frequencyHour+ "小时" +
							frequencyMinute + "分钟一次" );
					timeChanged = false;
				}
			}
		};

		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				frequencyHour = hours.getCurrentItem();
				frequencyMinute = mins.getCurrentItem();
				m_ShowFrequencyTextView.setText("现在的刷新频率是：每" + frequencyHour+ "小时" +
						frequencyMinute + "分钟一次" );
				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);	
	}
	
}
