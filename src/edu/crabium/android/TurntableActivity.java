package edu.crabium.android;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import edu.crabium.android.wheel.NumericWheelAdapter;
import edu.crabium.android.wheel.OnWheelChangedListener;
import edu.crabium.android.wheel.OnWheelScrollListener;
import edu.crabium.android.wheel.WheelView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TurntableActivity extends Activity {
	private Button m_BackButton, m_ConfirmButton;
	
	private TextView m_ShowFrequencyTextView;
	// Time changed flag
	@SuppressWarnings("unused")
	private boolean timeChanged = false;
	
	
	// Time scroll flag
	private boolean timeScrolled = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.turntable);
		
		m_ShowFrequencyTextView = (TextView)findViewById(R.id.showFrequencyTextView);
		m_ShowFrequencyTextView.setTextColor(Color.BLACK);
		m_ShowFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�ÿ" + MoreActivity.ReadFrequencyHour+ "Сʱ" +
				MoreActivity.ReadFrequencyMinute + "����һ��" );
		
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(TurntableActivity.this, ReadFrequencyActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				TurntableActivity.this.finish();
			}
		});
		
		m_ConfirmButton = (Button)findViewById(R.id.confirmButton);
		m_ConfirmButton.setTextColor(Color.WHITE);
		m_ConfirmButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//д������
		        SAXBuilder saxBuilder = new SAXBuilder();
		        try 
		        {
		        	FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					Document document = saxBuilder.build(fileInputStream);
					fileInputStream.close();
					
					Element root = document.getRootElement();
					Element GPSConfig = root.getChild("GPSConfig");
					Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
					RefreshFrequency.getChild("Hour").setText(String.valueOf(MoreActivity.ReadFrequencyHour));
					RefreshFrequency.getChild("Minute").setText(String.valueOf(MoreActivity.ReadFrequencyMinute));
					XMLOutputter out = new XMLOutputter();
					FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					out.output(document,fileOutputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch(IOException e){
				} catch (JDOMException e){
					e.printStackTrace();
				}
		        
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(TurntableActivity.this, ReadFrequencyActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				TurntableActivity.this.finish();
			}
		});
		
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setAdapter(new NumericWheelAdapter(0, 9));
		hours.setLabel("Сʱ");
	
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		mins.setLabel("����");
		mins.setCyclic(true);
	
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					MoreActivity.ReadFrequencyHour = hours.getCurrentItem();
					MoreActivity.ReadFrequencyMinute = mins.getCurrentItem();
					m_ShowFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�ÿ" + MoreActivity.ReadFrequencyHour+ "Сʱ" +
							MoreActivity.ReadFrequencyMinute + "����һ��" );
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
				MoreActivity.ReadFrequencyHour = hours.getCurrentItem();
				MoreActivity.ReadFrequencyMinute = mins.getCurrentItem();
				m_ShowFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�ÿ" + MoreActivity.ReadFrequencyHour+ "Сʱ" +
						MoreActivity.ReadFrequencyMinute + "����һ��" );
				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);	
	}
	
}
